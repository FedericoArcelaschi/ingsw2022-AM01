package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BoardFactoryTest {

    @Test
    void testGetBoard(){
        List<String> users = Arrays.asList("fede","gio");
        Turn turn = new Turn(users);
        Board board1 = BoardFactory.getBoard(users, turn);
        Board board2 = new Board("fede", "gio", turn);
        assertEquals(board2.getnPlayer(), board1.getnPlayer());
        assertEquals(board2.getPlayerUsernames(), board1.getPlayerUsernames());
    }

}