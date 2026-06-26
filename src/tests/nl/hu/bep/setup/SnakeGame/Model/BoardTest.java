package nl.hu.bep.setup.SnakeGame.Model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BoardTest {

    @Test
    public void positieBinnenBordGeeftTrue() {
        Board board = new Board(11, 11);

        assertTrue(board.isInsideBoard(0, 0));
        assertTrue(board.isInsideBoard(5, 5));
        assertTrue(board.isInsideBoard(10, 10));
    }

    @Test
    public void positieBuitenBordGeeftFalse() {
        Board board = new Board(11, 11);

        assertFalse(board.isInsideBoard(-1, 0));
        assertFalse(board.isInsideBoard(0, -1));
        assertFalse(board.isInsideBoard(11, 5));
        assertFalse(board.isInsideBoard(5, 11));
    }
}