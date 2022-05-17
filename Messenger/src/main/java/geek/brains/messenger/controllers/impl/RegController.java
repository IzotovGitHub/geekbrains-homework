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

import static geek.brains.server.constt.BodyKeys.*;
import static geek.brains.server.constt.ConnectionStatus.REGISTRATION;

public class RegController implements Controller {

    @FXML
    public TextField userNameField;
    @FXML
    public TextField loginField;
    @FXML
    public PasswordField passwordField;
    @FXML
    public Button regButton;


    private Connection connection;
    private Stage stage;

    @Override
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Connection getConnection() {
        return connection;
    }

    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @Override
    public Stage getStage() {
        return stage;
    }

    @FXML
    public void executeReg(ActionEvent actionEvent) {
        connection.setStatus(REGISTRATION);
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
}
