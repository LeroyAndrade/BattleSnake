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

    public static void loadAppDataFromFile() {
        try {
            // Ik controleer eerst of het bestand battlesnake-data.obj bestaat.
            // Als het bestand niet bestaat, hoeft er niks ingeladen te worden.
            if (Files.exists(BATTLESNAKE_STORAGE)) {

                // Ik open een InputStream naar het bestand.
                InputStream is = Files.newInputStream(BATTLESNAKE_STORAGE);

                // Met ObjectInputStream kan ik een opgeslagen Java-object teruglezen.
                ObjectInputStream ois = new ObjectInputStream(is);

                // Ik lees het object uit het bestand en cast het terug naar AppDataOpslag.
                AppDataOpslag loadedAppDataOpslag = (AppDataOpslag) ois.readObject();

                // Ik zet de ingeladen AppDataOpslag als de huidige data.
                PersistenceManager.setAppDataOpslag(loadedAppDataOpslag);

                // Ik sluit de streams weer.
                ois.close();
                is.close();

                System.out.println("AppDataOpslag is ingeladen uit bestand.");
            }
        } catch (Exception e) {
            // Als het laden niet lukt, print ik de foutmelding.
            // De standaard AppDataOpslag blijft dan gewoon gebruikt worden.
            e.printStackTrace();
        }
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