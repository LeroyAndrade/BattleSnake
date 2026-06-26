package nl.hu.bep.setup.SnakeGame.persistence;

import nl.hu.bep.setup.SnakeGame.Model.AppDataOpslag;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.io.ObjectOutputStream;

import java.nio.file.Files;
import java.nio.file.Path;

@WebListener
public class PersistenceManager implements ServletContextListener {

    // Hier geef ik aan waar mijn battlesnake-data.obj bestand opgeslagen wordt.
    // Voor Render/Azure zou dit /home/battlesnake-data.obj zijn.
    private static final Path BATTLESNAKE_STORAGE = Path.of("/home/battlesnake-data.obj");

    private static AppDataOpslag appDataOpslag = new AppDataOpslag();

    public static AppDataOpslag getAppDataOpslag() {
        return appDataOpslag;
    }

    public static void setAppDataOpslag(AppDataOpslag nieuweAppDataOpslag) {
        appDataOpslag = nieuweAppDataOpslag;
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("App aan het opstarten");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("App aan het stoppen");
    }
}