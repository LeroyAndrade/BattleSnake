package nl.hu.bep.setup.SnakeGame.Model;

import java.util.List;
import java.util.Map;
import java.util.Random;

public class Game {

    public String chooseMove(int myX, int myY, int boardWidth, int boardHeight, List<Map<String, Object>> myBody) {
        // Ik maak een Board object aan, zodat ik kan checken of ik niet tegen de muur ga
        Board board = new Board(boardWidth, boardHeight);

        // Ik check per richting of de move binnen bord blijft
        // en of ik niet tegen mijn eigen staart aan bots

        boolean upSafe = board.isInsideBoard(myX, myY + 1)
                && !hitsOwnBody(myX, myY + 1, myBody);

        boolean rightSafe = board.isInsideBoard(myX + 1, myY)
                && !hitsOwnBody(myX + 1, myY, myBody);

        boolean downSafe = board.isInsideBoard(myX, myY - 1)
                && !hitsOwnBody(myX, myY - 1, myBody);

        boolean leftSafe = board.isInsideBoard(myX - 1, myY)
                && !hitsOwnBody(myX - 1, myY, myBody);

        // Even printen om te zien welke kant veilig is
        System.out.println("upSafe = " + upSafe);
        System.out.println("rightSafe = " + rightSafe);
        System.out.println("downSafe = " + downSafe);
        System.out.println("leftSafe = " + leftSafe);

        // Ik kies nu gewoon de eerste veilige richting
        if (upSafe) {
            return "up";
        }

        if (rightSafe) {
            return "right";
        }

        if (downSafe) {
            return "down";
        }

        if (leftSafe) {
            return "left";
        }

        // Als niks veilig is, moet ik toch iets teruggeven
        String[] moves = {"up", "right", "down", "left"};
        Random random = new Random();
        int randomIndex = random.nextInt(moves.length);

        return moves[randomIndex];
    }

    private boolean hitsOwnBody(int newX, int newY, List<Map<String, Object>> myBody) {
        // Als er geen body is, dan kan ik er ook niet tegenaan botsen
        if (myBody == null) {
            return false;
        }

        // Ik loop door elk stukje van mijn eigen body heen
        for (Map<String, Object> bodyPart : myBody) {
            int bodyX = (int) bodyPart.get("x");
            int bodyY = (int) bodyPart.get("y");

            // Als mijn nieuwe plek hetzelfde is als een body stukje,
            // dan bots ik tegen mezelf
            if (newX == bodyX && newY == bodyY) {
                return true;
            }
        }

        // Geen body stukje geraakt
        return false;
    }
}