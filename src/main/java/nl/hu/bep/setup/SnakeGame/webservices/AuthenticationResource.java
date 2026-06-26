//POST /restservices/authentication
//Controleert username/password
//Geeft JWT terug
package nl.hu.bep.setup.SnakeGame.webservices;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;
import nl.hu.bep.setup.SnakeGame.Security.LogonRequest;
import nl.hu.bep.setup.SnakeGame.Security.SnakeUser;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.security.Key;
import java.util.Calendar;
import java.util.Map;

@Path("authentication")
public class AuthenticationResource {
    public static final Key key = MacProvider.generateKey();

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response authenticateUser(LogonRequest request) {
        try {
            // Hier check ik of de username/password klopt
            String role = SnakeUser.validateLogin(request.username, request.password);

            // Als role null is, dan is de login fout
            if (role == null) {
                throw new IllegalArgumentException("Geen geldige login");
            }

            // Als login klopt, maak ik een JWT token aan
            String jwtToken = createToken(request.username, role);

            return Response.ok(Map.of("JWT", jwtToken)).build();
        } catch (JwtException | IllegalArgumentException e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    private String createToken(String username, String role) {
        Calendar expires = Calendar.getInstance();
        expires.add(Calendar.MINUTE, 30);

        return Jwts.builder()
                .setSubject(username)
                .setExpiration(expires.getTime())
                .claim("role", role)
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
    }
}