package it.polimi.ingsw.model.expert;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Island;
import jdk.internal.net.http.ResponseSubscribers;
import java.util.HashMap;
import java.util.Map;

public class InfluenceCharacter extends Character{

    private int cost;
    private String explaination;
    private ListCharacters lc;

    public InfluenceCharacter(int idChar){
        lc =  ListCharacters.valueOf("PEASANT"); //sarebbe il 2.
        cost = lc.getCost();
        explaination = lc.getExplaination();
    }

    @Override
    public String getExplanation() {
        return explaination;
    }

    @Override
    public int getCost() {
        return cost;
    }

    @Override
    public boolean applyEffect() {
        return false;
    }

    //@Override
    public boolean applyEffect(String PlayerID, Island islandId, Board board) {
        board.getCastleMap().values();
        return false;
    }
}