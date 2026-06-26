//GET /restservices/snake
//PATCH /restservices/snake
//Om  snake.html het uiterlijk op te halen en aanpassen.
package nl.hu.bep.setup.SnakeGame.webservices;

import nl.hu.bep.setup.SnakeGame.Model.SnakeSettings;
import nl.hu.bep.setup.SnakeGame.persistence.PersistenceManager;

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
        return Response.ok(PersistenceManager.getAppDataOpslag().getSnakeSettings()).build();
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

        SnakeSettings settings = PersistenceManager.getAppDataOpslag().getSnakeSettings();

        if (body.containsKey("color")) {
            settings.setColor(body.get("color"));
        }

        if (body.containsKey("head")) {
            settings.setHead(body.get("head"));
        }

        if (body.containsKey("tail")) {
            settings.setTail(body.get("tail"));
        }

        if (body.containsKey("version")) {
            settings.setVersion(body.get("version"));
        }

        PersistenceManager.saveAppDataToFile();

        return Response.ok(settings).build();
    }
}