package hokm.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import hokm.messages.RegisterRequest;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class tmp {
    private static   final GsonBuilder builder = new GsonBuilder();
    private static Gson gsonAgent = builder.create();
    private static DataOutputStream sendBuffer;
    private static DataInputStream receiveBuffer;
    public static void main(String[] args) throws IOException {
        RegisterRequest request = new RegisterRequest();
        request.setUsername("hjavid");
        Socket socket = new Socket("localhost",1234);
        String message = gsonAgent.toJson(request);
        sendBuffer= new DataOutputStream(socket.getOutputStream());
        receiveBuffer= new DataInputStream(socket.getInputStream());
        sendBuffer.writeUTF(message);
        System.out.println(receiveBuffer.readUTF());

    }
}
