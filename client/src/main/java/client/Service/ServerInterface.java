package client.Service;

import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
public class ServerInterface {

    /*
    Private class used only by this class in order to
    poll new messages sent by potential clients every
    second.
     */
    @AllArgsConstructor
    private static class MessagePollThread extends Thread {

        @Override
        public void run(){
            // We poll incoming messages from our own DB, which will be hosted on localhost.
            List<String> pendingMessages = ClientBuilder.newClient()
                    .target("https://localhost:8080/chat")
                    .request(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .get(new GenericType<List<String>>() {});

            for (String message : pendingMessages) { System.out.println(">> " + message); }
            try { sleep(1000); }
            catch (InterruptedException e) {
                System.out.println("[EXCEPTION] Exception occurred while polling for new messages (" +
                        e.getMessage() + ").\nStopping application...");
                interrupt();
            }
        }
    }

    public static int connectToUser(String ip) {
        int responseCode = ClientBuilder.newClient()
                .target("https://" + ip + ":8080/")
                .request(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .get()
                .getStatus();

        // When accessing the URL, if the server is active it should always return status code 200
        return responseCode;
    }

    public void pollMessages() {
        MessagePollThread messagePollThread = new MessagePollThread();

        messagePollThread.start();
    }
}
