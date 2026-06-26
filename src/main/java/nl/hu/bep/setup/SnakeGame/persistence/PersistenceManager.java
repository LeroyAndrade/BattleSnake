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
    // Voor Render/Azure zou dit bijvoorbeeld /home/battlesnake-data.obj zijn.
    // Voor lokaal kan dit ook werken als /home bestaat en schrijfbaar is.

    private static final Path DATA_STORAGE = Path.of("/home/battlesnake-data.obj");

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
            // Als het bestand niet bestaat, gebruik ik gewoon de standaard AppDataOpslag.
            if (Files.exists(DATA_STORAGE)) {

                // Ik open een InputStream naar het bestand.
                InputStream is = Files.newInputStream(DATA_STORAGE);

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

    public static void saveAppDataToFile() {
        try {
            // Ik haal de map op waarin battlesnake-data.obj moet komen te staan.
            Path directory = DATA_STORAGE.getParent();

            // Als de map nog niet bestaat, maak ik deze eerst aan.
            if (!Files.exists(directory)) {
                Files.createDirectories(directory);
            }

            // Ik open een OutputStream naar battlesnake-data.obj.
            // Als het bestand nog niet bestaat, wordt het aangemaakt.
            // Als het bestand al bestaat, wordt het overschreven.
            OutputStream os = Files.newOutputStream(DATA_STORAGE);

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
            // Als het opslaan niet lukt, print ik de foutmelding.
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