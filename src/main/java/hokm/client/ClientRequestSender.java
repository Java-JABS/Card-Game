package hokm.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import hokm.messages.*;
import hokm.server.RequestException;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientRequestSender {
    final String hostAddress;
    final int port;
    private transient final GsonBuilder builder = new GsonBuilder();
    private transient final Gson gsonAgent = builder.create();
    private String token;

    public ClientRequestSender(String hostAddress, int port) {
        this.hostAddress = hostAddress;
        this.port = port;
    }

    <T extends ClientRequest> String sendMessage(T message) throws RequestException {
        try (Socket socket = new Socket(hostAddress, port)) {
            DataOutputStream sendBuffer = new DataOutputStream(socket.getOutputStream());
            DataInputStream receiveBuffer = new DataInputStream(socket.getInputStream());
            message.setToken(token);
            sendBuffer.writeUTF(gsonAgent.toJson(message));
            ServerResponse response = gsonAgent.fromJson(receiveBuffer.readUTF(), ServerResponse.class);
            if(!response.wasSuccessful()) throw new RequestException(response.getAdditionalInfo());
            return response.getAdditionalInfo();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    public void setToken(String token) {
        this.token = token;
    }
}
