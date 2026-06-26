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

}