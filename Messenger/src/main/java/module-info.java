module geek.brains.messenger {
    requires javafx.controls;
    requires javafx.fxml;


    opens geek.brains.messenger to javafx.fxml;
    exports geek.brains.messenger;
}