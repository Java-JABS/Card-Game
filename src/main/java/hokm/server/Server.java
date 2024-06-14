package hokm.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import hokm.messages.*;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Server extends Thread {
    private static final ArrayList<Socket> clientRequests = new ArrayList<>();
    private static final HashMap<String, Game> games = new HashMap<>();
    private static ServerSocket serverSocket;
    private static final GsonBuilder builder = new GsonBuilder();
    private static final Gson gsonAgent = builder.create();
    private DataOutputStream sendBuffer;
    private DataInputStream receiveBuffer;
    private static Database database;
    private static final HashMap<String, Player> players = new HashMap<>();
    private static final HashMap<String, Player> rooms = new HashMap<>();

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
                socket = clientRequests.get(0);
                clientRequests.remove(0);
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
            ClientRequest msg = (ClientRequest) gsonAgent
                    .fromJson(clientRequestJson, gsonAgent.fromJson(clientRequestJson, ClientRequest.class).getType().className);
            switch (msg.getType()) {
                case REGISTER:
                    registerUser((RegisterRequest) msg);
                    break;
                case LOGIN:
                    loginUser((LoginRequest) msg);
                    break;
                case JOIN:
                    joinGame((JoinRequest) msg);
                    break;
                case GAME_CREATE:
                    createRoom((GameCreateRequest) msg);
                    break;
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
        while (true) {
            socket = serverSocket.accept();
            synchronized (clientRequests) {
                clientRequests.add(socket);
                clientRequests.notify();
            }
        }
    }

    private Server(ServerSocket serverSocket, int workerNumbs) throws IOException, SQLException {
        database=new Database("database.db");
        Server.serverSocket = serverSocket;
        for (int i = 0; i < workerNumbs; i++) {
            new Server().start();
        }
        listen();
    }

    private Server() {
    }

    public static void runInstance(int port) {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            new Server(serverSocket, 10);
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static String tokenGenerator() {
        StringBuilder token = new StringBuilder();
        Random random = new Random();
        char[] newToken = new char[6];
        for (int i = 0; i < 6; i++) {
            token.append("a").append(random.nextInt(26));
        }
        return token.toString();
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
        String username;
        if ((username = database.getUsername(request.getToken())) != null) {
            sendResponse(true, username);
        } else {
            sendResponse(false);
        }
    }

    private void createRoom(GameCreateRequest request) {
        if (!checkLoggedIn(request)) return;
        Player player = new Player(request.getToken());
        if (rooms.containsValue(player))
            sendResponse(false, "You are already in a room");
        else {
            String gameToken = tokenGenerator();
            rooms.put(gameToken, player);
            sendResponse(true, gameToken);
        }
    }

    private boolean checkLoggedIn(ClientRequest request) {
        if (!players.containsKey(request.getToken())) {
            if (database.getUsername(request.getToken()) != null) {
                players.put(request.getToken(), new Player(request.getToken()));
                return true;
            }
            sendResponse(false,"You are not signed in!");
            return false;
        }
        return true;
    }

    private void joinGame(JoinRequest request) {
        if (!checkLoggedIn(request)) return;
        if (!games.containsKey(request.getGameToken())) {
            sendResponse(false, "Game doesn't exists!");
        } else {
            rooms.put(request.getToken(),players.get(request.getToken()));
            sendResponse(true);
        }
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
