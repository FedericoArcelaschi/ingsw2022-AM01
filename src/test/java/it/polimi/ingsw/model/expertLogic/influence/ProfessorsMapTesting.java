package it.polimi.ingsw.model.expertLogic.influence;

import it.polimi.ingsw.model.baseLogic.Bag;
import it.polimi.ingsw.model.baseLogic.Castle;
import it.polimi.ingsw.model.baseLogic.StudentColor;
import it.polimi.ingsw.model.baseLogic.Team;
import it.polimi.ingsw.model.baseLogic.influence.Professors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class ProfessorsMapTesting {

    private Professors professorsMap;
    private Castle castle1, castle2;
    @BeforeEach
    void setUp() {
        castle1 = new Castle(Team.BLACK, 2, Bag.extractMany(7));
        castle2 = new Castle(Team.WHITE, 2, Bag.extractMany(7));
        String player1 = "lorenza";
        String player2 = "federica";
        professorsMap = new Professors(Map.of(player1, castle1, player2, castle2));
    }

    @Test
    void initializeTest(){
        professorsMap.updateProfessorsAssigning();
        for (StudentColor c: StudentColor.values()) {
            assertNull(
                    professorsMap.getProfessorsAssigning().get(c),
                    "Each object must be initiated at null");
        }
    }

    @Test
    void computingTest(){
        try {
            castle1.addStudentsInDiningRoom(
                    Arrays.asList(  StudentColor.YELLOW, StudentColor.YELLOW, StudentColor.YELLOW,
                                    StudentColor.RED, StudentColor.RED,
                                    StudentColor.BLUE));
            professorsMap.updateProfessorsAssigning();
            assertEquals(Team.BLACK, professorsMap.getProfessorsAssigning().get(StudentColor.YELLOW),
                    "for yellow");
            assertEquals(Team.BLACK, professorsMap.getProfessorsAssigning().get(StudentColor.RED));
        }
        catch (Throwable e){
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void computing2Test(){
        try {
            castle1.addStudentsInDiningRoom(//black's castle
                    Arrays.asList(  StudentColor.YELLOW, StudentColor.YELLOW, StudentColor.YELLOW,
                                    StudentColor.RED, StudentColor.RED,
                                    StudentColor.BLUE));
            professorsMap.updateProfessorsAssigning();
            castle2.addStudentsInDiningRoom(//white's castle
                    Arrays.asList(StudentColor.YELLOW, StudentColor.YELLOW, StudentColor.YELLOW,
                                    StudentColor.RED, StudentColor.RED, StudentColor.RED, StudentColor.RED));
            professorsMap.updateProfessorsAssigning();
            assertEquals(   Team.BLACK,
                            professorsMap
                                    .getProfessorsAssigning()
                                    .get(StudentColor.YELLOW),
                    "Black has the yellow Professor");
            assertEquals(Team.WHITE, professorsMap.getProfessorsAssigning().get(StudentColor.RED));
        }
        catch (Throwable e){
            e.printStackTrace();
            fail();
        }
    }
}
