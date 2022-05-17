package geek.brains.messenger.controllers.impl;

import geek.brains.messenger.Messenger;
import geek.brains.messenger.controllers.Controller;
import geek.brains.server.connections.Connection;
import geek.brains.server.constt.Command;
import geek.brains.server.network.Data;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.Map;

import static geek.brains.server.constt.BodyKeys.LOGIN;
import static geek.brains.server.constt.BodyKeys.PASSWORD;
import static geek.brains.server.constt.ConnectionStatus.AUTHENTICATION;

public class AuthController implements Controller {

    @FXML
    public Button authButton;
    @FXML
    public TextField loginField;
    @FXML
    public PasswordField passwordField;

    private Connection connection;
    private Stage stage;

    public void executeAuth(ActionEvent actionEvent) {
        connection.setStatus(AUTHENTICATION);
        String login = loginField.getText();
        String password = passwordField.getText();

        if (login == null || password == null || login.isBlank() || login.isBlank()) {
            Messenger.showErrorDialog("Логин и пароль должны быть указаны");
            return;
        }
        connection.sendData(new Data(Command.AUTH, Map.of(
                LOGIN, login,
                PASSWORD, password
        )));
    }

    @Override
    public Connection getConnection() {
        return connection;
    }

    @Override
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Stage getStage() {
        return stage;
    }

    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
