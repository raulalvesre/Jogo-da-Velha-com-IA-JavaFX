package org.raul.players;

import org.raul.coordinates.Coordinate;
import org.raul.ticTacToe.TicTacToe;

import java.util.ArrayList;

public class HardBot extends Bot {

    public HardBot(String iAm, String enemyIs) {
        super(iAm, enemyIs);
        myLevel = "hard";
    }

    private class CoordinateWithScore extends Coordinate {
        int score;

        public CoordinateWithScore(int y, int x, int score) {
            super(y, x);
            this.score = score;
        }
    }

    @Override
    public Coordinate pickCoordinate() {
        return minimax(ticTacToe, me);
    }

    private CoordinateWithScore minimax(TicTacToe newTicTacToe, String player) {
        ArrayList<Coordinate> emptySpots = newTicTacToe.emptyCoordinates();

        if (newTicTacToe.playerWon(me)) {
            return new CoordinateWithScore(0, 0, 10);
        } else if (newTicTacToe.playerWon(enemy)) {
            return new CoordinateWithScore(0, 0, -10);
        } else if (emptySpots.size() == 0) {
            return new CoordinateWithScore(0, 0, 0);
        }

        ArrayList<CoordinateWithScore> moves = new ArrayList<>();

        int coordinateScore;
        for (Coordinate c : emptySpots) {
            newTicTacToe.markPlayerInCoordinate(c, player);

            if (player.equals(me)) {
                coordinateScore = minimax(newTicTacToe, enemy).score;
            } else {
                coordinateScore = minimax(newTicTacToe, me).score;
            }

            newTicTacToe.markPlayerInCoordinate(c, " ");
            moves.add(new CoordinateWithScore(c.getY(), c.getX(), coordinateScore));
        }

        CoordinateWithScore bestCoordinate = null;
        if (player.equals(me)) {
            int bestValue = -99999;
            for (CoordinateWithScore c : moves) {
                if (c.score > bestValue) {
                    bestValue = c.score;
                    bestCoordinate = c;
                }
            }
        } else {
            int bestValue = 99999;
            for (CoordinateWithScore c : moves) {
                if (c.score < bestValue) {
                    bestValue = c.score;
                    bestCoordinate = c;
                }
            }
        }

        return bestCoordinate;
    }
}
