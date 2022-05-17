package geek.brains.messenger;

import geek.brains.messenger.controllers.impl.MainController;
import geek.brains.messenger.creators.ControllerCreator;
import geek.brains.messenger.handlers.ClientHandler;
import geek.brains.server.connections.Connection;
import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;

public class Messenger extends Application {

    private static final String HOST = "localhost";
    private static final int PORT = 8080;

    @Override
    public void start(Stage primaryStage) throws IOException {
        Connection connection = Connection.create(HOST, PORT);
        MainController controller = (MainController) ControllerCreator.createMainController(primaryStage, connection);
        ClientHandler.handle(controller);
    }

    public static void showErrorDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setContentText(message);
        alert.showAndWait();
    }


    public static void main(String[] args) {
        launch();
    }
}