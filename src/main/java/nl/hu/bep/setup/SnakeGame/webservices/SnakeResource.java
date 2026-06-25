//GET /restservices/snake
//PATCH /restservices/snake
//Om  snake.html het uiterlijk op te halen en aanpassen.
package nl.hu.bep.setup.SnakeGame.webservices;

import nl.hu.bep.setup.SnakeGame.Model.SnakeSettings;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;

@Path("snake")
public class SnakeResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSnake() {
        // Huidige snake metadata teruggeven aan snake.html
        return Response.ok(SnakeSettings.getCurrent()).build();
    }

    @PATCH
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateSnake(Map<String, String> body) {
        if (body == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("error", "Geen JSON body meegestuurd"))
                    .build();
        }

        SnakeSettings settings = SnakeSettings.getCurrent();

        // Alleen aanpassen als het veld is meegestuurd
        if (body.containsKey("color")) {
            settings.setColor(body.get("color"));
        }

        if (body.containsKey("head")) {
            settings.setHead(body.get("head"));
        }

        if (body.containsKey("tail")) {
            settings.setTail(body.get("tail"));
        }

        return Response.ok(settings).build();
    }
}