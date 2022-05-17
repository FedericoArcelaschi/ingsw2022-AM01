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
        //Planning
        b.playCard("fede", 1);
        b.changePhase();
        b.playCard("gio", 8);
        b.changePhase();
        bd = DataBuilder.newBoardData("fede", b);
        System.out.println(bd);
        //Fede's turn
        b.moveStudentToDiningRoom("fede", Arrays.asList(Color.PINK, Color.PINK));
        b.moveStudentToIsland("fede", 1, List.of(Color.PINK));
        b.changePhase();
        b.moveMotherNature(1);
        b.changePhase();
        b.chooseCloud("fede",1);
        b.changePhase();
        //Gio's turn
        for (int i = 0; i < 3; i++)
            b.moveStudentToDiningRoom("gio", List.of(Color.YELLOW));
        b.changePhase();
        b.moveMotherNature(2);
        bd = DataBuilder.newBoardData("fede", b);
        System.out.println(bd);
    }

}