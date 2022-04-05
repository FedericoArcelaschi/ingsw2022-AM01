package it.polimi.ingsw.model.expert.Characters;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Castle;
import it.polimi.ingsw.model.Color;

import java.util.HashMap;
import java.util.Map;

public class Influence extends Generic {
    Board board;

    public Influence(int idChar){
        super(idChar);
    }

    @Override
    public boolean applyEffect() {

        return false;
    }
    public boolean applyEffect(boolean payedToken, String PlayerID, Board board) {
        this.board = board;
        if(payedToken){
            Map<Color, Castle> professorMap = board.getProfessorMap();
            modify(PlayerID, professorMap); //dovrò usare il valore di ritorno di sto metodo
            return true;
        }
        return false;
    }
    private Map<Color, Castle> modify(String PlayerId, Map<Color, Castle> professorMap){//TODO
        Map<Color, Castle> newProfessorMap = new HashMap<Color, Castle>();
        return newProfessorMap;
    }


}