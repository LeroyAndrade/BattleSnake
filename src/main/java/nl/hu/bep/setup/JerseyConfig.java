package nl.hu.bep.setup;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

import javax.ws.rs.ApplicationPath;

@ApplicationPath("restservices")
public class JerseyConfig extends ResourceConfig {
    public JerseyConfig() {
        packages("nl.hu.bep.setup.SnakeGame.webservices");
//        ,"nl.hu.bep.shopping.security"
        register(RolesAllowedDynamicFeature.class);

        // Geef aan in welke package je jouw webservices gaat programmeren, bijv.:
        // packages("nl.hu.bep.jouwapplicatie.webservices");
    }
}
