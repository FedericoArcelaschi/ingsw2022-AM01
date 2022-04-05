package it.polimi.ingsw.model.expert.Characters;

import it.polimi.ingsw.model.Bag;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *factory method for Generic generation
 * Could contain the board to init the characters. Could also implement the character extraction.
 **/
public class Tavern {
    private long seed;
    Bag bag;
    Generic ec;

    public Tavern(Bag bag) { //TODO: add to the expertBoard a seed that matches between the cloud and the Tavern.
        this.bag = bag;
        seed = bag.getSeed();
    }

    /**
     * extract the 3 cards for the game
     * @return List<Generic>
     */
    public List<Generic> extract(){
        List<Generic> expCards = new ArrayList<>();
        Random rand = new Random(seed);
        Integer idChar = 0;
        int i;
        for(i= 0; i < 3; i++) {
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
        if(idChar == 1)
            return ec = new Student(idChar, bag);
        else if(idChar == 2 || idChar == 6)
            return ec = new Influence(idChar);
        else if(idChar == 5)
            return ec = new Block(idChar);
        else if(idChar == 4 || idChar == 3)
            return ec = new Action(idChar);
        else if(true)
            return ec = new Influence(idChar);
        else
            return null; // vorrei un Exception
    }
}
