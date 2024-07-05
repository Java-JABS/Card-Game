package rule.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import rule.messages.*;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Server extends Thread {
    private static final ArrayList<Socket> clientRequests = new ArrayList<>();
    private static final GsonBuilder builder = new GsonBuilder();
    private static final Gson gsonAgent = builder.create();
    private static final HashMap<String, Room> rooms = new HashMap<>();
    private static final HashMap<String, Player> playersByToken = new HashMap<>();
    private static final Logger logger = LoggerFactory.getLogger(Server.class);
    private static ServerSocket serverSocket;
    private static Database database;
    private DataOutputStream sendBuffer;

    private Server(ServerSocket serverSocket, int workerNumbs) throws IOException, SQLException {
        database = new Database("database.db");
        database.initialize();
        logger.info("server initialized");
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
            token.append((char) ('a' + random.nextInt(26)));
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
            DataInputStream receiveBuffer = new DataInputStream(
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
                    case RULE:
                        rule((RuleRequest) msg);
                        break;
                    case ROOM_UPDATE:
                        roomUpdate((RoomUpdateRequest) msg);
                        break;
                }
            } catch (RequestException e) {
                sendResponse(false, gsonAgent.toJson(e.getErrorMessage()));
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
        logger.info("listening on *:{}", serverSocket.getLocalPort());
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
            logger.debug("registering user: {}successful", request.getUsername());
            sendResponse(true, token);
        } else {
            logger.debug("registering user: {}failed", request.getUsername());
            sendResponse(false);
        }
    }

    private void loginUser(LoginRequest request) {
        String username;
        if ((username = database.getUsername(request.getToken())) != null) {
            logger.info("logging user in: {}successful", username);
            sendResponse(true, username);
        } else {
            logger.debug("logging user in: failed");
            sendResponse(false);
        }
    }

    private void createRoom(RoomCreateRequest request) throws RequestException {
        isLoggedIn(request);
        Player player = playersByToken.get(request.getToken());
        isInRoom(player, false);
        String token = tokenGenerator();
        Room room = createNewRoom(token);
        room.join(player);
        logger.info("room created, token:{}", token);
        sendResponse(true, token);
    }

    private Room createNewRoom(String token) throws RequestException {
        Room room = new Room(token);
        if (rooms.putIfAbsent(token, room) != null) {
            logger.warn("failed to create room :( reason: duplicate room token!");
            throw new RequestException(RequestErrorMessage.CREATE_ROOM);
        }
        return room;
    }

    private void isLoggedIn(ClientRequest request) throws RequestException {
        if (!playersByToken.containsKey(request.getToken())) {
            String name = database.getUsername(request.getToken());
            if (name != null) {
                playersByToken.put(request.getToken(), new Player(name, request.getToken()));
            } else throw new RequestException(RequestErrorMessage.NOT_SIGNED_UP);
        }
    }

    private void isInRoom(Player player, boolean isInRoom) throws RequestException {
        if (player.isInARoom() != isInRoom) {
            if (isInRoom)
                throw new RequestException(RequestErrorMessage.NOT_IN_ROOM);
            else throw new RequestException(RequestErrorMessage.IN_ROOM);
        }
    }

    private void joinRoom(JoinRequest request) throws RequestException {
        isLoggedIn(request);
        Player player = playersByToken.get(request.getToken());
        isInRoom(player, false);
        joinPlayerToRoom(player, request.getGameToken());
        logger.info("player joined room");
        sendResponse(true);
    }

    private void joinPlayerToRoom(Player player, String roomToken) throws RequestException {
        Room room = rooms.get(roomToken);
        if (room == null) throw new RequestException(RequestErrorMessage.ROOM_NOT_EXISTS);
        room.join(player);
    }

    private void leave(LeaveRequest request) throws RequestException {
        isLoggedIn(request);
        Player player = playersByToken.get(request.getToken());
        isInRoom(player, true);
        {
            if (player.isInAGame()) {
                if (request.isForceLeaveGame()) {
                    player.leaveGame();
                    logger.info("player left the room");
                } else throw new RequestException(RequestErrorMessage.IN_GAME);
            } else player.leaveRoom();
            sendResponse(true);
        }
    }

    private void startGame(GameStartRequest request) throws RequestException {
        isLoggedIn(request);
        Player player = playersByToken.get(request.getToken());
        isInGame(player, false);
        isInRoom(player, true);
        Room room = player.getRoom();
        synchronized (room) {
            if (room.getPlayers().get(0).equals(player))
                room.startGame();
            else throw new RequestException(RequestErrorMessage.NOT_ROOM_OWNER);
        }
        logger.info("game started");
        sendResponse(true);
    }

    private void isInGame(Player player, boolean isInGame) throws RequestException {
        if (player.isInAGame() != isInGame) {
            if (isInGame)
                throw new RequestException(RequestErrorMessage.NOT_IN_GAME);
            else throw new RequestException(RequestErrorMessage.IN_GAME);
        }
    }

    private void gameUpdate(GameUpdateRequest request) throws RequestException {
        isLoggedIn(request);
        Player player = playersByToken.get(request.getToken());
        isInGame(player, true);
        logger.debug("updating game");
        sendResponse(true, gsonAgent.toJson(player.getGameUpdate(request.isMajorUpdate())));
    }

    private void putCard(PutCardRequest request) throws RequestException {
        isLoggedIn(request);
        Player player = playersByToken.get(request.getToken());
        isInGame(player, true);
        player.putCard(request.getCard());
        logger.info("putting card");
        sendResponse(true);
    }

    private void rule(RuleRequest request) throws RequestException {
        isLoggedIn(request);
        Player player = playersByToken.get(request.getToken());
        isInGame(player, true);
        player.rule(request.getRule());
        logger.info("getting rule");
        sendResponse(true);
    }

    private void roomUpdate(RoomUpdateRequest request) throws RequestException {
        isLoggedIn(request);
        Player player = playersByToken.get(request.getToken());
        isInRoom(player, true);
        logger.info("update room");
        sendResponse(true, gsonAgent.toJson(player.getRoomUpdate()));
    }

    private void sendResponse(boolean success, String problem) {
        ServerResponse response = new ServerResponse(success, problem);
        String responseString = gsonAgent.toJson(response);
        try {
            sendBuffer.writeUTF(responseString);
        } catch (IOException e) {
            logger.warn("connection lost!");
        }
    }

    private void sendResponse(boolean success) {
        sendResponse(success, null);
    }
}
