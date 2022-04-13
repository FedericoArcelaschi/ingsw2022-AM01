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
    ExpertBoard board;
    Generic ec;

    public Tavern(ExpertBoard board){
        this.board = board;
        seed = board.getBag().getSeed();
    }

    /**
     * extract the 3 cards for the game
     * @return List<Generic>
     */
    public List<Generic> extract(){
        List<Generic> expCards = new ArrayList<>();
        Random rand = new Random(seed);
        int idChar;
        for(int i = 0; i < 13; i++) {
            expCards.add(i, null);
        }
        for(int i= 0; i < 3; i++) {
            idChar = rand.nextInt(1,12);
            if(expCards.get(idChar)==null) {
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
        switch (idChar) {
            case 1, 7, 10, 11:
                return ec = new Student(idChar, board.getBag());
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
