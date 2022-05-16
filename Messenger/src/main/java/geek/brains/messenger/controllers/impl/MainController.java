package geek.brains.messenger.controllers.impl;

import geek.brains.messenger.controllers.Controller;
import geek.brains.messenger.creators.ControllerCreator;
import geek.brains.server.connections.Connection;
import geek.brains.server.network.Data;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import static geek.brains.server.constt.BodyKeys.MESSAGE;
import static geek.brains.server.constt.BodyKeys.USER_NAME;
import static geek.brains.server.constt.Command.SEND_ALL;
import static geek.brains.server.constt.Command.WRITE;

public class MainController implements Controller {

    @FXML
    public TextField messageField;
    @FXML
    public Button sendButton;
    @FXML
    public TextArea messageTextArea;
    @FXML
    public ListView<String> users;
    @FXML
    public Button authBtn;
    @FXML
    public Button regBtn;
    @FXML
    public Label userNameLabel;

    private Connection connection;
    private Stage stage;
    private Controller regController;
    private Controller authController;


    public void appendMessageToChat(String sender, String message) {
        messageTextArea.appendText(new SimpleDateFormat("dd.MM.yy").format(new Date()));
        messageTextArea.appendText(System.lineSeparator());

        if (sender != null) {
            messageTextArea.appendText(sender + ":");
            messageTextArea.appendText(System.lineSeparator());
        }

        messageTextArea.appendText(message);
        messageTextArea.appendText(System.lineSeparator());
        messageTextArea.appendText(System.lineSeparator());

        messageField.requestFocus();
        messageField.clear();
    }

    public void sendMessage(ActionEvent actionEvent) {
        String message = messageField.getText();
        String recipient;
        Data data;

        if (message.isEmpty()) {
            messageField.clear();
            return;
        }

        if (!users.getSelectionModel().isEmpty()) {
            recipient = users.getSelectionModel().getSelectedItem();
            data = new Data(WRITE, Map.of(
                    USER_NAME, recipient,
                    MESSAGE, message));
        } else {
            data = new Data(SEND_ALL, Map.of(MESSAGE, message));
        }

        connection.sendData(data);
        appendMessageToChat("Я", message);
    }

    public void executeReg(ActionEvent actionEvent) {
        regController = ControllerCreator.createRegController(stage, connection);
    }

    public void executeAuth(ActionEvent actionEvent) {
        authController = ControllerCreator.createAuthController(stage, connection);
    }

    public Controller getRegController() {
        return regController;
    }

    public Controller getAuthController() {
        return authController;
    }

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
}