package hokm.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import hokm.messages.*;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

public class Server extends Thread {
    private static final ArrayList<Socket> clientRequests = new ArrayList<>();
    private static final GsonBuilder builder = new GsonBuilder();
    private static final Gson gsonAgent = builder.create();
    private static final HashSet<Game> games = new HashSet<>();
    private static final HashMap<String, Room> rooms = new HashMap<>();
    private static final HashMap<String, Player> playersByToken = new HashMap<>();
    private static ServerSocket serverSocket;
    private static Database database;
    private DataOutputStream sendBuffer;
    private DataInputStream receiveBuffer;
    private static Logger logger = LoggerFactory.getLogger(Server.class);

    private Server(ServerSocket serverSocket, int workerNumbs) throws IOException, SQLException {
        database = new Database("database.db");
        database.initialize();
        Server.serverSocket = serverSocket;
        for (int i = 0; i < workerNumbs; i++) {
            new Server().start();
        }
        listen();
    }

    private Server() {
    }

    public static Server runInstance(int port) {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            return new Server(serverSocket, 10);
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static String tokenGenerator() {
        StringBuilder token = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            token.append((char) ('a'+random.nextInt(26)));
        }
        return token.toString();
    }

    @Override
    public void run() {
        Socket socket;
        while (true) {
            synchronized (clientRequests) {
                while (clientRequests.isEmpty()) {
                    try {
                        clientRequests.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                socket = clientRequests.remove(0);
            }
            if (socket != null) {
                handleConnection(socket);
            }
        }
    }

    private void handleConnection(Socket socket) {
        String clientRequestJson;

        try {
            receiveBuffer = new DataInputStream(
                    new BufferedInputStream(socket.getInputStream())
            );
            sendBuffer = new DataOutputStream(
                    new BufferedOutputStream(socket.getOutputStream())
            );


            clientRequestJson = receiveBuffer.readUTF();
            ClientRequest msg = gsonAgent
                    .fromJson(clientRequestJson, gsonAgent.fromJson(clientRequestJson, ClientRequest.class).getType().className);
            try {
                switch (msg.getType()) {
                    case REGISTER:
                        registerUser((RegisterRequest) msg);
                        break;
                    case LOGIN:
                        loginUser((LoginRequest) msg);
                        break;
                    case JOIN:
                        joinRoom((JoinRequest) msg);
                        break;
                    case ROOM_CREATE:
                        createRoom((RoomCreateRequest) msg);
                        break;
                    case LEAVE:
                        leave((LeaveRequest) msg);
                        break;
                    case GAME_START:
                        startGame((GameStartRequest) msg);
                        break;
                    case GAME_UPDATE:
                        gameUpdate((GameUpdateRequest) msg);
                        break;
                    case PUT_CARD:
                        putCard((PutCardRequest) msg);
                        break;
                    case HOKM:
                        hokm((HokmRequest) msg);
                        break;
                    case ROOM_UPDATE:
                        roomUpdate((RoomUpdateRequest) msg);
                        break;
                }
            } catch (RequestException e) {
                sendResponse(false, e.getMessage());
            } catch (RuntimeException e) {
                e.printStackTrace();
                sendResponse(false, "Server Side Error!");
            }

            sendBuffer.close();
            receiveBuffer.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void listen() throws IOException {
        Socket socket;
        logger.info("listening");
        while (true) {
            socket = serverSocket.accept();
            synchronized (clientRequests) {
                clientRequests.add(socket);
                clientRequests.notify();
            }
        }
    }

    private void registerUser(RegisterRequest request) {
        String token = DigestUtils.sha256Hex(request.getUsername() + tokenGenerator());
        if (database.createUser(request.getUsername(), token)) {
            sendResponse(true, token);
        } else {
            sendResponse(false);
        }
    }

    private void loginUser(LoginRequest request) {
        // Todo : Convert to logout
        String username;
        if ((username = database.getUsername(request.getToken())) != null) {
            sendResponse(true, username);
        } else {
            sendResponse(false);
        }
    }

    private void createRoom(RoomCreateRequest request) throws RequestException {
            isLoggedIn(request);
            Player player = playersByToken.get(request.getToken());
            isInRoom(player,false);
            String token = tokenGenerator();
            Room room = createNewRoom(token);
            room.join(player);
            sendResponse(true, token);
    }

    private Room createNewRoom(String token) throws RequestException {
        Room room = new Room();
        if (rooms.putIfAbsent(token, room) != null) {
            throw new RequestException("Couldn't create room!\nTry again.");
        }
        return room;
    }

    private void isLoggedIn(ClientRequest request) throws RequestException {
        if (!playersByToken.containsKey(request.getToken())) {
            String name = database.getUsername(request.getToken());
            if (name != null) {
                playersByToken.put(request.getToken(), new Player(name, request.getToken()));
            }
            else throw new RequestException( "You are not signed up!");
        }
    }

    private void isInRoom(Player player,boolean isInRoom) throws RequestException {
        if (player.isInARoom() != isInRoom) {
            if (isInRoom)
                throw new RequestException("Player is not in a room!");
            else throw new RequestException("Player is already in a room!");
        }
    }

    private void joinRoom(JoinRequest request) throws RequestException {
        isLoggedIn(request);
        Player player = playersByToken.get(request.getToken());
        isInRoom(player,false);
        joinPlayerToRoom(player,request.getGameToken());
        sendResponse(true);
    }

    private void joinPlayerToRoom(Player player,String roomToken) throws RequestException {
        Room room = rooms.get(roomToken);
        if (room==null) throw new RequestException("Room doesn't exists!");
        room.join(player);
    }

    private void leave(LeaveRequest request) throws RequestException {
        isLoggedIn(request);
        Player player = playersByToken.get(request.getToken());
        isInRoom(player,true);
        // Todo: create a leaveGame method
         {
            if (player.isInAGame()) {
                if (request.isForceLeaveGame()) {
                    // Todo : leave game and room
                    sendResponse(true);
                } else sendResponse(false, "Can't leave room while in a game!");
            }
            // Todo : leave room
            else sendResponse(true);
        }
    }

    private void startGame(GameStartRequest request) throws RequestException {
        isLoggedIn(request);
        // Todo
        Player player = playersByToken.get(request.getToken());
        isInGame(player, false);
        isInRoom(player, true);
        Room room = player.getRoom();
        synchronized (room) {
            if (room.getPlayers().get(0).equals(player))
                games.add(room.startGame());
            else throw new RequestException("You are not room owner!");
        }
        sendResponse(true);
    }

    private void isInGame(Player player, boolean isInGame) throws RequestException {
        if (player.isInAGame() != isInGame) {
            if (isInGame)
                throw new RequestException("Player is not in a game!");
            else throw new RequestException("Player is already in a game!");
        }
    }

    private void gameUpdate(GameUpdateRequest request) throws RequestException {
        isLoggedIn(request);
        Player player = playersByToken.get(request.getToken());
        isInGame(player,true);
        sendResponse(true, gsonAgent.toJson(player.getGameUpdate(request.isMajorUpdate())));
    }

    private void putCard(PutCardRequest request) throws RequestException {
        isLoggedIn(request);
        Player player = playersByToken.get(request.getToken());
        isInGame(player,true);
        player.putCard(request.getCard());
        sendResponse(true);
    }

    private void hokm(HokmRequest request) throws RequestException {
        isLoggedIn(request);
        Player player = playersByToken.get(request.getToken());
        isInGame(player,true);
        player.hokm(request.getHokm());
        sendResponse(true);
    }

    private void roomUpdate(RoomUpdateRequest request) throws RequestException {
        isLoggedIn(request);
        Player player = playersByToken.get(request.getToken());
        isInRoom(player,true);
        sendResponse(true,gsonAgent.toJson(player.getRoomUpdate()));
    }

    private void sendResponse(boolean success, String problem) {
        ServerResponse response = new ServerResponse(success, problem);
        String responseString = gsonAgent.toJson(response);
        try {
            sendBuffer.writeUTF(responseString);
        } catch (IOException e) {
            // Todo : handle connection lost
        }
    }

    private void sendResponse(boolean success) {
        sendResponse(success, null);
    }
}
