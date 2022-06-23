package it.polimi.ingsw.server.model.expertLogic.characters.island;

import it.polimi.ingsw.server.model.baseLogic.Card;
import it.polimi.ingsw.server.model.baseLogic.StudentColor;
import it.polimi.ingsw.server.model.baseLogic.Turn;
import it.polimi.ingsw.server.model.exceptions.*;
import it.polimi.ingsw.server.model.expertLogic.ExpertBoard;
import it.polimi.ingsw.server.model.expertLogic.ExpertIsland;
import it.polimi.ingsw.server.model.expertLogic.character.costants.CharacterExplanation;
import it.polimi.ingsw.server.model.expertLogic.character.costants.CharacterUtility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.random.RandomGenerator;

import static org.junit.jupiter.api.Assertions.*;

public class WitchTest { //5° character

    CharacterExplanation explanation = CharacterExplanation.WITCH;

    private String player1 = "Amico", player2 = "Frizz";
    private ExpertBoard expertBoard;

    @BeforeEach
    void setUp() {
        expertBoard = new ExpertBoard(player1, player2, new Turn(List.of(player1, player2)), RandomGenerator.getDefault().nextLong());
        if(!expertBoard.getAvailableCharacters().containsKey(CharacterUtility.WITCH)) {
            setUp();
            return;
        }
        playPlanningPhaseFirstPlayer1();
        moveStudentsToWaitingRoom();
    }

    private void moveStudentsToWaitingRoom() {
        List<StudentColor> studentsToAdd = expertBoard.getCastle(player1).getWaitingRoom();
        try {
            expertBoard.moveStudentsToDiningRoom(player1, studentsToAdd);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
        try {
            if(expertBoard.getCastle(player1).getCoins() < 2) {
                setUp();
            }
        } catch (WrongGameModeException e) {
            e.printStackTrace();
            return;
        }

    }

    @Test
    void playExpertCardTest() {
        final int islandIndex = 2;

        try {
            expertBoard.playExpertCard(
                    CharacterUtility.WITCH.getId(),
                    islandIndex,
                    List.of());
        } catch (StudentException | PhaseNotRightException | CoinException e) {
            e.printStackTrace();
            fail();
        }
        ExpertIsland blockedIsland = (ExpertIsland) expertBoard.getIslandList().get(islandIndex);
        assertTrue(blockedIsland.isBlocked());
    }

    void playPlanningPhaseFirstPlayer1() {
        try {
            expertBoard.playCard(player1, 5);
            expertBoard.changePhase();
            expertBoard.playCard(player2, 8);
        } catch (PhaseNotRightException e) {
            throw new RuntimeException(e);
        }
        expertBoard.getTurn().addCard(player1, new Card(5));
        expertBoard.getTurn().addCard(player2, new Card(8));
        expertBoard.getTurn().changePhase();
        //here is in student phase
    }
}
