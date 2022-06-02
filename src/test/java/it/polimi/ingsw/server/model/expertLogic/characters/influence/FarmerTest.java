package it.polimi.ingsw.server.model.expertLogic.characters.influence;

import it.polimi.ingsw.server.model.baseLogic.*;
import it.polimi.ingsw.server.model.exceptions.*;
import it.polimi.ingsw.server.model.expertLogic.ExpertBoard;
import it.polimi.ingsw.server.model.expertLogic.character.applyEffect.ParametersForCharacter;
import it.polimi.ingsw.server.model.expertLogic.character.costants.CharacterExplanation;
import it.polimi.ingsw.server.model.expertLogic.character.costants.CharacterUtility;
import it.polimi.ingsw.server.model.expertLogic.influence.ExpertInfluence;
import it.polimi.ingsw.server.model.expertLogic.influence.professor.ExpertProfessors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.random.RandomGenerator;

import static org.junit.jupiter.api.Assertions.*;

class FarmerTest { //2° character

    CharacterExplanation explanation = CharacterExplanation.FARMER;
    private ExpertProfessors professorsMap;
    private ExpertInfluence influence;
    private Castle castle1, castle2;
    private String player1 = "Lorenza", player2 = "Federica";

    @BeforeEach
    void setUp() {
        castle1 = new Castle(Team.BLACK, 2, Bag.extractMany(7));
        castle2 = new Castle(Team.WHITE, 2, Bag.extractMany(7));
        professorsMap = new ExpertProfessors(Map.of(player1, castle1, player2, castle2));
        influence = new ExpertInfluence(professorsMap);
    }

    @Test
    void applyEffectFarmer() {
        try {
            castle1.addStudentInDiningRoom(StudentColor.YELLOW);
            professorsMap.updateProfessorsAssigning();
            castle2.addStudentInDiningRoom(StudentColor.YELLOW);
            professorsMap.updateProfessorsAssigning();

            assertEquals(castle1.getTeam(), professorsMap.getProfessorsAssigning().get(StudentColor.YELLOW),
                    "Lorenza got the Yellow professor first -> has the Yellow professor.");
            ParametersForCharacter parFC = new ParametersForCharacter();
            parFC.setInfluence(influence);
            parFC.setCurrentTeam(Team.WHITE);
            CharacterUtility.FARMER
                    .getFunction()
                    .applyEffect(parFC);
            assertEquals(castle2.getTeam(), professorsMap.getProfessorsAssigning().get(StudentColor.YELLOW),
                    "Federica payed (she's the current player) for the FARMER effect -> she has the Yellow professor now.");
        }catch (Throwable e) {
            e.printStackTrace();
        }
    }

    @Test
    void playExpertCharacterTest() {
        //SetUp
        ExpertBoard expertBoard = new ExpertBoard(player1, player2, new Turn(List.of(player1, player2)), RandomGenerator.getDefault().nextLong());
        expertBoard.extract4CharacterTesting( 2 );

        try {
            expertBoard.moveStudentsToDiningRoom(player1, expertBoard.getCastle(player1).getWaitingRoom());
        } catch (NoSuchStudentException | NotYourTurnException | TooManyStudentsException e) {
            throw new RuntimeException(e);
        }
        try {
            expertBoard.playExpertCard( 2, 0, List.of(StudentColor.values()) );
        } catch (StudentException e) {
            throw new RuntimeException(e);
        } catch (CoinException ignored) {
            playExpertCharacterTest();
            return;
        }

        for (StudentColor c: StudentColor.values()) {
            assertEquals(expertBoard.getCurrentTeam(), expertBoard.getProfessorsMap().get(c),
                    "Every player has zero students in the dining room, therefore every professor should go to Lorenza");
        }
    }

    @Test
    void playExpertCharacterTest10times() {
        for (int i = 0; i < 10; i++) {
            playExpertCharacterTest();
        }
    }

}