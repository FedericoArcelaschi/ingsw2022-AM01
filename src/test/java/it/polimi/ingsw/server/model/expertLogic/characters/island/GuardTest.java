package it.polimi.ingsw.server.model.expertLogic.characters.island;

import it.polimi.ingsw.server.model.baseLogic.*;
import it.polimi.ingsw.server.model.exceptions.*;
import it.polimi.ingsw.server.model.expertLogic.ExpertBoard;
import it.polimi.ingsw.server.model.expertLogic.character.costants.CharacterExplanation;
import it.polimi.ingsw.server.model.expertLogic.character.costants.CharacterUtility;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.random.RandomGenerator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class GuardTest { //3° Character

    CharacterExplanation explanation = CharacterExplanation.GUARD;
    private ExpertBoard expertBoard;
    private final String player1 = "Gianpaolo", player2 = "Cugola";
    private List<StudentColor> studentsToAdd;

    @Test
    void playExpertCharacter10times() {
        for (int i = 0; i < 10; i++) playExpertCharacter();
    }

    void setUp() {
        expertBoard = new ExpertBoard(player1, player2, new Turn(List.of(player1, player2)), RandomGenerator.getDefault().nextLong());
        if(!expertBoard.getAvailableCharacters().containsKey(CharacterUtility.GUARD)) {
            setUp();
            return;
        }
        
        playPlanningPhaseFirstPlayer1(expertBoard);

        //this character needs three coins
        studentsToAdd = expertBoard.getCastle("Gianpaolo").getWaitingRoom();
        try {
            expertBoard.moveStudentsToDiningRoom("Gianpaolo", studentsToAdd);
        } catch(Exception e) {
            e.printStackTrace();
            fail();
        }

        try {
            if(expertBoard.getCastle("Gianpaolo").getCoins() < 3)
                setUp();
        } catch (WrongGameModeException e) {
            e.printStackTrace();
            fail();
        }

    }

    void playExpertCharacter() {

        setUp();

        final int islandIndex = 7;
        expertBoard.getIslandList().get(islandIndex).addStudent(studentsToAdd.get(0)); //the professor of this island's student is owned by Gianpaolo
        try{
            expertBoard.playExpertCard(3, islandIndex, List.of());
        } catch (CoinException coinException) {
            playExpertCharacter(); //if in this game there weren't enough coins to pay for the card, will create a new one.
            return;
        } catch (Exception e) {
            e.printStackTrace();
        }
        Team gianpaolosTeam = expertBoard.getCastle(player1).getTeam();
        assertEquals(gianpaolosTeam, expertBoard.getIslandList().get(islandIndex).getOwnership(),
                "the island should be now conquered by Gianpaolo");
    }

    void playPlanningPhaseFirstPlayer1(ExpertBoard expertBoard) {
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
    }
}
