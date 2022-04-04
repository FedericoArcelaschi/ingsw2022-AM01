package it.polimi.ingsw.model.expert.Characters;

public class ScriptCharacter extends ExpertCharacter {

    public ScriptCharacter(int idChar){
        super(idChar);
    }

    @Override
    public boolean applyEffect() {
        return false;
    }

    /**
     * Method for 4th character. increases the MN move range.
     * @param move
     * @return move +2
     */
    public int applyEffect(int move) { // for 4th character
        return move + 2;
    }


}
