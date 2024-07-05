package rule.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import rule.messages.ClientRequest;
import rule.messages.RequestErrorMessage;
import rule.messages.RequestException;
import rule.messages.ServerResponse;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientRequestSender {
    public static final GsonBuilder builder = new GsonBuilder();
    public static final Gson gsonAgent = builder.create();
    final String hostAddress;
    final int port;
    private String token;

    public ClientRequestSender(String hostAddress, int port) {
        this.hostAddress = hostAddress;
        this.port = port;
    }

    public synchronized <T extends ClientRequest> String sendMessage(T message) throws RequestException {
        try (Socket socket = new Socket(hostAddress, port)) {
            DataOutputStream sendBuffer = new DataOutputStream(socket.getOutputStream());
            DataInputStream receiveBuffer = new DataInputStream(socket.getInputStream());
            message.setToken(token);
            sendBuffer.writeUTF(gsonAgent.toJson(message));
            ServerResponse response = gsonAgent.fromJson(receiveBuffer.readUTF(), ServerResponse.class);
            if (response.wasNotSuccessful())
                throw new RequestException(gsonAgent.fromJson(response.getAdditionalInfo(), RequestErrorMessage.class));
            return response.getAdditionalInfo();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    public void setToken(String token) {
        this.token = token;
    }
}
