package it.polimi.ingsw.communication.modelData;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.exceptions.NoSuchStudentException;
import it.polimi.ingsw.model.exceptions.NotYourTurnException;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class BoardDataTest {

    @Test
    void gsonTest() throws NotYourTurnException, NoSuchStudentException {
        Board b = BoardFactory.getBoard(Arrays.asList("fede","gio"),new Turn(Arrays.asList("fede","gio")));
        b.playCard("fede", 1);
        b.changePhase();
        b.playCard("gio", 10);
        b.changePhase();
        BoardData bd = DataBuilder.newBoardData("fede", b);
        System.out.println(bd);
    }

}