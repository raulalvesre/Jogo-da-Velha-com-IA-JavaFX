package org.raul.players;

import org.raul.coordinates.Coordinate;

public class MediumBot extends Bot {

    public MediumBot(String iAm, String enemyIs) {
        super(iAm, enemyIs);
        myLevel = "medium";
    }

    @Override
    public Coordinate pickCoordinate() {
        Coordinate myWinnerCoordinate = ticTacToe.playerWinnerCoordinate(me);
        Coordinate enemyWinnerCoordinate = ticTacToe.playerWinnerCoordinate(enemy);

        if (myWinnerCoordinate != null) {
            return myWinnerCoordinate;
        } else if (enemyWinnerCoordinate != null) {
            return enemyWinnerCoordinate;
        } else {
            return super.pickCoordinate();
        }
    }

}
