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

                InputStream is = Files.newInputStream(BATTLESNAKE_STORAGE);
                ObjectInputStream ois = new ObjectInputStream(is);

                AppDataOpslag loadedAppDataOpslag = (AppDataOpslag) ois.readObject();

                PersistenceManager.setAppDataOpslag(loadedAppDataOpslag);

                ois.close();
                is.close();

                System.out.println("AppDataOpslag is ingeladen uit bestand.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static synchronized void saveAppDataToFile() {
        try {
            // Ik haal de map op waarin battlesnake-data.obj moet komen te staan.
            Path directory = BATTLESNAKE_STORAGE.getParent();

            // Als de map nog niet bestaat, maak ik deze eerst aan.
            if (!Files.exists(directory)) {
                Files.createDirectories(directory);
            }

            // Ik open een OutputStream naar battlesnake-data.obj.
            OutputStream os = Files.newOutputStream(BATTLESNAKE_STORAGE);

            // Met ObjectOutputStream kan ik een compleet Java-object opslaan.
            ObjectOutputStream oos = new ObjectOutputStream(os);

            // Ik haal de huidige AppDataOpslag op.
            AppDataOpslag appDataOpslagToSave = PersistenceManager.getAppDataOpslag();

            // Ik schrijf de huidige AppDataOpslag weg naar het bestand.
            oos.writeObject(appDataOpslagToSave);

            // Ik sluit de streams weer.
            oos.close();
            os.close();

            System.out.println("AppDataOpslag is opgeslagen in bestand.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // Deze methode wordt uitgevoerd wanneer de applicatie opstart.
        System.out.println("App aan het opstarten");
        System.out.println("Data wordt ingeladen");

        // Bij het opstarten probeer ik de AppDataOpslag uit het bestand te laden.
        PersistenceManager.loadAppDataFromFile();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // Deze methode wordt uitgevoerd wanneer de applicatie stopt.
        System.out.println("App aan het stoppen");
        System.out.println("Data wordt opgeslagen");

        // Bij het stoppen sla ik de huidige AppDataOpslag op in het bestand.
        PersistenceManager.saveAppDataToFile();
    }
}