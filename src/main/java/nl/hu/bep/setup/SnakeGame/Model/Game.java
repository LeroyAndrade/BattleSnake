package nl.hu.bep.setup.SnakeGame.Model;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.ArrayList;

public class Game {

    public String chooseMove( int myX,
            int myY,
            int boardWidth,
            int boardHeight,
            List<Map<String, Object>> myBody,
            List<Map<String, Object>> allSnakes,
            String myId,  List<Map<String, Object>> food) {

        // Ik maak een Board object aan, zodat ik kan checken of ik niet tegen de muur ga
        Board board = new Board(boardWidth, boardHeight);

        // Ik check per richting of de move binnen bord blijft
        // en of ik niet tegen mijn eigen body of een enemy body aan bots

        boolean upSafe = board.isInsideBoard(myX, myY + 1)
                && !hitsOwnBody(myX, myY + 1, myBody)
                && !hitsEnemyBody(myX, myY + 1, allSnakes, myId);

        boolean rightSafe = board.isInsideBoard(myX + 1, myY)
                && !hitsOwnBody(myX + 1, myY, myBody)
                && !hitsEnemyBody(myX + 1, myY, allSnakes, myId);

        boolean downSafe = board.isInsideBoard(myX, myY - 1)
                && !hitsOwnBody(myX, myY - 1, myBody)
                && !hitsEnemyBody(myX, myY - 1, allSnakes, myId);

        boolean leftSafe = board.isInsideBoard(myX - 1, myY)
                && !hitsOwnBody(myX - 1, myY, myBody)
                && !hitsEnemyBody(myX - 1, myY, allSnakes, myId);

        // Even printen om te zien welke kant veilig is
        System.out.println("upSafe = " + upSafe);
        System.out.println("rightSafe = " + rightSafe);
        System.out.println("downSafe = " + downSafe);
        System.out.println("leftSafe = " + leftSafe);


        // Ik stop alle veilige moves in een lijst
        List<String> safeMoves = new ArrayList<>();

        // de eerste veilige richting
        if (upSafe) {
            safeMoves.add("up");
        }

        if (rightSafe) {
            safeMoves.add("right");
        }

        if (downSafe) {
            safeMoves.add("down");
        }

        if (leftSafe) {
            safeMoves.add("left");
        }

        // Als er veilige moves zijn, kies ik random uit alleen veilige moves
        if (!safeMoves.isEmpty()) {
            Random random = new Random();
            int randomIndex = random.nextInt(safeMoves.size());

            return safeMoves.get(randomIndex);
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

    private boolean hitsEnemyBody(int newX, int newY, List<Map<String, Object>> allSnakes, String myId) {
        // Als er geen snakes zijn, kan ik ook niet tegen een andere snake botsen
        if (allSnakes == null) {
            return false;
        }

        // Ik loop door alle snakes op het bord heen
        for (Map<String, Object> snake : allSnakes) {
            String snakeId = (String) snake.get("id");

            // Als dit mijn eigen snake is, sla ik die over
            // Mijn eigen body check ik al met hitsOwnBody(...)
            if (snakeId.equals(myId)) {
                continue;
            }

            // Body van de andere snake ophalen
            List<Map<String, Object>> enemyBody = (List<Map<String, Object>>) snake.get("body");

            if (enemyBody == null) {
                continue;
            }

            // Ik loop door elk stukje van de enemy body heen
            for (Map<String, Object> bodyPart : enemyBody) {
                int bodyX = (int) bodyPart.get("x");
                int bodyY = (int) bodyPart.get("y");

                // Als mijn nieuwe plek hetzelfde is als een enemy body stukje,
                // dan bots ik tegen een andere snake
                if (newX == bodyX && newY == bodyY) {
                    return true;
                }
            }
        }

        // Geen enemy body stukje geraakt
        return false;
    }
}