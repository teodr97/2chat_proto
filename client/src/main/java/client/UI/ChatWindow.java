package client.UI;

import client.Service.HttpsClientFactory;
import client.Service.ServerInterface;
import commons.Entity.Message;
import org.glassfish.jersey.client.ClientConfig;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import java.net.ConnectException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

import static client.Service.ServerInterface.connectionResponseCode;

public class ChatWindow {

    private static InputMessageThread inputMessageThread;

    private static String ip;

    private static final String CMD_DISCONNECT = "/disconnect";

    private static class InputMessageThread extends Thread {

        @Override
        public void run() {
            while (true) {
                String messageInput = new Scanner(System.in).nextLine();

                // TODO Messages sent to the other end should also be saved on local DB as own messages
                if (messageInput.equalsIgnoreCase(CMD_DISCONNECT)) {
                    System.out.println("Stopping application...");
                    ServerInterface.stopPollMessages();
                    break;
                }
                else {
                    try {
                        if (connectionResponseCode(ip) != 200) {
                            System.out.println("[EXCEPTION] Connection to user dropped.\nStopping application...");
                            break;
                        }
                    } catch (NoSuchAlgorithmException e) {
                        throw new RuntimeException(e);
                    } catch (KeyManagementException e) {
                        throw new RuntimeException(e);
                    }
                }
                try {
                    HttpsClientFactory.buildHttpsClient()
                            .target("https://" + ip + ":8443/chat")
                            .request(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
                            .post(Entity.entity(Message.builder().text(messageInput).build(), MediaType.APPLICATION_JSON));
                } catch (KeyManagementException e) {
                    throw new RuntimeException(e);
                } catch (NoSuchAlgorithmException e) {
                    throw new RuntimeException(e);
                }
            }
            interrupt();
        }
    }

    public static void inputIp() {
        System.out.println("Connection ip: ");
        ip = new Scanner(System.in).next();
        System.out.println("Attempting to connect...");
        setupConnection();
    }

    private static void setupConnection() {
        try {
            if (connectionResponseCode(ip) != 200) throw new ConnectException();
            else {
                System.out.println("Connected to user!\nType \"/disconnect\" to disconnect from this session.");
                inputMessageThread = new InputMessageThread();
                inputMessageThread.start();
                ServerInterface.pollMessages();
            }
        }
        catch (ConnectException e) {
            System.out.println("Could not establish connection (timeout). Stopping application...");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (KeyManagementException e) {
            throw new RuntimeException(e);
        }
    }
}
