package it.polimi.ingsw.model.expert.characters;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.exceptions.*;
import it.polimi.ingsw.model.expert.ExpertBoard;
import it.polimi.ingsw.model.expert.ExpertCastle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * test for sixth character
 */
class InfluenceTestCentaur {

    private ExpertBoard board;
    private Turn t;

    @BeforeEach
    void setUp() throws NoSuchStudentException, NotYourTurnException, TooManyStudentsException {
        t = new Turn(Arrays.asList("piero", "angela"));
        board = (ExpertBoard) BoardFactory.getBoard(Arrays.asList("piero", "angela"), true, t, 42064);
        board.extract4CharacterTesting(6);
    }

    /**
     * Tests if the Centaur character works as intended.
     */
    @Test
    void testApplyEffect(){
        try {
            System.out.println(board.getCastleMap().get("piero").getWaitingRoom()); //PIERO HA DUE ROSA
            System.out.println(board.getCastleMap().get("angela").getWaitingRoom()); //ANGELA HA TRE ROSSI
            System.out.println(board.getIslandList().get(3).getStudents());
            assertEquals(1, ((ExpertCastle) board.getCastleMap().get("piero")).getCoins());
            board.playExpertCard(6, 4);
            board.playCard("piero", 6);
            t.nextTurnAction();
            board.moveStudentToDiningRoom("piero", Arrays.asList(Color.PINK));
            board.moveMotherNature(3);
            System.out.println(board.getIslandList().get(3).getOwnership());
            t.nextTurnAction();
            board.playCard("angela", 10);
            board.moveStudentToIsland("angela", 3, Arrays.asList(Color.RED, Color.RED));
            board.moveStudentToDiningRoom("angela", Arrays.asList(Color.RED));
            board.getCastleMap().get("angela").removeStudentsFromWaitingRoom(Arrays.asList(Color.RED, Color.RED, Color.RED));
            board.moveMotherNature(5);
            t.nextTurnPlanning();
            board.playCard("piero", 8);
            board.moveMotherNature(4);
            t.nextTurnAction();
            board.playCard("angela", 10);
            board.moveMotherNature(3);
            System.out.println(board.getIslandList().get(3).getStudents());
            System.out.println(board.getProfessorsMap());
            assertEquals(Team.BLACK, board.getIslandList().get(3).getOwnership());
        }catch(Exception e){
            e.printStackTrace();
            fail("exception not expected");
        }
    }
}