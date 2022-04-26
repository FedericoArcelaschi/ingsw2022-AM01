package it.polimi.ingsw.model.expert.Characters;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.exceptions.*;
import it.polimi.ingsw.model.expert.ExpertBoard;
import it.polimi.ingsw.model.expert.ExpertCastle;
import it.polimi.ingsw.model.expert.ExpertIsland;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

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
        board = (ExpertBoard) BoardFactory.getBoard(Arrays.asList("piero", "angela"), true, t, 42069);
        board.extract4CharacterTesting(6);
    }

    /**
     * Tests if the Centaur character works as intended.
     */
    @Test
    void testApplyEffect(){
        try {
            board.getCastleMap().get("piero").addStudentsInDiningRoom(Arrays.asList(Color.RED, Color.RED, Color.RED));
            board.getCastleMap().get("angela").addStudentsInDiningRoom(Arrays.asList(Color.BLUE, Color.BLUE, Color.BLUE));
            board.getCastleMap().get("piero").removeStudentsFromWaitingRoom(Arrays.asList(Color.GREEN));
            board.getCastleMap().get("piero").addStudentsInWaitingRoom(Arrays.asList(Color.BLUE));
            board.getCastleMap().get("angela").removeStudentsFromWaitingRoom(Arrays.asList(Color.YELLOW));
            board.getCastleMap().get("angela").addStudentsInWaitingRoom(Arrays.asList(Color.YELLOW));
            assertEquals(2, ((ExpertCastle) board.getCastleMap().get("piero")).getCoins());
            board.playExpertCard(6, 4);
            board.playCard("piero", 6);
            board.moveStudentToIsland("piero", 3, Arrays.asList(Color.BLUE));
            board.moveMotherNature(3);
            System.out.println(board.getIslandList().get(3).getOwnership());
            t.nextTurnAction();
            board.playCard("angela", 10);
            board.moveStudentToIsland("angela", 3, Arrays.asList(Color.BLUE, Color.BLUE));
            board.moveStudentToDiningRoom("angela", Arrays.asList(Color.BLUE));
            board.getCastleMap().get("angela").removeStudentsFromWaitingRoom(Arrays.asList(Color.BLUE, Color.BLUE, Color.BLUE));
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