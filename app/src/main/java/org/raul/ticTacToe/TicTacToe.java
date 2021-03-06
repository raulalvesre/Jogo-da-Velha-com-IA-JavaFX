package org.raul.ticTacToe;

import org.raul.controllers.GameGUI;
import org.raul.controllers.MainMenu;
import org.raul.players.*;

import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class TicTacToe {

    private GameGUI GUIController;
    private String[][] gameField;
    int turno;
    private Player playerX;
    private Player playerO;
    private final Lock lock = new ReentrantLock();
    public Condition humanTurnCondition = lock.newCondition();
    private boolean humanTurn;

    public TicTacToe() {
        gameField = new String[][]{{" ", " ", " "}, {" ", " ", " "}, {" ", " ", " "}};
        turno = 1;
    }

    public void setGUIController(GameGUI guiController) {
        this.GUIController = guiController;
    }

    public void startGame() {
        setPlayers();

        while (true) {
            gameTurn();
            if (gameIsOver()) {
                GUIController.gameIsOver();
                break;
            }
        }
    }

    public void setPlayers() {
        setPlayerX();
        setPlayerO();
    }

    private void setPlayerX() {
        switch (MainMenu.playerXIsA) {
            case "Easy Bot" -> playerX = new EasyBot("X", "O");
            case "Medium Bot" -> playerX = new MediumBot("X", "O");
            case "Hard Bot" -> playerX = new HardBot("X", "O");
            case "Human Being" -> playerX = new Human("X");
        }
    }

    private void setPlayerO() {
        switch (MainMenu.playerOIsA) {
            case "Easy Bot" -> playerO = new EasyBot("O", "X");
            case "Medium Bot" -> playerO = new MediumBot("O", "X");
            case "Hard Bot" -> playerO = new HardBot("O", "X");
            case "Human Being" -> playerO = new Human("O");
        }
    }

    private void gameTurn() {
        Player player = turno % 2 != 0 ? playerX : playerO;
        Coordinate playerChosenCoordinate = player.pickCoordinate();

        if (playerChosenCoordinate != null) {
            markPlayerInCoordinate(playerChosenCoordinate, player.toString(), true);
        }
    }

    public Lock getLock() {
        return lock;
    }

    //for humans
    public void markCoordinate(int y, int x) {
        String player = turno % 2 != 0 ? "X" : "O";

        gameField[y][x] = player;
        turno++;
        GUIController.setButtonText(y, x, player);
    }

    //for BOTS
    public void markPlayerInCoordinate(Coordinate coordinate, String whichPlayer, boolean markOnGUIGameField) {
        int y = coordinate.getY();
        int x = coordinate.getX();

        gameField[y][x] = whichPlayer;
        turno++;

        if (markOnGUIGameField) GUIController.setButtonText(y, x, whichPlayer);
    }

    public boolean isCoordinateEmpty(Coordinate coordinate) {
        int x = coordinate.getX();
        int y = coordinate.getY();
        return isCoordinateEmpty(y, x);
    }

    public boolean isCoordinateEmpty(int y, int x) {
        return gameField[y][x].equals(" ");
    }

    public boolean gameIsOver() {
        if (playerWon("X")) {
            GUIController.makeMsgLblStrong();
            GUIController.changeMsgLblText("X WON!!!");
            return true;
        } else if (playerWon("O")) {
            GUIController.makeMsgLblStrong();
            GUIController.changeMsgLblText("O WON!!!");
            return true;
        } else if (draw()) {
            GUIController.makeMsgLblStrong();
            GUIController.changeMsgLblText("DRAW!!!");
            return true;
        }

        return false;
    }

    public boolean playerWon(String player) {
        return (playerWonInARow(player) || playerWonInAColumn(player) || playerWonInADiag(player));
    }

    private boolean playerWonInARow(String player) {
        int quantosNaLinha;

        for (int y = 0; y < 3; y++) {
            quantosNaLinha = 0;
            for (int x = 0; x < 3; x++) {
                if (gameField[y][x].equals(player)) {
                    quantosNaLinha++;
                }
            }

            if (quantosNaLinha == 3) {
                return true;
            }
        }

        return false;
    }

    private boolean playerWonInAColumn(String player) {
        int quantosNaColuna;

        for (int x = 0; x < 3; x++) {
            quantosNaColuna = 0;
            for (int y = 0; y < 3; y++) {
                if (gameField[y][x].equals(player)) {
                    quantosNaColuna++;
                }
            }

            if (quantosNaColuna == 3) {
                return true;
            }
        }

        return false;
    }

    private boolean playerWonInADiag(String player) {
        int quantosNaDiag;

        for (int i = 0; i < 2; i++) {
            quantosNaDiag = 0;
            for (int j = 0; j < 3; j++) {
                if (gameField[Math.abs((i * 2) - j)][j].equals(player)) {
                    quantosNaDiag++;
                }
            }

            if (quantosNaDiag == 3) {
                return true;
            }
        }

        return false;
    }

    public boolean draw() {
        boolean fieldHasEmptyCells = false;
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++) {
                if (gameField[y][x].equals(" ")) {
                    fieldHasEmptyCells = true;
                    break;
                }
            }
        }

        return !fieldHasEmptyCells;
    }

    public ArrayList<Coordinate> emptyCoordinates() {
        ArrayList<Coordinate> emptyCoordinates = new ArrayList<>();

        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++) {
                Coordinate coordinate = new Coordinate(y, x);
                if (isCoordinateEmpty(coordinate)) emptyCoordinates.add(coordinate);
            }
        }

        return emptyCoordinates;
    }

    public Coordinate playerWinnerCoordinate(String whichPlayer) {
        Coordinate inARow = playerWinnerCoordinateInARow(whichPlayer);
        Coordinate inAColumn = playerWinnerCoordinateInAColumn(whichPlayer);
        Coordinate inADiag = playerWinnerCoordinateInADiag(whichPlayer);

        if (inARow != null) return inARow;
        else if (inAColumn != null) return inAColumn;
        else return inADiag;
    }

    private Coordinate playerWinnerCoordinateInARow(String whichPlayer) {
        int aliadosNaLinha;
        int vaziosNaLinha;
        int xDaCoordenadaVazia;

        for (int y = 0; y < 3; y++) {
            aliadosNaLinha = 0;
            vaziosNaLinha = 0;
            xDaCoordenadaVazia = 0;

            for (int x = 0; x < 3; x++) {
                if (gameField[y][x].equals(whichPlayer))
                    aliadosNaLinha++;

                if (gameField[y][x].equals(" ")) {
                    xDaCoordenadaVazia = x;
                    vaziosNaLinha++;
                }
            }

            if (aliadosNaLinha == 2 && vaziosNaLinha == 1) {
                return new Coordinate(y, xDaCoordenadaVazia);
            }
        }

        return null;
    }

    private Coordinate playerWinnerCoordinateInAColumn(String whichPlayer) {
        int aliadosNaColuna;
        int vaziosNaColuna;
        int yDaCoordenadaVazia;

        for (int x = 0; x < 3; x++) {
            aliadosNaColuna = 0;
            vaziosNaColuna = 0;
            yDaCoordenadaVazia = 0;

            for (int y = 0; y < 3; y++) {
                if (gameField[y][x].equals(whichPlayer))
                    aliadosNaColuna++;

                if (gameField[y][x].equals(" ")) {
                    vaziosNaColuna++;
                    yDaCoordenadaVazia = y;
                }
            }

            if (aliadosNaColuna == 2 && vaziosNaColuna == 1) {
                return new Coordinate(yDaCoordenadaVazia, x);
            }
        }

        return null;
    }

    private Coordinate playerWinnerCoordinateInADiag(String whichPlayer) {
        int aliadosNaDiag;
        int vaziosNaDiag = 0;
        int linhaDaCoordenadaVazia;
        int colunaDaCoordenadaVazia;

        for (int i = 0; i < 2; i++) {
            aliadosNaDiag = 0;
            linhaDaCoordenadaVazia = 0;
            colunaDaCoordenadaVazia = 0;

            for (int j = 0; j < 3; j++) {
                if (gameField[Math.abs((i * 2) - j)][j].equals(whichPlayer))
                    aliadosNaDiag++;

                if (gameField[Math.abs((i * 2) - j)][j].equals(" ")) {
                    vaziosNaDiag++;
                    linhaDaCoordenadaVazia = Math.abs((i * 2) - j);
                    colunaDaCoordenadaVazia = j;
                }
            }

            if (aliadosNaDiag == 2 && vaziosNaDiag == 1) {
                return new Coordinate(linhaDaCoordenadaVazia, colunaDaCoordenadaVazia);
            }
        }

        return null;
    }

    public void setHumanTurn(boolean isIt) {
        humanTurn = isIt;
    }

    public boolean isHumanTurn() {
        return humanTurn;
    }
}