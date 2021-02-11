package org.raul.players;

import org.raul.App;
import org.raul.coordinates.Coordinate;
import org.raul.tictactoe.TicTacToe;

public class Human implements Player {

    private TicTacToe ticTacToe;

    public Human() {
        ticTacToe = App.getGame();
    }

    @Override
    public Coordinate pickCoordinate() {
        ticTacToe.getLock().lock();
        while (ticTacToe.isHumanPlayerTurn()) {
            ticTacToe.humanTurnCondition.awaitUninterruptibly();
        }

        return null;
    }

}
