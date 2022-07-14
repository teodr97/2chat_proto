package client.UI;

import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;

import java.util.Scanner;

import static client.Service.ServerInterface.connectToUser;

public class ChatWindow {

    public void inputIp() {
        String ip;

        System.out.println("Connection ip: ");
        ip = new Scanner(System.in).next();
        setupConnection(ip);
    }

    private void setupConnection(String ip) {
        int responseCode = connectToUser(ip);

        if (responseCode != 200) System.out.println("Could not establish connection (timeout). Stopping application...");
        else System.out.println("Connected to user!");
    }
}
