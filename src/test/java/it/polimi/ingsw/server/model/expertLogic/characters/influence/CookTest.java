package it.polimi.ingsw.server.model.expertLogic.characters.influence;

import it.polimi.ingsw.server.model.baseLogic.*;
import it.polimi.ingsw.server.model.exceptions.StudentException;
import it.polimi.ingsw.server.model.exceptions.TooManyStudentsException;
import it.polimi.ingsw.server.model.expertLogic.ExpertCastle;
import it.polimi.ingsw.server.model.expertLogic.ExpertIsland;
import it.polimi.ingsw.server.model.expertLogic.character.applyEffect.ParametersForCharacter;
import it.polimi.ingsw.server.model.expertLogic.character.charTypes.StandardCharacter;
import it.polimi.ingsw.server.model.expertLogic.character.costants.CharacterExplanation;
import it.polimi.ingsw.server.model.expertLogic.influence.ExpertInfluence;
import it.polimi.ingsw.server.model.expertLogic.influence.professor.ExpertProfessors;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class CookTest { //9° character

    CharacterExplanation explanation = CharacterExplanation.COOK;
    private ExpertProfessors professorsMap;
    private ExpertInfluence influence;
    private Castle castle1, castle2;
    private String player1 = "Gian Marco", player2 = "Antonino";

    @Test
    void applyEffectTest() {
        castle1 = new ExpertCastle(Team.WHITE, 2, Bag.extractMany(7));
        castle2 = new ExpertCastle(Team.BLACK, 2, Bag.extractMany(7));
        influence = new ExpertInfluence(
                professorsMap
                        = new ExpertProfessors(
                        Map.of(player1, castle1, player2, castle2)));

        Island island = new ExpertIsland(new Island());

        for (Team t : Team.values())
            assertEquals(0, influence.getInfluenceMap(island).get(t));

        try {
            castle1.addStudentInDiningRoom(StudentColor.BLUE);
            castle1.addStudentInDiningRoom(StudentColor.YELLOW);
            castle2.addStudentInDiningRoom(StudentColor.RED);
            castle2.addStudentInDiningRoom(StudentColor.GREEN);

        } catch (TooManyStudentsException e) {fail();}
        assertEquals(0, influence.getInfluenceMap(island).get(Team.WHITE));
        assertEquals(0, influence.getInfluenceMap(island).get(Team.BLACK));

        island.addStudent(StudentColor.YELLOW);
        island.addStudent(StudentColor.BLUE);//2 students for the White team
        island.addStudent(StudentColor.RED);
        island.addStudent(StudentColor.GREEN);
        island.addStudent(StudentColor.GREEN);//3 students for the BLAck team
        assertEquals(2, influence.getInfluenceMap(island).get(Team.WHITE));
        assertEquals(3, influence.getInfluenceMap(island).get(Team.BLACK));

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
        assertEquals(1, influence.getInfluenceMap(island).get(Team.BLACK));

        influence.reset();
        assertEquals(2, influence.getInfluenceMap(island).get(Team.WHITE));
        assertEquals(3, influence.getInfluenceMap(island).get(Team.BLACK));
    }
}
