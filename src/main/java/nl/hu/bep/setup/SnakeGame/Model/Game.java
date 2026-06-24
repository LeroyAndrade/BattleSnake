package nl.hu.bep.setup.SnakeGame.Model;

import java.util.List;
import java.util.Map;

public class Game {

    public String chooseMove(int myX, int myY, int boardWidth, int boardHeight,  List<Map<String, Object>> myBody ) {
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

        // als default:
        return "up";
    }

    private boolean hitsOwnBody(int newX, int newY, List<Map<String, Object>> myBody) {
        // Loop door alle stukjes van je eigen lichaam
        for (Map<String, Object> bodyPart : myBody) {
            int bodyX = (int) bodyPart.get("x");
            int bodyY = (int) bodyPart.get("y");

            // Als de nieuwe plek hetzelfde is als een body-part, dan return true
            if (newX == bodyX && newY == bodyY) {
                return true;
            }
        }
        // Geen body-part geraakt
        return false;
    }
}