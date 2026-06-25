// Voor snake uiterlijk aanpassen
//bewaar  color, head, tail en version.
package nl.hu.bep.setup.SnakeGame.Model;

import java.io.Serializable;

public class SnakeSettings implements Serializable {
    private String author;
    private String color;
    private String head;
    private String tail;
    private String version;

    private SnakeSettings current = new SnakeSettings();

    public SnakeSettings() {
        this.author = "LeroyAndrade";
        this.color = "#F1F1F1";
        this.head = "default";
        this.tail = "default";
        this.version = "000.000.001-beta";
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public void setTail(String tail) {
        this.tail = tail;
    }

    public SnakeSettings getCurrent() {
        return current;
    }

    public String getAuthor() {
        return author;
    }

    public String getColor() {
        return color;
    }

    public String getHead() {
        return head;
    }

    public String getTail() {
        return tail;
    }

    public String getVersion() {
        return version;
    }
}