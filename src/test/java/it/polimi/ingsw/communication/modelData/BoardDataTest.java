package it.polimi.ingsw.communication.modelData;

import com.google.gson.Gson;
import it.polimi.ingsw.server.model.baseLogic.Board;
import it.polimi.ingsw.server.model.baseLogic.BoardFactory;
import it.polimi.ingsw.server.model.baseLogic.StudentColor;
import it.polimi.ingsw.server.model.baseLogic.Turn;
import it.polimi.ingsw.server.model.exceptions.NoSuchStudentException;
import it.polimi.ingsw.server.model.exceptions.PhaseNotRightException;
import it.polimi.ingsw.server.model.exceptions.TooManyStudentsException;
import it.polimi.ingsw.server.model.expertLogic.ExpertBoard;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.random.RandomGenerator;

class BoardDataTest {
    @Test
    void toStringTest() throws PhaseNotRightException, NoSuchStudentException, TooManyStudentsException {
        BoardData bd;
        Board b = BoardFactory.getBoard(Arrays.asList("fede","gio", "pippo"), false, RandomGenerator.getDefault().nextLong());
        b.playCard("fede", 2);
        b.changePhase();
        b.playCard("gio", 3);
        b.changePhase();
        b.playCard("pippo", 4);
        b.changePhase();
        b.moveStudentsToDiningRoom("fede", b.getCastle("fede").getWaitingRoom().subList(0, 3));
        bd = b.getData("fede");
        System.out.println(bd);
    }

    @Test
    void toStringColorTest() {
        StudentColor yellow = StudentColor.YELLOW;
        System.out.println(yellow.toStringColored());
    }

    @Test
    void soutBoardDataTest() {
        System.out.println(
                new Board(
                        "pippo", "pluto",
                        new Turn(List.of("pippo", "pluto")),
                        RandomGenerator.getDefault().nextLong()).getData("pippo"));
    }

    @Test
    void TeamBackgroundColor() {
        System.out.println("backgroundColor test: ");
        System.out.println("\u001b[40;31m test TEST \u001b[0m");
    }

    @Test
    void CastleDataTest() { //with Maps!!
        Board board = new Board("pippo", "pluto", new Turn(List.of("pippo", "pluto")), RandomGenerator.getDefault().nextLong());
        BoardData boardData = board.getData("pippo");
        String Json = new Gson().toJson(boardData);
        BoardData boardDataDeserialized = new Gson().fromJson(Json, BoardData.class);
        Assertions.assertEquals(boardData, boardDataDeserialized);
    }

    @Test
    void ExpertBoardDataTest() {
        Board board = new ExpertBoard("lore", "gio", "fede",
                new Turn(List.of("lore", "gio", "fede")),
                RandomGenerator.getDefault().nextLong());
        BoardData boardData = board.getData("lore");
        String Json = new Gson().toJson(boardData);
        BoardData boardDataDeserialized = new Gson().fromJson(Json, BoardData.class);
        System.out.println(boardDataDeserialized);
        Assertions.assertEquals(boardData, boardDataDeserialized);
    }
}