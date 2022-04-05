package it.polimi.ingsw.model.expert.Characters;

import it.polimi.ingsw.model.Bag;
import it.polimi.ingsw.model.Board;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *factory method for ExpertCharacter generation
 * Could contain the board to init the characters. Could also implement the character extraction.
 **/
public class Tavern {
    private long seed;
    Bag bag;
    ExpertCharacter ec;

    public Tavern(Bag bag) { //TODO: add to the expertBoard a seed that matches between the cloud and the Tavern.
        this.bag = bag;
        seed = bag.getSeed();
    }

    /**
     * extract the 3 cards for the game
     * @return List<ExpertCharacter>
     */
    public List<ExpertCharacter> extract(){
        List<ExpertCharacter> expCards = new ArrayList<>();
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
     * @return ExpertCharacter
     */
    private ExpertCharacter getExpertCharacter(int idChar){
        if(idChar == 1)
            return ec = new StudentCharacter(idChar, bag);
        else if(idChar == 2 || idChar == 6)
            return ec = new InfluenceCharacter(idChar);
        else if(idChar == 3 || idChar == 5)
            return ec = new BlockCharacter(idChar);
        else if(idChar == 4)
            return ec = new ScriptCharacter(idChar);
        else if(true)
            return ec = new InfluenceCharacter(idChar);
        else
            return null; // vorrei un Exception
    }
}
