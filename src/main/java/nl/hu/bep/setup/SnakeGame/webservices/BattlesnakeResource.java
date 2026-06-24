// https://docs.battlesnake.com/api/webhooks

package nl.hu.bep.setup.SnakeGame.webservices;

import nl.hu.bep.setup.SnakeGame.Model.Game;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Map;

@Path("/")
public class BattlesnakeResource {

    // http://localhost:8082/restservices
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response info() {
        // Deze response gebruikt Battlesnake om de API-versie en de snake layout te controleren.
        return Response.ok(Map.of(
                "apiversion", "1",
                "author", "LeroyAndrade",
                "color", "#F1F1F1",
                "head", "default",
                "tail", "default",
                "version", "000.000.001-beta"
        )).build();
    }

    @POST
    @Path("start")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response start(Map<String, Object> body) {
        // Battlesnake verwacht vooral een 200 OK.
        // De response zelf wordt hier niet echt gebruikt.
        System.out.println("START body = " + body);

        return Response.ok(Map.of()).build();
    }

    @POST
    @Path("move")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response move(Map<String, Object> body) {
        // Zonder body kan ik geen move bepalen.
        if (body == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("error", "Geen JSON body meegestuurd"))
                    .build();
        }

        // Algemene game info uitlezen.
        // Dit gebruik ik nu vooral voor logging/debugging.
        Map<String, Object> gameMap = (Map<String, Object>) body.get("game");

        if (gameMap != null) {
            String gameId = (String) gameMap.get("id");
            String map = (String) gameMap.get("map");
            Object timeout = gameMap.get("timeout");

            System.out.println("Game id = " + gameId);
            System.out.println("Map = " + map);
            System.out.println("Timeout = " + timeout);
        }

        // Board en eigen snake uit de body halen.
        Map<String, Object> board = (Map<String, Object>) body.get("board");
        Map<String, Object> you = (Map<String, Object>) body.get("you");

        if (board == null || you == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("error", "board of you ontbreekt in body"))
                    .build();
        }

        // Mijn eigen id gebruik ik om mezelf te herkennen tussen alle snakes.
        String myId = (String) you.get("id");

        // Head is mijn huidige positie.
        Map<String, Object> head = (Map<String, Object>) you.get("head");

        if (head == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("error", "head ontbreekt in you"))
                    .build();
        }

        int boardWidth = (int) board.get("width");
        int boardHeight = (int) board.get("height");

        int myX = (int) head.get("x");
        int myY = (int) head.get("y");

        // Mijn body gebruik ik om niet tegen mezelf te botsen.
        List<Map<String, Object>> myBody = (List<Map<String, Object>>) you.get("body");

        // Alle snakes op het bord, inclusief mezelf.
        // Game gebruikt myId om mijn eigen snake over te slaan bij enemy checks.
        List<Map<String, Object>> allSnakes = (List<Map<String, Object>>) board.get("snakes");

        // Even printen om te zien of er andere snakes zijn.
        if (allSnakes != null) {
            for (Map<String, Object> snake : allSnakes) {
                String snakeId = (String) snake.get("id");

                if (!snakeId.equals(myId)) {
                    System.out.println("Andere snake gevonden: " + snake.get("name"));
                }
            }
        }

        Game gameLogic = new Game();

        String move = gameLogic.chooseMove(
                myX,
                myY,
                boardWidth,
                boardHeight,
                myBody,
                allSnakes,
                myId
        );

        System.out.println("Mijn hoofd x = " + myX);
        System.out.println("Mijn hoofd y = " + myY);
        System.out.println("Gekozen move = " + move);

        return Response.ok(Map.of("move", move)).build();
    }

    @POST
    @Path("end")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response end(Map<String, Object> body) {
        // Battlesnake verwacht vooral een 200 OK.
        // De response zelf wordt hier niet echt gebruikt.
        System.out.println("END body = " + body);

        return Response.ok(Map.of()).build();
    }

    // Check alle snakes via Bruno.
    // Dit endpoint wordt niet door Battlesnake zelf gebruikt.
    @POST
    @Path("debug/snakes")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response debugSnakes(Map<String, Object> body) {
        if (body == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("error", "Geen JSON body meegestuurd"))
                    .build();
        }

        Map<String, Object> board = (Map<String, Object>) body.get("board");
        Map<String, Object> you = (Map<String, Object>) body.get("you");

        if (board == null || you == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("error", "board of you ontbreekt in body"))
                    .build();
        }

        String myId = (String) you.get("id");
        List<Map<String, Object>> allSnakes = (List<Map<String, Object>>) board.get("snakes");

        int amountOfSnakes = 0;
        int amountOfOtherSnakes = 0;

        if (allSnakes != null) {
            for (Map<String, Object> snake : allSnakes) {
                String snakeId = (String) snake.get("id");
                String snakeName = (String) snake.get("name");

                System.out.println("Snake in board:");
                System.out.println("naam = " + snakeName);
                System.out.println("id = " + snakeId);

                // Als de id niet hetzelfde is als mijn id, dan = andere snake de vijand
                if (!snakeId.equals(myId)) {
                    System.out.println("Andere snake gevonden: " + snakeName);
                } else {
                    System.out.println("Dit ben ik zelf");
                }
            }

            return Response.ok(Map.of(
                    "myId", myId,
                    "amountOfSnakes", amountOfSnakes,
                    "amountOfOtherSnakes", amountOfOtherSnakes
            )).build();
        }
        return null;
    }
}