package nl.hu.bep.setup.SnakeGame.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AppDataOpslag implements Serializable {
    private SnakeSettings snakeSettings;
    private List<GameRecord> games;

    public AppDataOpslag() {
        this.snakeSettings = new SnakeSettings();
        this.games = new ArrayList<>();
    }

    public SnakeSettings getSnakeSettings() {
        return snakeSettings;
    }

    public List<GameRecord> getGames() {
        return games;
    }

    public GameRecord getGameById(String gameId) {
        for (GameRecord game : games) {
            if (game.getGameId().equals(gameId)) {
                return game;
            }
        }

        return null;
    }

    public void addGame(String gameId) {
        if (getGameById(gameId) == null) {
            games.add(new GameRecord(gameId));
        }
    }

}