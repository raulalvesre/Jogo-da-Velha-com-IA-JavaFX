module Jogo.da.Velha.com.IA.app.main {
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;

    opens org.raul to javafx.fxml;
    opens org.raul.controllers to javafx.fxml;

    exports org.raul;
    exports org.raul.controllers;
}