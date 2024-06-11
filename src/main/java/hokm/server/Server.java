package hokm.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import hokm.messages.ClientRequest;
import hokm.messages.ClientRequestType;
import hokm.messages.JoinRequest;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
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
                //Todo handleConnection(socket);
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
            switch (msg.getType()){
                case JOIN:
                    //Todo
                    break;
                case GAME_CREATE:
                    //Todo
                    break;
            }

            sendBuffer.close();
            receiveBuffer.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void listen() throws IOException {
        Socket socket;
        while (true) {
            socket = serverSocket.accept();
            synchronized (clientRequests) {
                clientRequests.add(socket);
                clientRequests.notify();
            }
        }
    }

    private Server(ServerSocket serverSocket, int workerNumbs) throws IOException {
        Server.serverSocket = serverSocket;
        for (int i = 0; i < workerNumbs; i++) {
            new Server().start();
        }
        listen();
    }

    private Server() {
    }

    static void runInstance(int port) {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            new Server(serverSocket, 10);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    void createGame(Client client) {
        Game newGame = new Game(tokenGenerator());
        games.put(newGame.token, newGame);
        newGame.joinClient(client);
    }

    void joinGame(Client client, String token) {
        Game game;
        if ((game = games.get(token)) != null) {
            game.joinClient(client);
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
}
