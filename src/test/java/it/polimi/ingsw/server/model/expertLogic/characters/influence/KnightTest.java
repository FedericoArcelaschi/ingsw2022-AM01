package it.polimi.ingsw.server.model.expertLogic.characters.influence;

import it.polimi.ingsw.server.model.baseLogic.Bag;
import it.polimi.ingsw.server.model.baseLogic.Castle;
import it.polimi.ingsw.server.model.baseLogic.Island;
import it.polimi.ingsw.server.model.baseLogic.Team;
import it.polimi.ingsw.server.model.exceptions.StudentException;
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

public class KnightTest { //8° character

    CharacterExplanation explanation = CharacterExplanation.KNIGHT;
    private ExpertInfluence influence;
    private Castle castle1, castle2;
    private final String player1 = "Guido";
    private final String player2 = "Benz";

    @Test
    void applyEffectTest() {
        castle1 = new ExpertCastle(Team.WHITE, 2, Bag.extractMany(7));
        castle2 = new ExpertCastle(Team.BLACK, 2, Bag.extractMany(7));
        influence = new ExpertInfluence( new ExpertProfessors(
                                Map.of(player1, castle1, player2, castle2)));

        Island island = new ExpertIsland(new Island());

        for (Team t : Team.values())
            assertEquals(0, influence.getInfluenceMap(island).get(t));

        StandardCharacter knight = new StandardCharacter(8);

        ParametersForCharacter par4C = new ParametersForCharacter();
        par4C.setInfluence(influence);
        par4C.setCurrentTeam(Team.WHITE);
        try {
            knight.applyEffect(par4C);
        } catch (StudentException | IllegalAccessException e) {
            fail(e.getMessage());
        }
        assertEquals(2, influence.getInfluenceMap(island).get(Team.WHITE));
    }
}
