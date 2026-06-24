package nl.hu.bep.setup.SnakeGame.Model;

public class Board {
    private int width;
    private int height;

    public Board(int width, int height) {
        this.width = width;
        this.height = height;
    }

    // Controleer of een positie nog binnen het bord zit.
    public boolean isInsideBoard(int x, int y) {
        // Links buiten het bord
        if (x < 0) {
            return false;
        }

        // Rechts buiten het bord
        if (x >= width) {
            return false;
        }

        // Onder buiten het bord
        if (y < 0) {
            return false;
        }

        // Boven buiten het bord
        if (y >= height) {
            return false;
        }

        // Anders is de positie veilig binnen het bord
        return true;
    }
}