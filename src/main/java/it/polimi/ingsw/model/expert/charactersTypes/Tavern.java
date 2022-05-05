package it.polimi.ingsw.model.expert.charactersTypes;

import it.polimi.ingsw.model.Bag;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *factory method for MasterCharacter generation
 * Could contain the board to init the characters. Could also implement the character extraction.
 **/
public class Tavern {
    private long seed;
    private static Bag bag;
    private final int numberOfCharacters = CharactersInfo.values().length; //final = 12
    private final int numberOfPlayableCharacter = 3;
    private final ArrayList<MasterCharacter> extractedCharacters = new ArrayList<>();

    /**
     * @param bag for Student Character generation
     */
    public Tavern(Bag bag){
        seed = bag.getSeed();
        Tavern.bag = bag;
        for (int i = 0; i <= numberOfCharacters; i++) {
            extractedCharacters.add(i, null);
        }
    }

    /**
     * extract 3 different cards for the game
     * @return List<MasterCharacter>
     */
    public List<MasterCharacter> extract(){
        MasterCharacter ec;
        Random rand = new Random(seed);
        int idChar;
        for(int i= 0; i < numberOfPlayableCharacter; i++) {
            idChar = rand.nextInt(1, numberOfCharacters);
            if(extractedCharacters.get(idChar) == null) {
                ec = getExpertCharacter(idChar);
                extractedCharacters.set(idChar, ec);
            }else
                i--;
        }
        return extractedCharacters;

    }

    /**
     * Factory method that calls the right Character constructor
     * @param idChar - integer between 1 and 12 identifier of the character
     * @return MasterCharacter - abstract superclass of all character
     */
    private MasterCharacter getExpertCharacter(int idChar) {
        if(idChar < 1 || idChar > numberOfCharacters)
            throw new IllegalArgumentException(idChar + " is not a legal id for ExpertCharacters");
        //TODO: make more functional
        return switch (idChar) {
            case 1, 7, 11 -> new Student(idChar, bag);
            case 10 -> new Student(idChar);
            case 5 -> new Block(idChar);
            default -> new Main(idChar);
        };
    }

    public MasterCharacter extract4testing(int idChar){
        if(extractedCharacters.get(idChar) == null)
            return getExpertCharacter(idChar);
        return extractedCharacters.get(idChar);
    }

}
