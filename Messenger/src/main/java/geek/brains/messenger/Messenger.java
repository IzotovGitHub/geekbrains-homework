package geek.brains.messenger;

import geek.brains.messenger.controllers.AuthController;
import geek.brains.messenger.controllers.MessengerController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class Messenger extends Application {

    private Stage chatStage;
    private Stage authStage;

    @Override
    public void start(Stage primaryStage) throws IOException {
        this.chatStage = primaryStage;

        MessengerController controller = createChatDialog(chatStage);
        connectToServer(controller);
        createAuthDialog(primaryStage);

        controller.initializeMessageHandler();
    }

    private MessengerController createChatDialog(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(Messenger.class.getResource("messenger-template.fxml"));

        Parent root = fxmlLoader.load();

        Scene scene = new Scene(root);
        this.chatStage.setTitle("Java FX Application");
        this.chatStage.setScene(scene);

        MessengerController controller = fxmlLoader.getController();

        stage.show();
        return controller;
    }

    private void createAuthDialog(Stage stage) throws IOException {
        FXMLLoader authLoader = new FXMLLoader();
        authLoader.setLocation(Messenger.class.getResource("auth-template.fxml"));
        AnchorPane authDialogPanel = authLoader.load();

        authStage = new Stage();
        authStage.initOwner(stage);
        authStage.initModality(Modality.WINDOW_MODAL);

        authStage.setScene(new Scene(authDialogPanel));

        AuthController authController = authLoader.getController();
        authController.setMessenger(this);
        authController.initializeMessageHandler();

        authStage.showAndWait();
    }

    private void connectToServer(MessengerController messengerController) {
        Network network = Network.getInstance();
        boolean resultConnectedToServer = network.connect();
        if (!resultConnectedToServer) {
            String errorMessage = "Невозможно установить сетевое соединение";
            System.err.println(errorMessage);
            showErrorDialog(errorMessage);
        }

        chatStage.setOnCloseRequest(windowEvent -> network.close());
    }

    public void showErrorDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setContentText(message);
        alert.showAndWait();
    }

    public Stage getAuthStage() {
        return authStage;
    }

    public Stage getChatStage() {
        return chatStage;
    }

    public static void main(String[] args) {
        launch();
    }
}