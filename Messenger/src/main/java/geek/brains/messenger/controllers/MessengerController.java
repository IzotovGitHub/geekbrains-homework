package geek.brains.messenger.controllers;

import geek.brains.messenger.Network;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.Consumer;

public class MessengerController {
    @FXML
    public TextField messageField;

    @FXML
    public Button sendButton;

    @FXML
    public TextArea messageTextArea;


    public void appendMessageToChat(String message) {
        if (!(message = messageField.getText().trim()).isEmpty()) {
            String date = new SimpleDateFormat("dd.MM.yy").format(new Date());
            messageTextArea.appendText(date + ": " + message);
            messageTextArea.appendText(System.lineSeparator());
            messageField.requestFocus();
            messageField.clear();
        }
    }

    public void initializeMessageHandler() {
        Network.getInstance().waitMessages(this::appendMessageToChat);
    }

    public void appendMessageToChat(ActionEvent actionEvent) {
    }
}