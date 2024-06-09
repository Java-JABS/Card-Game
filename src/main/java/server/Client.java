package server;

import java.io.*;
import java.net.Socket;

public class Client {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private ObjectOutputStream objectOut;

    public Client(Socket socket) {
        this.clientSocket = socket;
        try {
            objectOut = new ObjectOutputStream(clientSocket.getOutputStream());
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}