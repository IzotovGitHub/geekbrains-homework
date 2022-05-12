package geek.brains.messenger;

import geek.brains.messenger.controllers.AuthController;
import geek.brains.messenger.controllers.MainController;
import geek.brains.messenger.controllers.handlers.ClientHandler;
import geek.brains.server.connections.Connection;
import geek.brains.server.network.Data;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

import static geek.brains.server.constt.Command.END;
import static geek.brains.server.constt.Command.UPDATE_USER_LIST;
import static geek.brains.server.constt.ConnectionStatus.CONNECTION;
import static java.lang.Thread.sleep;

public class Messenger extends Application {

    private Stage chatStage;
    private Stage authStage;

    private Connection connection;

    @Override
    public void start(Stage primaryStage) throws IOException {
        this.chatStage = primaryStage;

        MainController controller = createChatDialog();

        connection = Connection.create("localhost", 8080);
        ClientHandler.handle(connection, this, controller);
        chatStage.setOnCloseRequest(windowEvent -> connection.sendData(new Data(END)));

        controller.setConnection(connection);

        createAuthDialog(chatStage);
        startUserListener();
    }

    private MainController createChatDialog() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(Messenger.class.getResource("messenger-template.fxml"));

        Parent root = fxmlLoader.load();

        Scene scene = new Scene(root);
        this.chatStage.setTitle("Java FX Application");
        this.chatStage.setScene(scene);

        MainController controller = fxmlLoader.getController();

        chatStage.show();
        return controller;
    }

    private AuthController createAuthDialog(Stage parentStage) throws IOException {
        FXMLLoader authLoader = new FXMLLoader();
        authLoader.setLocation(Messenger.class.getResource("auth-template.fxml"));
        AnchorPane authDialogPanel = authLoader.load();

        authStage = new Stage();
        authStage.initOwner(parentStage);
        authStage.initModality(Modality.WINDOW_MODAL);

        authStage.setScene(new Scene(authDialogPanel));

        AuthController authController = authLoader.getController();
        authController.setConnection(connection);

        authStage.showAndWait();
        return authController;
    }

    public static void showErrorDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void startUserListener() {
        Thread thread = new Thread(() -> {
            while (true) {
                if (CONNECTION.equals(connection.getStatus())) {
                    connection.sendData(new Data(UPDATE_USER_LIST));
                    try {
                        sleep(10_000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
        thread.setDaemon(true);
        thread.start();
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