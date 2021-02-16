package org.raul.players;

import org.raul.ticTacToe.Coordinate;

public class EasyBot extends Bot{

    public EasyBot(String iAm, String theyAre) {
        super(iAm, theyAre);
    }

    @Override
    public Coordinate pickCoordinate() {
        takeEasyForALittleWhile();
        return super.pickCoordinate();
    }

}
