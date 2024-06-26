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
    private final GsonBuilder builder = new GsonBuilder();
    private final Gson gsonAgent = builder.create();

    public ClientRequestSender(String hostAddress, int port) {
        this.hostAddress = hostAddress;
        this.port = port;
    }

    ServerResponse sendMessage(String message) {
        try (Socket socket = new Socket(hostAddress, port);) {
            DataOutputStream sendBuffer = new DataOutputStream(socket.getOutputStream());
            DataInputStream receiveBuffer = new DataInputStream(socket.getInputStream());
            sendBuffer.writeUTF(message);
            return gsonAgent.fromJson(receiveBuffer.readUTF(), ServerResponse.class);
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    String register(String username) throws RequestException {
        RegisterRequest request = new RegisterRequest();
        request.setUsername(username);
        String message = gsonAgent.toJson(request);
        ServerResponse response = sendMessage(message);
        if(!response.wasSuccessful()) throw new RequestException(response.getAdditionalInfo());
        return response.getAdditionalInfo();
    }
    void join(String token) throws RequestException {
        throw new RequestException("");
    }
    void room_create(){

    }

//    REGISTER(RegisterRequest.class),
//    LOGIN(LoginRequest .class),
//    JOIN(JoinRequest .class),
//    ROOM_CREATE(RoomCreateRequest .class),
//    LEAVE(LeaveRequest.class),
//    GAME_START(GameStartRequest.class),
//    GAME_UPDATE(GameUpdateRequest.class),
//    ROOM_UPDATE(RoomUpdateRequest.class),
//            ;

}
