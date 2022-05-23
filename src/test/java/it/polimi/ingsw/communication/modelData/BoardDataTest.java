package it.polimi.ingsw.communication.modelData;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.exceptions.NoSuchStudentException;
import it.polimi.ingsw.model.exceptions.NotYourTurnException;
import it.polimi.ingsw.model.exceptions.TooManyStudentsException;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BoardDataTest {


    @Test
    void toStringTest() throws NotYourTurnException, NoSuchStudentException, TooManyStudentsException {
        BoardData bd;
        Board b = BoardFactory.getBoard(Arrays.asList("fede","gio"),new Turn(Arrays.asList("fede","gio")), 1);
        bd = DataBuilder.newBoardData("fede", b);
        System.out.println(bd);
    }

}