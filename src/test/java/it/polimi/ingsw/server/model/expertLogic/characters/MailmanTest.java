package it.polimi.ingsw.server.model.expertLogic.characters;

import it.polimi.ingsw.server.model.baseLogic.Turn;
import it.polimi.ingsw.server.model.baseLogic.TurnPhase;
import it.polimi.ingsw.server.model.exceptions.CoinException;
import it.polimi.ingsw.server.model.exceptions.PhaseNotRightException;
import it.polimi.ingsw.server.model.exceptions.StudentException;
import it.polimi.ingsw.server.model.expertLogic.ExpertBoard;
import it.polimi.ingsw.server.model.expertLogic.character.costants.CharacterExplanation;
import it.polimi.ingsw.server.model.expertLogic.character.costants.CharacterUtility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.random.RandomGenerator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class MailmanTest { //4° character

    CharacterExplanation explanation = CharacterExplanation.MAILMAN;

    private final String player1 = "John";
    private final String player2 = "Travolta";
    private ExpertBoard expertBoard;


    @BeforeEach
    void setUp() {
        expertBoard = new ExpertBoard(player1, player2, new Turn(List.of(player1, player2)), RandomGenerator.getDefault().nextLong());
        if(!expertBoard.getAvailableCharacters().containsKey(CharacterUtility.MAILMAN)) {
            setUp();
            return;
        }
        try {
            expertBoard.playCard(player1, 5);
            expertBoard.changePhase();
            expertBoard.playCard(player2, 7);
            expertBoard.changePhase();
        } catch (PhaseNotRightException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void playExpertCardTest() {
        try {
            expertBoard.playExpertCard(4, null, List.of());
        } catch (StudentException | CoinException | PhaseNotRightException e) {
            e.printStackTrace();
            fail();
        }

        expertBoard.changePhase();
        assertEquals(TurnPhase.MOTHERNATURE, expertBoard.getTurn().getCurrentPhase());

        try {
            expertBoard.moveMotherNature(3 + 2);
        } catch (PhaseNotRightException e) {
            e.printStackTrace();
            fail();
        }
    }
}