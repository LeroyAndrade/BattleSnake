// https://docs.battlesnake.com/api/webhooks

package nl.hu.bep.setup.SnakeGame.webservices;
import nl.hu.bep.setup.SnakeGame.Model.Game;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;

@Path("/")
public class BattlesnakeResource {
// http://localhost:8082/restservices
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response info() {
        // Deze response gebruikt Battlesnake om API-versie en uiterlijk te controleren.
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
        // Response wordt door Battlesnake genegeerd.
        // 200 OK is hier vooral belangrijk.
        System.out.println("START body = " + body);

        return Response.ok(Map.of()).build();
    }

    @POST
    @Path("move")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response move(Map<String, Object> body) {
        if (body == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("error", "Geen JSON body meegestuurd"))
                    .build();
        }

        Map<String, Object> board = (Map<String, Object>) body.get("board");
        Map<String, Object> you = (Map<String, Object>) body.get("you");
        Map<String, Object> head = (Map<String, Object>) you.get("head");

        int boardWidth = (int) board.get("width");
        int boardHeight = (int) board.get("height");

        int myX = (int) head.get("x");
        int myY = (int) head.get("y");

        Game game = new Game();
        String move = game.chooseMove(myX, myY, boardWidth, boardHeight);

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
        // Response wordt door Battlesnake genegeerd.
        // 200 OK is hier vooral belangrijk.
        System.out.println("END body = " + body);

        return Response.ok(Map.of()).build();
    }
}