package nl.hu.bep.setup.SnakeGame.webservices;

import nl.hu.bep.setup.SnakeGame.Model.GameRecord;
import nl.hu.bep.setup.SnakeGame.persistence.PersistenceManager;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Map;

@Path("games")
public class GamesResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getGames() {
        List<GameRecord> games = PersistenceManager.getAppDataOpslag().getGames();

        return Response.ok(games).build();
    }

    @GET
    @RolesAllowed({"user", "admin"})
    @Path("{gameId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getGame(@PathParam("gameId") String gameId) {
        GameRecord game = PersistenceManager.getAppDataOpslag().getGameById(gameId);

        if (game == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(Map.of("error", "Game niet gevonden"))
                    .build();
        }

        return Response.ok(game).build();
    }

    @DELETE
    @RolesAllowed({"user", "admin"})
    @Path("{gameId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteGame(@PathParam("gameId") String gameId) {
        boolean removed = PersistenceManager.getAppDataOpslag().removeGame(gameId);

        if (!removed) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(Map.of("error", "Game niet gevonden"))
                    .build();
        }

        PersistenceManager.saveAppDataToFile();

        return Response.ok(Map.of("message", "Game verwijderd")).build();
    }
}