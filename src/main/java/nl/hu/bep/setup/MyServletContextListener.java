//maak je lokaal users aan:

package nl.hu.bep.setup;
import nl.hu.bep.setup.SnakeGame.Security.SnakeUser;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;



@WebListener
public class MyServletContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("App aan het opstarten");

        // Lokale users aanmaken bij het starten van de applicatie.
        // Dit is hetzelfde idee als lokaal testdata klaarzetten.
        SnakeUser.addUser(new SnakeUser("leroy", "password123", "user"));
        SnakeUser.addUser(new SnakeUser("admin", "admin123", "admin"));

        System.out.println("Snake users zijn aangemaakt");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("App aan het stoppen");
    }
}

