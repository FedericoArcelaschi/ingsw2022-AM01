package it.polimi.ingsw.model.expert.charactersTypes;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.exceptions.NoSuchStudentException;
import it.polimi.ingsw.model.exceptions.TooManyStudentsException;
import it.polimi.ingsw.model.expert.charactersFunctions.ApplyEffect;
import it.polimi.ingsw.model.expert.influence.ExpertInfluenceMap;
import it.polimi.ingsw.model.expert.interfaces.StudentPlaces;

import java.util.List;

public class Main extends MasterCharacter {
    /**
     * @param idChar id corresponding to the position in the CharacterList
     */
    private ApplyEffect function;
    public Main(int idChar) {
        super(idChar);
        function = charactersFunction.getFunction();
    }

    @Override
    public void applyEffect(List<Color> students, List<StudentPlaces> placesList, ExpertInfluenceMap influence, Integer steps) throws NoSuchStudentException, TooManyStudentsException, IllegalAccessException {
        function.applyEffect(students, placesList, influence, steps);
        cost += characterName.getCost() + 1;
    }

    @Override
    public String getEffect() {
        return null;
    }
}
