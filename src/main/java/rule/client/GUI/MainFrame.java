package rule.client.GUI;

import rule.client.ClientRequestSender;
import rule.messages.LoginRequest;
import rule.messages.RegisterRequest;
import rule.messages.RequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class MainFrame extends JFrame {

    private final Logger logger = LoggerFactory.getLogger(MainFrame.class);
    ClientRequestSender client;


    public MainFrame() {
        Assets.playBackgroundMusic("intro.wav");
        this.setTitle("Java-JABS™ CardGame");
        this.setIconImage(Assets.getImageIcon("icon.png").getImage());
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
                logger.info("A config found, connect to server");
                newClient = ClientRequestSender.gsonAgent.fromJson(a.toString(), ClientRequestSender.class);
                logger.info("Trying to connect to the server :");
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

        while (newClient == null) {
            try {
                logger.info("Couldn't connect to server, Try to getting server information from user: ");
                newClient = new ClientRequestSender(JOptionPane.showInputDialog("Please Enter Server IP."), Integer.parseInt(JOptionPane.showInputDialog("Please Enter Server PORT.")));
                newClient.sendMessage(new RegisterRequest());
            } catch (RequestException e) {
                trySignup(newClient);
            } catch (RuntimeException e) {
                JOptionPane.showMessageDialog(null, "couldn't connect to server!", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        }
        client = newClient;
        try (FileWriter file = new FileWriter("config.txt")) {
            logger.info("Saving server information to config.txt :");
            file.write(ClientRequestSender.gsonAgent.toJson(client));
        } catch (IOException ignored) {
        }
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        add(new MainMenuPanel());
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

    }

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
}
