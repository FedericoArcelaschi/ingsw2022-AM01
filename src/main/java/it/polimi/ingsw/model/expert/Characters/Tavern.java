package it.polimi.ingsw.model.expert.Characters;

import it.polimi.ingsw.model.Bag;
import it.polimi.ingsw.model.Board;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *factory method for Generic generation
 * Could contain the board to init the characters. Could also implement the character extraction.
 **/
public class Tavern {
    private long seed;
    Board board;
    Generic ec;

    public Tavern(Board board){
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
        for(int i= 0; i < 3; i++) {
            ec = getExpertCharacter(idChar = rand.nextInt(1, 12));
            if(!expCards.contains(ec))
                expCards.add(ec);
            else
                i--;
        }
        return expCards;
    }

    /**
     * Factory method that calls the right Character constructor
     * @param idChar
     * @return Generic
     */
    private Generic getExpertCharacter(int idChar){
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
                return null;
        }
    }
}
