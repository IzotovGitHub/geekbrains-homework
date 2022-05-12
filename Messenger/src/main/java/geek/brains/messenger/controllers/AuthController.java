package geek.brains.messenger.controllers;

import geek.brains.messenger.Messenger;
import geek.brains.messenger.Network;
import geek.brains.server.connections.Connection;
import geek.brains.server.constt.Command;
import geek.brains.server.network.Data;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.Map;

import static geek.brains.server.constt.BodyKeys.*;

public class AuthController {
    public static final String AUTH_COMMAND = "/auth";
    public static final String AUTH_OK_COMMAND = "/authOk";

    @FXML
    public  TextField userNameField;
    @FXML
    public TextField loginField;
    @FXML
    public PasswordField passwordField;
    @FXML
    public Button authButton;


    private Connection connection;

    @FXML
    public void executeAuth() {
        String userName = userNameField.getText();
        String login = loginField.getText();
        String password = passwordField.getText();

        if (userName == null || login == null || password == null || login.isBlank() || login.isBlank() || userName.isBlank()) {
            Messenger.showErrorDialog("Логин и пароль должны быть указаны");
            return;
        }
        connection.sendData(new Data(Command.LOG_IN, Map.of(
                LOGIN, login,
                PASSWORD, password,
                USER_NAME, userName
        )));
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
