package org.raul.controllers;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;

import org.raul.App;

public class MainMenu {

    public static String playerXIsA;
    public static String playerOIsA;
    @FXML
    private ToggleGroup playerXGp;
    @FXML
    private ToggleGroup playerOGp;
    @FXML
    private Button startGameBt;

    @FXML
    private void initialize() {
        BooleanBinding arePlayersSelected = Bindings.createBooleanBinding(() -> playerXGp.getSelectedToggle() == null, playerXGp.selectedToggleProperty())
                .or(Bindings.createBooleanBinding(() -> playerOGp.getSelectedToggle() == null, playerOGp.selectedToggleProperty()));

        startGameBt.disableProperty().bind(arePlayersSelected);
    }

    @FXML
    private void startGameBtAction() {
        playerXIsA = ((ToggleButton) playerXGp.getSelectedToggle()).getText();
        playerOIsA = ((ToggleButton) playerOGp.getSelectedToggle()).getText();
        App.newGame();
        App.setRoot("gameGUI");
    }

    @FXML
    private void exitBtAction() {
        Platform.exit();
    }
}
