package it.polimi.ingsw.server.model.expertLogic.characters.influence;

import it.polimi.ingsw.server.model.baseLogic.*;
import it.polimi.ingsw.server.model.exceptions.*;
import it.polimi.ingsw.server.model.expertLogic.ExpertBoardStub;
import it.polimi.ingsw.server.model.expertLogic.ExpertCastle;
import it.polimi.ingsw.server.model.expertLogic.ExpertIsland;
import it.polimi.ingsw.server.model.expertLogic.character.applyEffect.ParametersForCharacter;
import it.polimi.ingsw.server.model.expertLogic.character.StandardCharacter;
import it.polimi.ingsw.server.model.expertLogic.character.costants.CharacterExplanation;
import it.polimi.ingsw.server.model.expertLogic.character.costants.CharacterUtility;
import it.polimi.ingsw.server.model.expertLogic.influence.ExpertInfluence;
import it.polimi.ingsw.server.model.expertLogic.influence.professor.ExpertProfessors;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static it.polimi.ingsw.server.model.baseLogic.StudentColor.RED;
import static it.polimi.ingsw.server.model.baseLogic.StudentColor.YELLOW;
import static it.polimi.ingsw.server.model.baseLogic.Team.BLACK;
import static org.junit.jupiter.api.Assertions.*;

public class CookTest { //9° character

    CharacterExplanation explanation = CharacterExplanation.COOK;
    private ExpertProfessors professorsMap;
    private ExpertInfluence influence;
    private Castle castle1, castle2;
    private final String player1 = "Giammarco";
    private final String player2 = "Antonino";

    private ExpertBoardStub expertBoard = new ExpertBoardStub(player1, player2, CharacterUtility.COOK);

    @Test
    void applyEffectTest() {
        castle1 = new ExpertCastle(Team.WHITE, 2, Bag.extractMany(7));
        castle2 = new ExpertCastle(BLACK, 2, Bag.extractMany(7));
        influence = new ExpertInfluence(
                professorsMap
                        = new ExpertProfessors(
                        Map.of(player1, castle1, player2, castle2)));

        Island island = new ExpertIsland(new Island());

        for (Team t : Team.values())
            assertEquals(0, influence.getInfluenceMap(island).get(t));

        try {
            castle1.addStudentInDiningRoom(StudentColor.BLUE);
            castle1.addStudentInDiningRoom(YELLOW);
            castle2.addStudentInDiningRoom(RED);
            castle2.addStudentInDiningRoom(StudentColor.GREEN);

        } catch (TooManyStudentsException e) {fail();}
        assertEquals(0, influence.getInfluenceMap(island).get(Team.WHITE));
        assertEquals(0, influence.getInfluenceMap(island).get(BLACK));

        island.addStudent(YELLOW);
        island.addStudent(StudentColor.BLUE);//2 students for the White team
        island.addStudent(RED);
        island.addStudent(StudentColor.GREEN);
        island.addStudent(StudentColor.GREEN);//3 students for the BLAck team
        assertEquals(2, influence.getInfluenceMap(island).get(Team.WHITE));
        assertEquals(3, influence.getInfluenceMap(island).get(BLACK));

        StandardCharacter cook = new StandardCharacter(9);
        ParametersForCharacter par4C = new ParametersForCharacter();
        par4C.setInfluence(influence);
        par4C.setCurrentTeam(Team.WHITE);
        par4C.setRequestedStudent(StudentColor.GREEN);

        try {
            cook.applyEffect(par4C);
        } catch (StudentException | IllegalAccessException e) {
            fail(e.getMessage());
        }

        assertEquals(2, influence.getInfluenceMap(island).get(Team.WHITE));
        assertEquals(1, influence.getInfluenceMap(island).get(BLACK));

        influence.reset();
        assertEquals(2, influence.getInfluenceMap(island).get(Team.WHITE));
        assertEquals(3, influence.getInfluenceMap(island).get(BLACK));
    }

    void setUp() throws NoSuchStudentException, TooManyStudentsException {
        expertBoard.playPlanningPhaseFirstPlayer1();
        expertBoard.getCastle(player1).removeStudentsFromWaitingRoom(List.of(StudentColor.GREEN, StudentColor.GREEN, StudentColor.GREEN, StudentColor.GREEN, StudentColor.GREEN, StudentColor.GREEN, YELLOW));
        expertBoard.getCastle(player1).addStudentsInWaitingRoom(List.of(RED));

        expertBoard.getCastle(player1).addStudentsInDiningRoom(List.of(RED));

        expertBoard.getCastle(player2).addStudentsInDiningRoom(List.of(YELLOW));
        //student player1
    }

    void playInitialRound() throws PhaseNotRightException {
        //student player1
        expertBoard.getIslandList().get(6).addStudent(RED);
        expertBoard.getIslandList().get(6).addStudent(RED);
        expertBoard.getIslandList().get(6).addStudent(YELLOW);
        expertBoard.changePhase();
        expertBoard.moveMotherNature(3);
        expertBoard.changePhase();
        expertBoard.changePhase();
        //student player 2
        expertBoard.changePhase();
    }

    @Test
    void playExpertCharacterTest() throws StudentException, PhaseNotRightException, CoinException {
        setUp();
        playInitialRound();
        expertBoard.add1Coin(player2);
        expertBoard.add1Coin(player2);
        expertBoard.playExpertCard(9, null, List.of(RED));
        expertBoard.moveMotherNature(3);
        assertEquals(Team.BLACK, expertBoard.getIslandList().get(6).getOwnership());
    }

    @Test
    void doNOTplayExpertCharacterTest() throws StudentException, PhaseNotRightException {
        setUp();
        playInitialRound();
        expertBoard.moveMotherNature(3);
        assertEquals(Team.WHITE, expertBoard.getIslandList().get(6).getOwnership());
    }

    @Test
    void playExpertCharacterERROR() throws StudentException, PhaseNotRightException {
        setUp();
        playInitialRound();
        expertBoard.add1Coin(player2);
        expertBoard.add1Coin(player2);
        assertThrowsExactly(IllegalArgumentException.class,
            ()->
                expertBoard.playExpertCard(9, null, null),
                "Cook needs one student to be used");
    }
}
