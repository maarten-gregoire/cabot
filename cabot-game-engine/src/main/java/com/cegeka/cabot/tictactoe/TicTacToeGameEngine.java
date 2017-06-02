package com.cegeka.cabot.tictactoe;

import com.cegeka.cabot.api.GameEngine;
import com.cegeka.cabot.tictactoe.domain.TicTacToeAction;
import com.cegeka.cabot.tictactoe.domain.TicTacToeGameResult;
import com.cegeka.cabot.tictactoe.domain.TicTacToeGameState;

import java.util.Random;

import static com.cegeka.cabot.tictactoe.domain.TicTacToeGameResult.DRAW;
import static com.cegeka.cabot.tictactoe.domain.TicTacToeGameState.ticTacToeGameState;

public class TicTacToeGameEngine extends GameEngine<TicTacToePlayer> {

    private boolean verbose = false;

    @Override
    protected GameResult playGame(TicTacToePlayer player1, TicTacToePlayer player2) {
        boolean moetPlayer1Beginnen = new Random().nextBoolean();
        TicTacToeGameState ticTacToeGameState = ticTacToeGameState();
        TicTacToePlayer activePlayer = moetPlayer1Beginnen ? player1 : player2;
        TicTacToePlayer nonActivePlayer = moetPlayer1Beginnen ? player2 : player1;
        TicTacToeGameResult gameResult = TicTacToeGameResult.ONGOING;

        TicTacToeGameState previousGameState;

        GameResult resultaat = new GameResult(moetPlayer1Beginnen);
        if (verbose) ticTacToeGameState.printGameState();

        int reward = 0;
        while(!gameResult.isFinishedState()) {
            previousGameState = ticTacToeGameState.clone();
            TicTacToeAction gespeeldeActie = activePlayer.bepaalActieOmTeSpelen(ticTacToeGameState, ticTacToeGameState.getToegelatenActies());
            if (verbose) System.out.println(activePlayer.name() + " speelt " + gespeeldeActie.toString());
            gameResult = ticTacToeGameState
                    .applyAction(
                            gespeeldeActie,
                            activePlayer == player1 ? 1 : 2);
            if (verbose) ticTacToeGameState.printGameState();
            switch (gameResult) {
                case ONGOING:
                    reward = 0;
                    break;
                case WINNER:
                    reward = 100000;
                    if (verbose) System.out.println(activePlayer.name() + " wins!");
                    break;
                case DRAW:
                    reward = 10;
                    if (verbose) System.out.println("Draw!");
                    break;
            }
            activePlayer.kenRewardToeVoorGespeeldeActie(previousGameState, gespeeldeActie, ticTacToeGameState, ticTacToeGameState.getToegelatenActies(), reward);
            if (gameResult == DRAW) {
                nonActivePlayer.kenRewardToeVoorGespeeldeActie(previousGameState, gespeeldeActie, ticTacToeGameState, ticTacToeGameState.getToegelatenActies(), reward);
            }
            if (activePlayer == player1) {
                activePlayer = player2;
                nonActivePlayer = player1;
                resultaat.addPlayer1Punten(reward);
                if (gameResult == DRAW) {
                    resultaat.addPlayer2Punten(reward);
                }
            } else {
                activePlayer = player1;
                nonActivePlayer = player2;
                resultaat.addPlayer2Punten(reward);
                if (gameResult == DRAW) {
                    resultaat.addPlayer1Punten(reward);
                }
            }
        }

        return resultaat;
    }
}
