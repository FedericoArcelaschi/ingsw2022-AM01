package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BoardFactoryTest {

    @Test
    void testGetBoard2Player(){
        List<String> users = Arrays.asList("fede","gio");
        Turn turn = new Turn(users);
        Board board1 = BoardFactory.getBoard(users, turn);
        Board board2 = new Board("fede", "gio", turn);
        assertEquals(board2.getnPlayer(), board1.getnPlayer());
        assertEquals(board2.getPlayerUsernames(), board1.getPlayerUsernames());
    }

    @Test
    void testGetBoard3Player(){
        List<String> users = Arrays.asList("fede","gio","lore");
        Turn turn = new Turn(users);
        Board board1 = BoardFactory.getBoard(users, turn);
        Board board2 = new Board("fede", "gio", "lore", turn);
        assertEquals(board2.getnPlayer(), board1.getnPlayer());
        assertEquals(board2.getPlayerUsernames(), board1.getPlayerUsernames());
    }

    @Test
    void testGetBoard4Player(){
        List<String> users = Arrays.asList("fede","gio", "lore", "fede");
        Turn turn = new Turn(users);
        Board board1 = BoardFactory.getBoard(users, turn);
        Board board2 = new Board("fede", "gio", "lore", "fede", turn);
        assertEquals(board2.getnPlayer(), board1.getnPlayer());
        assertEquals(board2.getPlayerUsernames(), board1.getPlayerUsernames());
    }

    @Test
    void testGetBoard5Player(){
        List<String> users = Arrays.asList("fede","gio", "lore", "fede", "fede");
        Turn turn = new Turn(users);
        assertThrows(IllegalArgumentException.class, () -> BoardFactory.getBoard(users, turn));
    }

}