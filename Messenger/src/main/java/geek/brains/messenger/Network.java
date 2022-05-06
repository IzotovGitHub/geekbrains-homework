package geek.brains.messenger;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.function.Consumer;

public class Network {

    private static Network INSTANCE;
    public static final String SERVER_HOST = "localhost";
    public static final int SERVER_PORT = 8080;

    private Socket socket;
    private DataInputStream socketInput;
    private DataOutputStream socketOutput;


    private Network() {
    }

    public static Network getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Network();
        }

        return INSTANCE;
    }

    public boolean connect() {
        try {
            socket = new Socket(SERVER_HOST, SERVER_PORT);
            socketInput = new DataInputStream(socket.getInputStream());
            socketOutput = new DataOutputStream(socket.getOutputStream());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void sendMessage(String message) throws IOException {
        try {
            socketOutput.writeUTF(message);
        } catch (IOException e) {
            System.err.println("Не удалось отправить сообщение на сервер");
            e.printStackTrace();
            throw e;
        }
    }

    public void waitMessages(Consumer<String> messageHandler) {
        Thread thread = new Thread(() -> {
            while (true) {
                try {
                    if (Thread.currentThread().isInterrupted()) {
                        return;
                    }
                    String message = socketInput.readUTF();
                    messageHandler.accept(message);
                } catch (IOException e) {
                    System.err.println("Не удалось получить сообщение от сервера");
                    e.printStackTrace();
                    break;
                }
            }
        });
        thread.setDaemon(true);
        thread.start();
    }

    public void close() {
        try {
            socket.close();
        } catch (IOException e) {
            System.err.println("Не удалось закрыть сетевое соединение");
        }
    }
}
