module geek.brains.messenger {
    requires javafx.controls;
    requires javafx.fxml;
    requires server;



    opens geek.brains.messenger to javafx.fxml;
    exports geek.brains.messenger;
    exports geek.brains.messenger.controllers;
    opens geek.brains.messenger.controllers to javafx.fxml;
}