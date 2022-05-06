package geek.brains.messenger.controllers;

import geek.brains.messenger.Messenger;
import geek.brains.messenger.Network;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.function.Consumer;

public class AuthController {
    public static final String AUTH_COMMAND = "/auth";
    public static final String AUTH_OK_COMMAND = "/authOk";

    @FXML
    public TextField loginField;
    @FXML
    public PasswordField passwordField;
    @FXML
    public Button authButton;

    private Messenger messenger;

    @FXML
    public void executeAuth() {
        String login = loginField.getText();
        String password = passwordField.getText();

        if (login == null || password == null || login.isBlank() || login.isBlank()) {
            messenger.showErrorDialog("Логин и пароль должны быть указаны");
            return;
        }

        String authCommandMessage = String.format("%s %s %s", AUTH_COMMAND, login, password);

        try {
            Network.getInstance().sendMessage(authCommandMessage);
        } catch (IOException e) {
            messenger.showErrorDialog("Ошибка передачи данных по сети");
            e.printStackTrace();
        }

    }

    public void setMessenger(Messenger messenger) {
        this.messenger = messenger;
    }

    public void initializeMessageHandler() {
        Network.getInstance().waitMessages(message -> {
            if (message.startsWith(AUTH_OK_COMMAND)) {
                Thread.currentThread().interrupt();
                Platform.runLater(() -> {
                    String[] parts = message.split(" ");
                    String userName = parts[1];
                    messenger.getChatStage().setTitle(userName);
                    messenger.getAuthStage().close();
                });
            } else {
                Platform.runLater(() -> messenger.showErrorDialog("Пользователя с таким логином и паролем не существует"));
            }
        });
    }
}
