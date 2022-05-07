package it.polimi.ingsw.model.expert.character.type;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.IntegerBoxing;
import it.polimi.ingsw.model.exceptions.StudentException;
import it.polimi.ingsw.model.expert.character.functionalInterfaces.ApplyEffect;
import it.polimi.ingsw.model.expert.boardInterfaces.StudentPlaces;
import it.polimi.ingsw.model.influence.Influence;

import java.util.List;

public class Main extends MasterCharacter {
    /**
     * @param idChar id corresponding to the position in the CharacterList
     */
    private ApplyEffect function;
    public Main(int idChar) {
        super(idChar);
    }

    @Override
    public void applyEffect(List<Color> students, List<StudentPlaces> placesList, Influence influence, IntegerBoxing steps) throws StudentException, IllegalAccessException {
        charactersFunction.applyEffect(students, placesList, influence, steps);
        cost += characterName.getCost() + 1;
    }

    @Override
    public String getEffect() {
        return null;
    }
}
