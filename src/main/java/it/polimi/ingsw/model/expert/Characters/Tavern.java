package it.polimi.ingsw.model.expert.Characters;

import java.lang.IllegalArgumentException;
import it.polimi.ingsw.model.Bag;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.expert.ExpertBoard;

import javax.crypto.NullCipher;
import java.util.*;

/**
 *factory method for Generic generation
 * Could contain the board to init the characters. Could also implement the character extraction.
 **/
public class Tavern {
    private long seed;
    private static Bag bag;
    private final int numberOfCharacters = 12;
    private final int numberOfPlayableCharacter = 3;

    public Tavern(Bag bag){
        seed = bag.getSeed();
        this.bag = bag;
    }

    /**
     * extract 3 different cards for the game
     * @return List<Generic>
     */
    public List<Generic> extract(){
        Generic ec;
        List<Generic> expCards = new ArrayList<>();
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
     * @param idChar - integer between 1 and 12 identifier of the character
     * @return Generic - abstract superclass of all character
     */
    private Generic getExpertCharacter(int idChar) throws IllegalArgumentException{
        Generic ec;
        switch (idChar){
            case 1, 7, 10, 11:
                return ec = new Student(idChar, bag);
            case 2, 6, 8, 9, 12:
                return ec = new Influence(idChar);
            case 3, 4:
                return ec = new Action(idChar);
            case 5:
                return ec = new Block(idChar);
            default:
                throw new IllegalArgumentException();
        }
    }
    public Generic extract4testing(int idChar){
        return getExpertCharacter(idChar);
    }

}
