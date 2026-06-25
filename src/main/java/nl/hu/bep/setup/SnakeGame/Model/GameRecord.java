//Eén gespeeld potje bewaren.
//   gameId, aantal turns, moves, finished.
package nl.hu.bep.setup.SnakeGame.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameRecord implements Serializable {
    private String gameId;
    private int turns;
    private boolean finished;
    private List<String> moves = new ArrayList<>();

    public GameRecord(String gameId) {
        this.gameId = gameId;
        this.turns = 0;
        this.finished = false;
    }

    public String getGameId() {
        return gameId;
    }

    public int getTurns() {
        return turns;
    }

    public boolean isFinished() {
        return finished;
    }

    public List<String> getMoves() {
        return Collections.unmodifiableList(moves);
    }

    public void addMove(String move) {
        moves.add(move);
        turns++;
    }

    public void finish() {
        finished = true;
    }
}