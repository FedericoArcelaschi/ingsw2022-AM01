package it.polimi.ingsw.server.model.baseLogic;

import it.polimi.ingsw.server.model.baseLogic.Board;
import it.polimi.ingsw.server.model.baseLogic.BoardFactory;
import it.polimi.ingsw.server.model.baseLogic.Turn;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.random.RandomGenerator;

import static org.junit.jupiter.api.Assertions.*;

class BoardFactoryTest {

    private int seed;

    @BeforeEach
    void setUp(){
        seed = 1;
    }

    @Test
    void testGetBoard2Player(){
        List<String> users = Arrays.asList("fede","gio");
        Turn turn = new Turn(users);
        Board board1 = BoardFactory.getBoard(users, false, RandomGenerator.getDefault().nextLong());
        Board board2 = new Board("fede", "gio", turn, seed);
        assertEquals(board2.getCastleMap().keySet().size(), board1.getCastleMap().keySet().size());
    }

    @Test
    void testGetBoard3Player(){
        List<String> users = Arrays.asList("fede","gio","lore");
        Turn turn = new Turn(users);
        Board board1 = BoardFactory.getBoard(users, false, RandomGenerator.getDefault().nextLong());
        Board board2 = new Board("fede", "gio", "lore", turn, seed);
        assertEquals(board2.getCastleMap().keySet().size(), board1.getCastleMap().keySet().size());
    }

    @Test
    void testGetBoard4Player(){
        List<String> users = Arrays.asList("fede","gio", "lore", "fede");
        Turn turn = new Turn(users);
        Board board1 = BoardFactory.getBoard(users, false, RandomGenerator.getDefault().nextLong());
        Board board2 = new Board("fede", "gio", "lore", "fede", turn, seed);
        assertEquals(board2.getCastleMap().keySet().size(), board1.getCastleMap().keySet().size());
    }

    @Test
    void testGetBoard5Player(){
        List<String> users = Arrays.asList("fede","gio", "lore", "fede", "fede");
        Turn turn = new Turn(users);
        assertThrows(IllegalArgumentException.class, () -> BoardFactory.getBoard(users, false, RandomGenerator.getDefault().nextLong()));
    }

}