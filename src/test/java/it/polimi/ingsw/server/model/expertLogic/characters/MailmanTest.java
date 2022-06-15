package it.polimi.ingsw.server.model.expertLogic.characters;

import it.polimi.ingsw.server.model.baseLogic.*;
import it.polimi.ingsw.server.model.exceptions.CoinException;
import it.polimi.ingsw.server.model.exceptions.NotYourTurnException;
import it.polimi.ingsw.server.model.exceptions.PhaseNotRightException;
import it.polimi.ingsw.server.model.exceptions.StudentException;
import it.polimi.ingsw.server.model.expertLogic.ExpertBoard;
import it.polimi.ingsw.server.model.expertLogic.character.costants.CharacterExplanation;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.random.RandomGenerator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class MailmanTest { //4° character

    CharacterExplanation explanation = CharacterExplanation.MAILMAN;

    private String player1 = "Jhon", player2 = "Travolta";
    private ExpertBoard expertBoard;

    @Test
    void playExpertCardTest() throws PhaseNotRightException, NotYourTurnException {
        expertBoard = new ExpertBoard(player1, player2, new Turn(List.of(player1, player2)), RandomGenerator.getDefault().nextLong());
        expertBoard.playCard(player1, 1);
        expertBoard.playCard(player1, 1);
        try {
            expertBoard.playCard(player1, 5);
        } catch (NotYourTurnException | PhaseNotRightException e) {
            playExpertCardTest();
        }

        try {
            expertBoard.playExpertCard(4, 0, List.of());
        } catch (StudentException | CoinException e) {
            fail(e.getCause());
        }

        assertEquals(3 + 2, expertBoard.getPossibleMovingSteps(),
                "5 card -> 3 possible steps + 2 for the mailman effect");
    }
}