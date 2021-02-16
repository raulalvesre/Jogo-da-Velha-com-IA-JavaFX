package org.raul.players;

import org.raul.App;
import org.raul.ticTacToe.Coordinate;
import org.raul.ticTacToe.TicTacToe;

public class Human implements Player {

    private TicTacToe ticTacToe;
    private String me;

    public Human(String me) {
        ticTacToe = App.getGame();
        this.me = me;
    }

    @Override
    public Coordinate pickCoordinate() {
        ticTacToe.setHumanTurn(true);
        ticTacToe.getLock().lock();

        while (ticTacToe.isHumanTurn()) {
            ticTacToe.humanTurnCondition.awaitUninterruptibly();
        }

        return null;
    }

    @Override
    public String toString() {
        return me;
    }

}
