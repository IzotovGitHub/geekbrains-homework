package geek.brains.messenger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MessengerController {
    @FXML
    public TextField messageField;

    @FXML
    public Button sendButton;

    @FXML
    public TextArea messageTextArea;


    public void appendMessageToChat(ActionEvent actionEvent) {
        String message;
        if (!(message = messageField.getText().trim()).isEmpty()) {
            String date = new SimpleDateFormat("dd.MM.yy").format(new Date());
            messageTextArea.appendText(date + ": " + message);
            messageTextArea.appendText(System.lineSeparator());
            messageField.requestFocus();
            messageField.clear();
        }
    }
}