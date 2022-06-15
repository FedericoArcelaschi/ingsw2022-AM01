package it.polimi.ingsw.server.model.expertLogic.characters.island;

import it.polimi.ingsw.server.model.baseLogic.*;
import it.polimi.ingsw.server.model.exceptions.CoinException;
import it.polimi.ingsw.server.model.exceptions.StudentException;
import it.polimi.ingsw.server.model.exceptions.TooManyStudentsException;
import it.polimi.ingsw.server.model.expertLogic.ExpertBoard;
import it.polimi.ingsw.server.model.expertLogic.ExpertIsland;
import it.polimi.ingsw.server.model.expertLogic.character.applyEffect.ParametersForCharacter;
import it.polimi.ingsw.server.model.expertLogic.character.charTypes.StandardCharacter;
import it.polimi.ingsw.server.model.expertLogic.character.costants.CharacterExplanation;
import it.polimi.ingsw.server.model.expertLogic.influence.ExpertInfluence;
import it.polimi.ingsw.server.model.expertLogic.influence.professor.ExpertProfessors;

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
        } catch (CoinException | IllegalArgumentException e) {
            playExpertCharacter();//if in this game there weren't enough coins to pay for the card, will create a new one.
            return;
        } catch (Exception e) {
            e.printStackTrace();
        }
        Team gianPaolosTeam = expertBoard.getCastle(player1).getTeam();
        assertEquals(gianPaolosTeam, expertBoard.getIslandList().get(islandIndex).getOwnership(),
                "the island should be now conquered by Gianpaolo");
    }

 @Test
    void playExpertCharacter10times() {
        for (int i = 0; i < 10; i++) playExpertCharacter();
    }
}
