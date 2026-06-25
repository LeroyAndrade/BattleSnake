// Voor snake uiterlijk aanpassen
//bewaar  color, head, tail en version.
package nl.hu.bep.setup.SnakeGame.Model;

public class SnakeSettings {
    private String author;
    private String color;
    private String head;
    private String tail;
    private String version;

    private static SnakeSettings current = new SnakeSettings();

    public SnakeSettings() {
        this.author = "LeroyAndrade";
        this.color = "#F1F1F1";
        this.head = "default";
        this.tail = "default";
        this.version = "000.000.001-beta";
    }
}