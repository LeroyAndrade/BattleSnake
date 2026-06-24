package nl.hu.bep.setup.SnakeGame.Model;

public class Game {

    public String chooseMove(int myX, int myY, int boardWidth, int boardHeight) {
        Board board = new Board(boardWidth, boardHeight);

        // Check eerst of up veilig is
        if (board.isInsideBoard(myX, myY + 1)) {
            return "up";
        }

        // Anders probeer rechts
        if (board.isInsideBoard(myX + 1, myY)) {
            return "right";
        }

        // Anders probeer down
        if (board.isInsideBoard(myX, myY - 1)) {
            return "down";
        }

        // Anders probeer links
        if (board.isInsideBoard(myX - 1, myY)) {
            return "left";
        }

        // Als echt niks kan, geef toch iets terug
        return "up";
    }
}