package org.raul.controllers;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import org.raul.App;
import org.raul.ticTacToe.TicTacToe;

public class GameGUI {

    private TicTacToe ticTacToe;
    @FXML
    private Label msgLbl;
    @FXML
    public GridPane gameField;
    @FXML
    private Button playAgainBt;

    @FXML
    private void initialize() {
        ticTacToe = App.getGame();
        ticTacToe.setGUIController(this);

        Task<Void> runGame = new Task<>() {
            @Override
            protected Void call() {
                ticTacToe.startGame();
                return null;
            }
        };

        Thread gameThread = new Thread(runGame);
        gameThread.setDaemon(true);
        gameThread.start();
    }

    public void setButtonText(int y, int x, String text) {
        Platform.runLater(() -> getGameFieldButton(y, x).setText(text));
    }

    private Button getGameFieldButton(int row, int col) {
        for (Node node : gameField.getChildren()) {
            if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
                if (node instanceof Button) {
                    return (Button) node;
                }
            }
        }

        return null;
    }

    @FXML
    private void handleGameFieldButtons(ActionEvent event) {
        Button botaoClicado = (Button) event.getSource();
        int y = GridPane.getRowIndex(botaoClicado);
        int x = GridPane.getColumnIndex(botaoClicado);

        if (ticTacToe.isCoordinateEmpty(y, x)) {
            if (ticTacToe.isHumanTurn()) {
                msgLbl.setText("");
                ticTacToe.markCoordinate(y, x);
                ticTacToe.getLock().lock();
                ticTacToe.setHumanTurn(false);
                ticTacToe.humanTurnCondition.signal();
                ticTacToe.getLock().unlock();
            } else {
                makeMsgLblNormal();
                msgLbl.setText("Wait for your turn!!!");
            }
        } else {
            makeMsgLblNormal();
            msgLbl.setText("Coordinate already filled, try again!!!");
        }
    }

    public void makeMsgLblNormal() {
        msgLbl.setStyle("-fx-font-weight: bold; " +
                "-fx-font-size: 16px");
    }

    public void makeMsgLblStrong() {
        msgLbl.setStyle("-fx-font-weight: bold; " +
                "-fx-font-size: 50px");
    }

    public void changeMsgLblText(String newMessage) {
        Platform.runLater(() -> msgLbl.setText(newMessage));
    }

    public void gameIsOver() {
        disableGameFieldButtons();
        makePlayAgainBtVisible();
    }

    private void disableGameFieldButtons() {
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++) {
                Button button = getGameFieldButton(y, x);
                button.setMouseTransparent(true);
                button.setFocusTraversable(false);
            }
        }
    }

    private void makePlayAgainBtVisible() {
        playAgainBt.setVisible(true);
    }

    @FXML
    private void exitGame() {
        Platform.exit();
    }

    @FXML
    private void handlePlayAgainBt() {
        App.setRoot("mainMenu");
    }

}
