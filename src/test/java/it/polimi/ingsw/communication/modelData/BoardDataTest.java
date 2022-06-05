package it.polimi.ingsw.communication.modelData;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.communication.modelData.expertMode.ExpertBoardData;
import it.polimi.ingsw.server.model.baseLogic.Board;
import it.polimi.ingsw.server.model.baseLogic.BoardFactory;
import it.polimi.ingsw.server.model.baseLogic.StudentColor;
import it.polimi.ingsw.server.model.baseLogic.Turn;
import it.polimi.ingsw.server.model.expertLogic.ExpertBoard;
import it.polimi.ingsw.server.model.expertLogic.character.applyEffect.ApplyEffect;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.random.RandomGenerator;

class BoardDataTest {
    @Test
    void toStringTest() {
        BoardData bd;
        Board b = BoardFactory.getBoard(Arrays.asList("fede","gio"), false);
        bd = ModelDataBuilder.newBoardData("fede", b);
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
                ModelDataBuilder
                .newBoardData("pippo",
                        new Board(
                                "pippo", "pluto",
                                new Turn(List.of("pippo", "pluto")),
                                RandomGenerator.getDefault().nextLong())));
    }

    @Test
    void TeamBackgroundColor() {
        System.out.println("\u001b[40;31m test TEST \u001b[0m");
    }

    @Test
    void CastleDataTest() { //with Maps!!
        BoardData boardData = ModelDataBuilder.newBoardData("pippo", new Board("pippo", "pluto", new Turn(List.of("pippo", "pluto")), RandomGenerator.getDefault().nextLong()));
        String Json = new Gson().toJson(boardData);
        BoardData boardDataDeserialized = new Gson().fromJson(Json, BoardData.class);
        Assertions.assertEquals(boardData, boardDataDeserialized);
    }

    @Test
    void ExpertBoardDataTest() {
        BoardData boardData = ModelDataBuilder.newExpertBoardData("lore",
                new ExpertBoard("lore", "gio", "fede",
                        new Turn(List.of("lore", "gio", "fede")),
                        RandomGenerator.getDefault().nextLong()));

        System.out.println(boardData);
        String Json = new Gson().toJson(boardData);
        BoardData boardDataDeserialized = new Gson().fromJson(Json, ExpertBoardData.class);
        System.out.println(boardDataDeserialized);
    }
}