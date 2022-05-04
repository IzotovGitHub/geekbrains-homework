package geek.brains.quarter.one.java2.lesson6;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentLinkedQueue;

import static geek.brains.quarter.one.java2.lesson6.NetworkStatus.STOPPED;
import static geek.brains.quarter.one.java2.lesson6.NetworkStatus.STOPPING;

public class SampleServer{

    private final int PORT;


    private final ConcurrentLinkedQueue<String> messagesQueueToPrint = new ConcurrentLinkedQueue<>();

    public SampleServer(final int PORT){
        this.PORT = PORT;
    }

    public void start(){
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Сервер запущен");
            Socket clientSocket = serverSocket.accept();
            System.out.println("Клиент подключился");

            Network network = Network.open(clientSocket);
            MessagesToSend messages = new MessagesToSend("Сервер");

            network.startMessagesRequest(messages);
            network.startMessagesListener(messagesQueueToPrint);

            while(!STOPPED.equals(network.getStatus())){
                if(!messages.isEmpty()){
                    network.sendMessage(messages.pollMessage());
                }
                if(!messagesQueueToPrint.isEmpty()){
                    System.out.println(messagesQueueToPrint.poll());
                }

                if(STOPPING.equals(network.getStatus())){
                    network.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public static void main(String[] args) {
        new SampleServer( 8080).start();
    }
}
