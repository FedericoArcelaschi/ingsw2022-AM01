package it.polimi.ingsw.communication.modelData;

import it.polimi.ingsw.server.model.baseLogic.Board;
import it.polimi.ingsw.server.model.baseLogic.BoardFactory;
import it.polimi.ingsw.server.model.baseLogic.StudentColor;
import it.polimi.ingsw.server.model.baseLogic.Turn;
import it.polimi.ingsw.server.model.exceptions.NoSuchStudentException;
import it.polimi.ingsw.server.model.exceptions.NotYourTurnException;
import it.polimi.ingsw.server.model.exceptions.TooManyStudentsException;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.random.RandomGenerator;

class BoardDataTest {


    @Test
    void toStringTest() throws NotYourTurnException, NoSuchStudentException, TooManyStudentsException {
        BoardData bd;
        Board b = BoardFactory.getBoard(Arrays.asList("fede","gio"), false);
        bd = ModelDataBuilder.newBoardData("fede", b);
        System.out.println(bd);
    }

    @Test
    void toStringColorTest() {
        StudentColor red = StudentColor.RED;
        System.out.println(red + " altro22");
    }

    @Test
    void soutBoardDataTest() {
        System.out.println(ModelDataBuilder.newBoardData("pippo", new Board("pippo", "pluto", new Turn(List.of("pippo", "pluto")), RandomGenerator.getDefault().nextLong())));
    }

    @Test
    void TeamBackgroundColor() {
        System.out.println("\u001b[40;31m test TEST \u001b[0m");
    }
}