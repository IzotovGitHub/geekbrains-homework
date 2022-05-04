package geek.brains.quarter.one.java2.lesson6;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Queue;
import java.util.Scanner;

import static geek.brains.quarter.one.java2.lesson6.NetworkStatus.STOPPED;
import static geek.brains.quarter.one.java2.lesson6.NetworkStatus.STOPPING;

public class Network {

    public static final String END = "/end";
    private DataInputStream inputStream;
    private DataOutputStream outputStream;
    private NetworkStatus status;

    private final Socket socket;


    private Network(Socket socket) {
        status = NetworkStatus.OPEN;
        this.socket = socket;
        try {
            inputStream = new DataInputStream(socket.getInputStream());
            outputStream = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Network open(Socket socket) {
        return new Network(socket);
    }

    public boolean close() {
        System.out.println("Close");
        if (inputStream != null || outputStream != null) {
            try {
                socket.close();
                outputStream.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        status = STOPPED;
        return true;
    }

    public void startMessagesRequest(MessagesToSend messages) {
        new Thread(() -> {
            try (Scanner scanner = new Scanner(System.in)) {
                while (true) {
                    String message = scanner.nextLine();
                    messages.add(message);
                    if (message.equals(END)) {
                        status = STOPPING;
                        break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void startMessagesListener(Queue<String> messagesQueue) {
        new Thread(() -> {
            while (true) {
                try {
                    if (!Thread.currentThread().isInterrupted()) {
                        String message = inputStream.readUTF();
                        if (message.endsWith(END)) {
                            messagesQueue.add("Собеседник отключился");
                            status = STOPPING;
                            break;
                        }
                        messagesQueue.add(message);
                    }
                } catch (IOException e) {
                    System.out.println("Соединение прервано");
                    break;
                }
            }
        }).start();
    }

    public void sendMessage(String message) {
        try {
            outputStream.writeUTF(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public NetworkStatus getStatus() {
        return status;
    }
}
