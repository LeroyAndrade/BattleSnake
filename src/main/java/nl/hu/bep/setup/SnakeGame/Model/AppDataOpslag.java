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

    public void addMove(String gameId, String move) {
        GameRecord game = getGameById(gameId);

        if (game == null) {
            game = new GameRecord(gameId);
            games.add(game);
        }

        game.addMove(move);
    }

    public void finishGame(String gameId) {
        GameRecord game = getGameById(gameId);

        if (game != null) {
            game.finish();
        }
    }

    public boolean removeGame(String gameId) {
        GameRecord game = getGameById(gameId);

        if (game != null) {
            return games.remove(game);
        }

        return false;
    }
}