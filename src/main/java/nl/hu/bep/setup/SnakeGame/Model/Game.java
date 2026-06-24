package nl.hu.bep.setup.SnakeGame.Model;

import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Map;

public class Game {

    public String chooseMove(int myX, int myY, int boardWidth, int boardHeight, List<Map<String, Object>> myBody) {
        Board board = new Board(boardWidth, boardHeight);

        // Check eerst of up veilig is:
        // 1. binnen het bord
        // 2. niet tegen eigen lichaam
        if (board.isInsideBoard(myX, myY + 1) && !hitsOwnBody(myX, myY + 1, myBody)) {
            return "up";
        }

        // Anders probeer rechts
        if (board.isInsideBoard(myX + 1, myY) && !hitsOwnBody(myX + 1, myY, myBody)) {
            return "right";
        }

        // Anders probeer down
        if (board.isInsideBoard(myX, myY - 1) && !hitsOwnBody(myX, myY - 1, myBody)) {
            return "down";
        }

        // Anders probeer links
        if (board.isInsideBoard(myX - 1, myY) && !hitsOwnBody(myX - 1, myY, myBody)) {
            return "left";
        }

        // Als echt niks veilig lijkt, geef toch iets terug.
        return "up";
    }


    private boolean hitsOwnBody(int newX, int newY, List<Map<String, Object>> myBody) {
        // Als body null is, kan niet worden gechecked
        if (myBody == null) {
            return false;
        }

        // Loop door alle stukken van je eigen lichaam
        for (Map<String, Object> bodyPart : myBody) {
            int bodyX = (int) bodyPart.get("x");
            int bodyY = (int) bodyPart.get("y");

            // Als de nieuwe plek hetzelfde is als een body-part,
            // dan bots je tegen self.
            if (newX == bodyX && newY == bodyY) {
                return true;
            }
        }

        return false;
    }
}