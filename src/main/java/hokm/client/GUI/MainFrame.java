package hokm.client.GUI;

import hokm.client.ClientRequestSender;
import hokm.messages.LoginRequest;
import hokm.messages.RegisterRequest;
import hokm.server.RequestException;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class MainFrame extends JFrame {
    ClientRequestSender client;

    static void trySignup(ClientRequestSender newClient) {
        while (true) {
            try {
                assert newClient != null;
                newClient.setToken(newClient.sendMessage(new RegisterRequest(JOptionPane.showInputDialog("Enter a new username:"))));
                return;
            } catch (RequestException ex) {
                JOptionPane.showMessageDialog(null, "This username is taken!\nPlease try again!", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    MainFrame() {
        // Todo
        ClientRequestSender newClient = null;
        try {
            File file = new File("config.txt");
            if (file.isFile() && file.canRead()) {
                Scanner scanner = new Scanner(file);
                StringBuilder a = new StringBuilder();
                while (scanner.hasNextLine()) {
                    a.append(scanner.nextLine());
                }

                newClient = ClientRequestSender.gsonAgent.fromJson(a.toString(), ClientRequestSender.class);
                newClient.sendMessage(new LoginRequest());
                client = newClient;
            }
        } catch (FileNotFoundException ignored) {
        } catch (RequestException e) {
            JOptionPane.showMessageDialog(null, "Your token is wrong!\nPlease sign up again!", "Warning", JOptionPane.WARNING_MESSAGE);
            trySignup(newClient);
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(null, "couldn't connect to server!", "Warning", JOptionPane.WARNING_MESSAGE);
        }
        while (client == null) {
            try {
                client = new ClientRequestSender(JOptionPane.showInputDialog("Please Enter Server IP."), Integer.parseInt(JOptionPane.showInputDialog("Please Enter Server PORT.")));
                client.sendMessage(new RegisterRequest());
            } catch (RequestException e) {
                trySignup(newClient);
            } catch (RuntimeException e) {
                JOptionPane.showMessageDialog(null, "couldn't connect to server!", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        }

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        add(new MainMenuPanel());
        pack();
        setVisible(true);
    }

    public static void main(String[] args) {
        new MainFrame();
    }

}
