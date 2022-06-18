package it.polimi.ingsw.server.model.expertLogic.character.charTypes;

import it.polimi.ingsw.server.model.baseLogic.Bag;
import it.polimi.ingsw.server.model.expertLogic.character.costants.CharacterUtility;

import java.util.*;

/**
 *factory method for StandardCharacter generation
 * Could contain the board to init the characters. Could also implement the character extraction.
 **/
public class Tavern {
    private long seed;
    private static Bag bag;
    private final int numberOfCharacters = CharacterUtility.values().length+1; //final = 12
    private final int numberOfPlayableCharacter = 3;

    /**
     * @param bag for StudentCharacter Character generation
     */
    public Tavern(Bag bag){
        seed = bag.getSeed();
        Tavern.bag = bag;
    }

    /**
     * extract 3 different cards for the game
     * @return List<StandardCharacter>
     */
    public Map<CharacterUtility, StandardCharacter> extract(){
        StandardCharacter ec;
        Random rand = new Random(seed);
        int idChar;
        Map<CharacterUtility, StandardCharacter> characterMap = new EnumMap<>(CharacterUtility.class);
        for(int i= 0; i < numberOfPlayableCharacter; i++) {
            idChar = rand.nextInt(1, numberOfCharacters);
            CharacterUtility characterID = CharacterUtility.getChar(idChar);
            if(!characterMap.containsKey(characterID))
                characterMap.put(characterID, getExpertCharacter(idChar));
            else --i;

        }
        return characterMap;

    }

    /**
     * Factory method that calls the right Character constructor
     * @param idChar - integer between 1 and 12 identifier of the character
     * @return StandardCharacter - abstract superclass of all character
     */
    private StandardCharacter getExpertCharacter(int idChar) {
        if(idChar < 1 || idChar > numberOfCharacters)
            throw new IllegalArgumentException(idChar + " is not a legal id for ExpertCharacters");
        //TODO: improve
        return switch (idChar) {
            case 1, 7, 11 -> new StudentCharacter(idChar, bag);
            case 5 -> new BlockCharacter(idChar);
            default -> new StandardCharacter(idChar);
        };
    }

    public StandardCharacter extract4testing(int idChar){
        return getExpertCharacter(idChar);
    }

}
