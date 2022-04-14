package it.polimi.ingsw.model.expert.Characters;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.exceptions.TooManyStudentsException;
import it.polimi.ingsw.model.expert.ExpertBoard;
import it.polimi.ingsw.model.expert.ExpertIsland;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class InfluenceTestFarmer {
    private final Turn t = new Turn(Arrays.asList("a", "b"));
    private ExpertBoard board;
    private Map<Parameters, Object> parametersMap;
    private Map<Color, Castle> professorsMap;
    private Map<String, Castle> castleMap;
    private Castle castleA, castleB;
    private Generic charInfl;


    @BeforeEach
    void setUp() throws TooManyStudentsException {
        board = new ExpertBoard("a", "b", t);
        board.setup4CharacterTesting(2);
        castleA = board.getCastle("a");
        castleB = board.getCastle("b");
        charInfl = board.getAvailableCharacterCards().get(2);
        parametersMap = new HashMap<>();
        //castleMap.put()

    }

    @Test
    void applyEffect() throws TooManyStudentsException {//a's turn
        Map<Color, Castle> expectedProfessorMap, professorMap;
        expectedProfessorMap.putAll(
                Map.of(Color.YELLOW, castleA)
        );
        castleA.addStudentsInDiningRoom(
                Arrays.asList(
                    Color.YELLOW,
                    Color.YELLOW,
                    Color.YELLOW,
                    Color.BLUE,
                    Color.GREEN,
                    Color.GREEN));
        castleB.addStudentsInDiningRoom(
                Arrays.asList(
                    Color.YELLOW,
                    Color.YELLOW,
                    Color.YELLOW,
                    Color.RED,
                    Color.PINK,
                    Color.GREEN));
        professorMap = board.getProfessorMap();
        parametersMap.put(
                Parameters.PROFESSORMAP, professorMap);
        Influence farmerChar = (Influence) board.getAvailableCharacterCards().get(2);
        board.getCastle("a").addStudentsInDiningRoom(List.of(Color.YELLOW));
        board.getCastle("b").addStudentsInDiningRoom(List.of(Color.BLUE,Color.BLUE,Color.BLUE,Color.BLUE));
        board.getCastle("a").addStudentsInDiningRoom(List.of(Color.BLUE));
        //System.out.println(parametersMap.get(Parameters.PROFESSORMAP));
        board.playExpertCard(2, (ExpertIsland) board.getIslandList().get(0), 0, Arrays.asList());
        //System.out.println(professorMap);
    }

    private void updateProfessorsMap(){
        for(Color color : Color.values()) {
            int max = 0;
            Castle newOwner = null;
            for (Castle castle : castleMap.values()) {
                int n = castle.getDiningRoom().get(color);
                if(n > max){
                    max = n;
                    newOwner = castle;
                }
                else if(n == max){
                    newOwner = null;
                }
            }
            if(newOwner != null) professorsMap.replace(color, newOwner);
        }
    }
}