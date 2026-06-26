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
}