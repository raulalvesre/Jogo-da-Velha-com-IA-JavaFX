package org.raul.players;

import org.raul.App;
import org.raul.ticTacToe.Coordinate;
import org.raul.ticTacToe.TicTacToe;

import java.util.Random;

public abstract class Bot implements Player {

    protected TicTacToe ticTacToe;
    protected String me;
    protected String enemy;
    protected Random random;

    public Bot(String me, String enemy) {
        ticTacToe = App.getGame();
        this.me = me;
        this.enemy = enemy;
        random = new Random();
    }

    public Coordinate pickCoordinate() {
        while (true) {
            int x = random.nextInt(3);
            int y = random.nextInt(3);

            if (ticTacToe.isCoordinateEmpty(y, x)) {
                return new Coordinate(y, x);
            }
        }
    }

    protected void takeEasyForALittleWhile() {
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return me;
    }

}
