package geek.brains.messenger.controllers;

import geek.brains.messenger.Network;
import geek.brains.server.connections.Connection;
import geek.brains.server.constt.Command;
import geek.brains.server.network.Data;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import static geek.brains.server.constt.BodyKeys.MESSAGE;
import static geek.brains.server.constt.BodyKeys.USER_NAME;
import static geek.brains.server.constt.Command.*;
import static java.lang.Thread.sleep;

public class MainController {
    @FXML
    public TextField messageField;

    @FXML
    public Button sendButton;

    @FXML
    public TextArea messageTextArea;

    @FXML
    public ListView<String> users;

    private Connection connection;


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


    public void setConnection(Connection connection) {
        this.connection = connection;
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
}