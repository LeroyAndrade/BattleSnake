package nl.hu.bep.setup.SnakeGame.Model;

// Testen of Game.chooseMove(...) slimme/veilige keuzes maakt.
// Ik test hier vooral:
// 1. richting food gaan als dat veilig is
// 2. niet door de muur gaan
// 3. niet tegen mijn eigen body gaan
// 4. niet tegen enemy body gaan

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class GameTest {

    @Test
    public void slangGaatRichtingFoodAlsDatVeiligIs() {
        // Arrange: ik maak mijn Game object aan
        Game game = new Game();

        // Mijn hoofd staat op x=5, y=5
        int myX = 5;
        int myY = 5;

        // Mijn body staat onder mijn hoofd
        // Daardoor is rechts gewoon veilig
        List<Map<String, Object>> myBody = List.of(
                Map.of("x", 5, "y", 5),
                Map.of("x", 5, "y", 4),
                Map.of("x", 5, "y", 3)
        );

        // In allSnakes zet ik mijn eigen snake.
        // De id is testID, zodat Game weet welke snake van mij is.
        List<Map<String, Object>> allSnakes = List.of(
                Map.of(
                        "id", "testID",
                        "body", myBody
                )
        );

        // Food staat rechts van mij op x=8, y=5
        // Dus als rechts veilig is, verwacht ik "right"
        List<Map<String, Object>> food = List.of(
                Map.of("x", 8, "y", 5)
        );

        // ik laat de game een move kiezen
        String move = game.chooseMove(
                myX,
                myY,
                11,
                11,
                myBody,
                allSnakes,
                "testID",
                food
        );

        // de slang moet richting food gaan, dus right
        assertEquals("right", move);
    }

    @Test
    public void slangGaatNietDoorDeMuur() {
        Game game = new Game();

        // Mijn hoofd staat helemaal bovenaan het bord
        // Bij een bord van 11 hoog zijn geldige y-waarden 0 t/m 10
        int myX = 5;
        int myY = 10;

        // Mijn body zit onder mijn hoofd
        List<Map<String, Object>> myBody = List.of(
                Map.of("x", 5, "y", 10),
                Map.of("x", 5, "y", 9),
                Map.of("x", 5, "y", 8)
        );

        // Food staat boven de muur.
        // De slang wil misschien naar food, maar up zou buiten het bord zijn.
        List<Map<String, Object>> food = List.of(
                Map.of("x", 5, "y", 12)
        );

        String move = game.chooseMove(
                myX,
                myY,
                11,
                11,
                myBody,
                null,
                "testID",
                food
        );

        // De slang mag 'UP' niet up kiezen, want dan gaat hij door de muur
        assertNotEquals("up", move);
    }

    @Test
    public void slangGaatNietTegenEigenBody() {
        Game game = new Game();

        // Mijn hoofd staat op x=1, y=1
        int myX = 1;
        int myY = 1;

        // Up, right en down zijn geblokkeerd door mijn eigen body.
        // Daardoor blijft alleen left over.
        List<Map<String, Object>> myBody = List.of(
                Map.of("x", 1, "y", 1),
                Map.of("x", 1, "y", 2),
                Map.of("x", 2, "y", 1),
                Map.of("x", 1, "y", 0)
        );

        // Ik geef mijn eigen snake mee in allSnakes
        List<Map<String, Object>> allSnakes = List.of(
                Map.of(
                        "id", "testID",
                        "body", myBody
                )
        );

        // Food staat ergens anders, maar de slang mag niet door zijn body heen
        List<Map<String, Object>> food = List.of(
                Map.of("x", 5, "y", 5)
        );

        String move = game.chooseMove(
                myX,
                myY,
                11,
                11,
                myBody,
                allSnakes,
                "testID",
                food
        );

        // Omdat up/right/down geblokkeerd zijn, verwacht ik left
        assertEquals("left", move);
    }

    @Test
    public void slangGaatNietTegenEnemyBody() {
        Game game = new Game();

        // Mijn hoofd staat op x=5, y=5
        int myX = 5;
        int myY = 5;

        // Mijn eigen body blokkeert down
        List<Map<String, Object>> myBody = List.of(
                Map.of("x", 5, "y", 5),
                Map.of("x", 5, "y", 4)
        );

        // Enemy body staat rechts van mij op x=6, y=5
        List<Map<String, Object>> enemyBody = List.of(
                Map.of("x", 6, "y", 5)
        );

        // allSnakes bevat mijn snake en een enemy snake
        List<Map<String, Object>> allSnakes = List.of(
                Map.of(
                        "id", "testID",
                        "body", myBody
                ),
                Map.of(
                        "id", "enemyID",
                        "body", enemyBody
                )
        );

        // Food staat rechts, maar rechts is geblokkeerd door enemy body
        List<Map<String, Object>> food = List.of(
                Map.of("x", 8, "y", 5)
        );

        String move = game.chooseMove(
                myX,
                myY,
                11,
                11,
                myBody,
                allSnakes,
                "testID",
                food
        );

        // De slang mag niet right kiezen, want daar zit enemy body
        assertNotEquals("right", move);
    }
}