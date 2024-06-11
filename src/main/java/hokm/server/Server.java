package hokm.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import hokm.messages.ClientRequest;
import hokm.messages.ClientRequestType;
import hokm.messages.JoinRequest;
import hokm.messages.RegisterRequest;
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
                case REGISTER:
                    registerUser((RegisterRequest)msg);
                    break;
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
    private void registerUser(RegisterRequest request){
                String token = DigestUtils.sha256Hex(request.getUsername()+tokenGenerator());
        try{
            database.createUser(request.getUsername(), token);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // Todo write outputbuffer

    }
}
