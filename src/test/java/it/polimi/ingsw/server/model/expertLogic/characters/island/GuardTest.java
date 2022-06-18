package it.polimi.ingsw.server.model.expertLogic.characters.island;

import it.polimi.ingsw.server.model.baseLogic.StudentColor;
import it.polimi.ingsw.server.model.baseLogic.Island;
import it.polimi.ingsw.server.model.baseLogic.Team;
import it.polimi.ingsw.server.model.baseLogic.Turn;
import it.polimi.ingsw.server.model.exceptions.*;
import it.polimi.ingsw.server.model.expertLogic.ExpertBoard;
import it.polimi.ingsw.server.model.expertLogic.ExpertIsland;
import it.polimi.ingsw.server.model.expertLogic.character.costants.CharacterExplanation;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.random.RandomGenerator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class GuardTest { //3° Character

    CharacterExplanation explanation = CharacterExplanation.GUARD;

    void playExpertCharacter() {
        String player1 = "Gianpaolo", player2 = "Cugola";
        ExpertBoard expertBoard = new ExpertBoard(player1, player2, new Turn(List.of(player1, player2)), RandomGenerator.getDefault().nextLong());
        expertBoard.extract4CharacterTesting(3);
        //this character needs three coins
        List<StudentColor> studentsToAdd = expertBoard.getCastle("Gianpaolo").getWaitingRoom();
        try {
            expertBoard.moveStudentsToDiningRoom("Gianpaolo", studentsToAdd);
        } catch(Exception e) {
            fail();
        }

        int islandIndex
                = expertBoard.getIslandList().indexOf(new ExpertIsland(new Island(studentsToAdd.get(0)))); //the professor of this island's student is owned by Gianpaolo
        try{
            expertBoard.playExpertCard(3, islandIndex, List.of());
        } catch (CoinException coinException) {
            playExpertCharacter();//if in this game there weren't enough coins to pay for the card, will create a new one.
            return;
        } catch (Exception e) {
            e.printStackTrace();
        }
        Team gianpaolosTeam = expertBoard.getCastle(player1).getTeam();
        assertEquals(gianpaolosTeam, expertBoard.getIslandList().get(islandIndex).getOwnership(),
                "the island should be now conquered by Gianpaolo");
    }

 @Test
    void playExpertCharacter10times() {
        for (int i = 0; i < 10; i++) playExpertCharacter();
    }
}
