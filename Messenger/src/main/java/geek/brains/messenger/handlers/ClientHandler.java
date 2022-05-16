package geek.brains.messenger.handlers;

import geek.brains.messenger.Messenger;
import geek.brains.messenger.controllers.impl.MainController;
import geek.brains.server.connections.Connection;
import geek.brains.server.constt.Command;
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

    public static void handle(MainController controller) {
        new Thread(() -> {
            Connection connection = controller.getConnection();
            if (connection != null) {
                while (!CLOSE.equals(connection.getStatus())) {
                    Data data = connection.waitData();
                    Command command = data.getCommand();
                    Map<String, String> body = data.getBody();

                    if (LOG_IN_OK.equals(command)) {
                        connection.connect();
                        Platform.runLater(() -> {
                            connection.sendData(new Data(UPDATE_USER_LIST));
                            controller.userNameLabel.setText(body.get(USER_NAME));
                            controller.authBtn.setDisable(true);
                            controller.regBtn.setDisable(true);
                            controller.getRegController().getStage().close();
                        });
                    } else if (AUTH_OK.equals(command)) {
                        connection.connect();
                        Platform.runLater(() -> {
                            connection.sendData(new Data(UPDATE_USER_LIST));
                            controller.userNameLabel.setText(body.get(USER_NAME));
                            controller.authBtn.setDisable(true);
                            controller.regBtn.setDisable(true);
                            controller.getAuthController().getStage().close();
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
                        Platform.runLater(() -> {
                            controller.getStage().close();
                        });
                        connection.close();
                    }
                }
            }
        }).start();

    }
}
