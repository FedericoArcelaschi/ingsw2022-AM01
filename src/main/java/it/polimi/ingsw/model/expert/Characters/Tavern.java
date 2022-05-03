package it.polimi.ingsw.model.expert.characters;

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
    private final int numberOfCharacters = 12;
    private final int numberOfPlayableCharacter = 3;

    /**
     * @param bag for Student Character generation
     */
    public Tavern(Bag bag){
        seed = bag.getSeed();
        Tavern.bag = bag;
    }
    /**Constructor for tests*/
    public Tavern(){}

    /**
     * extract 3 different cards for the game
     * @return List<MasterCharacter>
     */
    public List<MasterCharacter> extract(){
        MasterCharacter ec;
        List<MasterCharacter> expCards = new ArrayList<>();
        Random rand = new Random(seed);
        int idChar;
        for(int i = 0; i < numberOfCharacters + 1; i++) {
            expCards.add(i, null);
        }
        for(int i= 0; i < numberOfPlayableCharacter; i++) {
            idChar = rand.nextInt(1, numberOfCharacters);
            if(expCards.get(idChar) == null) {
                ec = getExpertCharacter(idChar);
                expCards.set(idChar, ec);
            }else
                i--;
        }
        return expCards;

    }

    /**
     * Factory method that calls the right Character constructor
     *
     * @param idChar - integer between 1 and 12 identifier of the character
     * @return MasterCharacter - abstract superclass of all character
     */
    private MasterCharacter getExpertCharacter(int idChar) throws IllegalArgumentException {
        MasterCharacter ec;
        return switch (idChar) {
            case 1, 7, 11 -> ec = new Student(idChar, bag);
            case 10 -> ec = new Student(idChar);
            case 2, 6, 8, 9, 12 -> ec = new Influence(idChar);
            case 3, 4 -> ec = new Action(idChar);
            case 5 -> ec = new Block(idChar);
            default -> throw new IllegalArgumentException(idChar + " is not a legal id for ExpertCharacters");
        };
    }

    public MasterCharacter extract4testing(int idChar){
        return getExpertCharacter(idChar);
    }

}
