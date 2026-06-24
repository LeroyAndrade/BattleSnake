// https://docs.battlesnake.com/api/webhooks

package nl.hu.bep.setup.SnakeGame.webservices;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Map;

@Path("/")
public class BattlesnakeResource {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, String> info() {
        // Deze response gebruikt Battlesnake om API-versie en uiterlijk te controleren.
        return Map.of(
                "apiversion", "1",
                "author", "LeroyAndrade",
                "color", "#F1F1F1",
                "head", "default",
                "tail", "default",
                "version", "000.000.001-beta"
        );
    }

    // Response wordt door Battlesnake genegeerd,  200 OK is erg belangrijk.
    @POST
    @Path("start")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, String> start(String body) {
        return Map.of();
    }

    @POST
    @Path("move")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, String> move(String body) {
        // omhoog bewegen.
        return Map.of("move", "up");
    }

    @POST
    @Path("end")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, String> end(String body) {
        return Map.of();
    }
}