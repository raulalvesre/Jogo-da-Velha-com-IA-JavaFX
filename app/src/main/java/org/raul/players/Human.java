package org.raul.players;

import org.raul.App;
import org.raul.coordinates.Coordinate;
import org.raul.ticTacToe.TicTacToe;

public class Human implements Player {

    private TicTacToe ticTacToe;

    public Human() {
        ticTacToe = App.getGame();
    }

    @Override
    public Coordinate pickCoordinate() {
        ticTacToe.getLock().lock();
        while (ticTacToe.isHumanTurn()) {
            ticTacToe.humanTurnCondition.awaitUninterruptibly();
        }

        return null;
    }

}
