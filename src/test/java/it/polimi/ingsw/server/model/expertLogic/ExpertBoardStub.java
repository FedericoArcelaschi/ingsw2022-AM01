package it.polimi.ingsw.server.model.expertLogic;

import it.polimi.ingsw.server.model.baseLogic.*;
import it.polimi.ingsw.server.model.exceptions.CoinException;
import it.polimi.ingsw.server.model.exceptions.TooManyStudentsException;
import it.polimi.ingsw.server.model.expertLogic.character.specializedCharacters.charTypes.Tavern;
import it.polimi.ingsw.server.model.expertLogic.character.costants.CharacterUtility;

import java.util.List;
import java.util.random.RandomGenerator;

public class ExpertBoardStub extends ExpertBoard {
    /**
     * Constructor with 6 greens in the waiting room to gain the 2 coins
     */
    public ExpertBoardStub(String playerID1, String playerID2, CharacterUtility characterToExtract) {
        super(playerID1, playerID2, new Turn(List.of(playerID1, playerID2)), RandomGenerator.getDefault().nextLong());
        construct();

        do {
            expertCharactersCards = new Tavern(new Bag(20)).extract();
        } while (!expertCharactersCards.containsKey(characterToExtract));

        castleMap.replace(playerID1, new ExpertCastle(Team.WHITE, 2, List.of(StudentColor.GREEN, StudentColor.GREEN, StudentColor.GREEN, StudentColor.GREEN, StudentColor.GREEN, StudentColor.GREEN, StudentColor.YELLOW)));
        castleMap.replace(playerID2, new ExpertCastle(Team.BLACK, 2, List.of(StudentColor.GREEN, StudentColor.GREEN, StudentColor.GREEN, StudentColor.GREEN, StudentColor.GREEN, StudentColor.GREEN, StudentColor.YELLOW)));
    }

    public void remove1Coin(String playerID) {
        try {
            ((ExpertCastle) castleMap.get(playerID)).payCharacter(1);
        } catch (CoinException e) {
            System.err.println(e.getMessage());
        }
    }

    public void add1Coin(String playerID) {
        for (StudentColor student : StudentColor.values()) {
            try {
                castleMap.get(playerID).addStudentsInDiningRoom(List.of(student, student, student));
                break;
            } catch (TooManyStudentsException repeat) {}
        }
    }
}
