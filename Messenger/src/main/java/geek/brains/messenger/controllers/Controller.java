package geek.brains.messenger.controllers;

import geek.brains.server.connections.Connection;
import javafx.stage.Stage;

public interface Controller {

    void setConnection(Connection connection);

    Connection getConnection();

    void setStage(Stage stage);

    Stage getStage();

}
