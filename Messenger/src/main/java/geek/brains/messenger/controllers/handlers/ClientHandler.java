package geek.brains.messenger.controllers.handlers;

import geek.brains.messenger.Messenger;
import geek.brains.messenger.controllers.MainController;
import geek.brains.server.connections.Connection;
import geek.brains.server.constt.Command;
import geek.brains.server.constt.ConnectionStatus;
import geek.brains.server.network.Data;
import javafx.application.Platform;

import java.util.Map;

import static geek.brains.server.constt.BodyKeys.MESSAGE;
import static geek.brains.server.constt.BodyKeys.*;
import static geek.brains.server.constt.Command.*;
import static geek.brains.server.constt.ConnectionStatus.CLOSE;
import static java.lang.String.format;

public class ClientHandler {
    private ClientHandler() {
    }

    public static void handle(Connection connection, Messenger messenger, MainController controller) {
        new Thread(() -> {
            while (!CLOSE.equals(connection.getStatus())) {
                Data data = connection.waitData();
                Command command = data.getCommand();
                Map<String, String> body = data.getBody();

                if (AUTH_OK.equals(command)) {
                    Thread.currentThread().interrupt();
                    Platform.runLater(() -> {
                        messenger.getChatStage().setTitle(body.get(USER_NAME));
                        messenger.getAuthStage().close();
                    });
                } else if (ERR_MESSAGE.equals(command)) {
                    Platform.runLater(() -> Messenger.showErrorDialog(body.get(MESSAGE)));
                } else if (UPDATE_USER_LIST.equals(command)) {
                    String[] userNames = body.get(USER_NAMES).split(",");
                    Platform.runLater(() -> controller.users.getItems().setAll(userNames));
                } else if (Command.MESSAGE.equals(command)) {
                    Platform.runLater(() -> controller.appendMessageToChat(body.get(SENDER), body.get(MESSAGE)));
                } else if (END.equals(command)) {
                    Platform.runLater(() -> {
                        connection.sendData(new Data(UPDATE_USER_LIST));
                        controller.appendMessageToChat(null, format("Пользователь %s вышел из чата", body.get(SENDER)));
                    });
                } else if (CLOSE_CONNECTION.equals(command)) {
                    connection.close();
                }
            }
            System.out.println("Выход из цикла");
        }).start();

    }
}
