package geek.brains.server;

import geek.brains.server.connections.Connection;
import geek.brains.server.handlers.ClientHandler;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {


    public void start(final int PORT) {
        try (ServerSocket server = new ServerSocket(PORT)) {
            while (true) {
                System.out.println("Сервер ожидает подключения");
                Connection connection = new Connection(server.accept());
                System.out.println("Клиент подключился");

                ClientHandler.newClientHandler(connection).handle();
            }
        } catch (IOException e) {
            System.out.println("Ошибка в работе сервера");
        }
    }
}
