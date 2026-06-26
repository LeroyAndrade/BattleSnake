
//Zorgt dat Jersey weet wie is ingelogd en welke rol die user heeft.

//Zorgt dat Jersey weet wie is ingelogd en welke rol die user heeft.

//Leest Authorization: Bearer <JWT>
//Controleert JWT
//Zet user in SecurityContext

package nl.hu.bep.setup.SnakeGame.Security;

import javax.ws.rs.core.SecurityContext;
import java.security.Principal;

public class MySecurityContext implements SecurityContext {
    private SnakeUser user;
    private String scheme;

    public MySecurityContext(SnakeUser user, String scheme) {
        this.user = user;
        this.scheme = scheme;
    }

    @Override
    public Principal getUserPrincipal() {
        return this.user;
    }

    @Override
    public boolean isUserInRole(String roleName) {
        if (user == null) {
            return false;
        }

        if (user.getRole() == null) {
            return false;
        }

        System.out.printf("%s equals %s%n", roleName, user.getRole());

        return roleName.equals(user.getRole());
    }

    @Override
    public boolean isSecure() {
        return "https".equals(this.scheme);
    }

    @Override
    public String getAuthenticationScheme() {
        return SecurityContext.BASIC_AUTH;
    }
}