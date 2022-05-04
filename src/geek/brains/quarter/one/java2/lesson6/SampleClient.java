package geek.brains.quarter.one.java2.lesson6;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ConcurrentLinkedQueue;

import static geek.brains.quarter.one.java2.lesson6.NetworkStatus.STOPPED;
import static geek.brains.quarter.one.java2.lesson6.NetworkStatus.STOPPING;

public class SampleClient {

    private final ConcurrentLinkedQueue<String> messagesQueueToPrint = new ConcurrentLinkedQueue<>();


    public void start(String host, int port) throws IOException {
        try (Socket socket = new Socket(host, port)) {
            System.out.println("Клиент запущен");

            Network network = Network.open(socket);
            MessagesToSend messages = new MessagesToSend("Клиент");

            network.startMessagesRequest(messages);
            network.startMessagesListener(messagesQueueToPrint);

            while (!STOPPED.equals(network.getStatus())) {
                if (!messages.isEmpty()) {
                    network.sendMessage(messages.pollMessage());
                }
                if (!messagesQueueToPrint.isEmpty()) {
                    System.out.println(messagesQueueToPrint.poll());
                }
                if (STOPPING.equals(network.getStatus())) {
                    network.close();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        new SampleClient().start("localhost", 8080);
    }
}
