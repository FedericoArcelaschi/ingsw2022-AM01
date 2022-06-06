package it.polimi.ingsw.communication.modelData;

import com.google.gson.Gson;
import it.polimi.ingsw.server.model.baseLogic.Board;
import it.polimi.ingsw.server.model.baseLogic.BoardFactory;
import it.polimi.ingsw.server.model.baseLogic.Turn;
import it.polimi.ingsw.server.model.exceptions.NoSuchStudentException;
import it.polimi.ingsw.server.model.exceptions.NotYourTurnException;
import it.polimi.ingsw.server.model.exceptions.TooManyStudentsException;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

class BoardDataTest {


    @Test
    void toStringTest() throws NotYourTurnException, NoSuchStudentException, TooManyStudentsException {
        BoardData bd;
        Board b = BoardFactory.getBoard(Arrays.asList("fede","gio"), false);
        bd = ModelDataBuilder.newBoardData("fede", b);
        System.out.println(new Gson().toJson(bd.myCastle()));
    }

}