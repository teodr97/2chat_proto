package client.Service;

import commons.Entity.Message;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.glassfish.jersey.client.ClientConfig;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import java.util.List;

@NoArgsConstructor
public class ServerInterface {

    private static MessagePollThread messagePollThread;

    /*
    Private class used only by this class in order to
    poll new messages sent by potential clients every
    second. Connects to localhost.
     */
    private static class MessagePollThread extends Thread {

        @Override
        public void run(){
            while (true) {
                if (connectionResponseCode("localhost") != 200) {
                    System.out.println("[EXCEPTION] Could not connect to local database.\nStopping application...");
                    break;
                }
                // We poll incoming messages from our own DB, which will be hosted on localhost.
                List<Message> pendingMessages = ClientBuilder.newClient()
                        .target("http://localhost:8080/chat")
                        .request(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .get()
                        .readEntity(new GenericType<List<Message>>() {});

                if (pendingMessages != null) for (Message message : pendingMessages) { System.out.println(">> " + message.getText()); }
                try { sleep(1000); }
                catch (InterruptedException e) {
                    System.out.println("[EXCEPTION] Exception occurred while polling for new messages (" +
                            e.getMessage() + ").\nStopping application...");
                    break;
                }
            }
            interrupt();
        }
    }

    public static int connectionResponseCode(String ip) {
        // When accessing the URL, if the server is active it should always return status code 200
        return ClientBuilder.newClient(new ClientConfig())
                .target("http://" + ip + ":8080/")
                .request(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .get()
                .getStatus();
    }

    public static void pollMessages() {
        if (messagePollThread == null) {
            messagePollThread = new MessagePollThread();
            messagePollThread.start();
        }
    }

    public static void stopPollMessages() {
        if (messagePollThread != null) messagePollThread.interrupt();
    }
}
