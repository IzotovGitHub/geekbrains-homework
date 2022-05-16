package geek.brains.messenger.creators;

import geek.brains.messenger.Messenger;
import geek.brains.messenger.controllers.Controller;
import geek.brains.messenger.controllers.impl.AuthController;
import geek.brains.messenger.controllers.impl.MainController;
import geek.brains.messenger.controllers.impl.RegController;
import geek.brains.server.connections.Connection;
import geek.brains.server.network.Data;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

import static geek.brains.server.constt.Command.END;

public class ControllerCreator {


    public static Controller createMainController(Stage primaryStage, Connection connection) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(Messenger.class.getResource("messenger-template.fxml"));

        Parent root = fxmlLoader.load();

        Scene scene = new Scene(root);

        primaryStage.setTitle("Messenger");
        primaryStage.setScene(scene);
        primaryStage.setOnCloseRequest(windowEvent -> connection.sendData(new Data(END)));

        MainController controller = fxmlLoader.getController();
        controller.setStage(primaryStage);
        controller.setConnection(connection);

        primaryStage.show();
        return controller;
    }

    public static Controller createRegController(Stage parentStage, Connection connection) {
        FXMLLoader authLoader = new FXMLLoader();
        authLoader.setLocation(Messenger.class.getResource("reg-template.fxml"));

        AnchorPane authDialogPanel = null;
        try {
            authDialogPanel = authLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Stage regStage = new Stage();
        regStage.initOwner(parentStage);
        regStage.initModality(Modality.WINDOW_MODAL);
        regStage.setTitle("Регистрация");

        regStage.setScene(new Scene(authDialogPanel));

        RegController regController = authLoader.getController();
        regController.setStage(regStage);
        regController.setConnection(connection);

        regStage.show();
        return regController;
    }

    public static Controller createAuthController(Stage parentStage, Connection connection) {
        FXMLLoader authLoader = new FXMLLoader();
        authLoader.setLocation(Messenger.class.getResource("auth-template.fxml"));

        AnchorPane authDialogPanel = null;
        try {
            authDialogPanel = authLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Stage authStage = new Stage();
        authStage.initOwner(parentStage);
        authStage.initModality(Modality.WINDOW_MODAL);
        authStage.setTitle("Аутентификация");

        authStage.setScene(new Scene(authDialogPanel));

        AuthController authController = authLoader.getController();
        authController.setStage(authStage);
        authController.setConnection(connection);

        authStage.show();
        return authController;
    }
}
