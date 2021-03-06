package org.raul.players;

import org.raul.ticTacToe.Coordinate;

public class MediumBot extends Bot {

    public MediumBot(String iAm, String enemyIs) {
        super(iAm, enemyIs);
    }

    @Override
    public Coordinate pickCoordinate() {
        Coordinate myWinnerCoordinate = ticTacToe.playerWinnerCoordinate(me);
        Coordinate enemyWinnerCoordinate = ticTacToe.playerWinnerCoordinate(enemy);

        takeEasyForALittleWhile();
        if (myWinnerCoordinate != null) {
            return myWinnerCoordinate;
        } else if (enemyWinnerCoordinate != null) {
            return enemyWinnerCoordinate;
        } else {
            return super.pickCoordinate();
        }
    }

}
