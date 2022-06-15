package it.polimi.ingsw.server.model.expertLogic.characters.student;
import it.polimi.ingsw.server.model.baseLogic.*;
import it.polimi.ingsw.server.model.baseLogic.interfaces.MapToList;
import it.polimi.ingsw.server.model.exceptions.StudentException;
import it.polimi.ingsw.server.model.exceptions.TooManyStudentsException;
import it.polimi.ingsw.server.model.expertLogic.ExpertCastle;
import it.polimi.ingsw.server.model.expertLogic.character.applyEffect.ParametersForCharacter;
import it.polimi.ingsw.server.model.expertLogic.character.charTypes.StandardCharacter;

import it.polimi.ingsw.server.model.expertLogic.character.charTypes.StudentCharacter;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class TaxmanTest {

    @Test
    void applyEffectTest() {
        Castle  c1 = new ExpertCastle(Team.WHITE, 3, List.of(
                        StudentColor.BLUE, StudentColor.YELLOW, StudentColor.BLUE, StudentColor.YELLOW, StudentColor.YELLOW, StudentColor.PINK, StudentColor.RED)),
                c2 = new ExpertCastle(Team.BLACK, 3, List.of(
                        StudentColor.BLUE, StudentColor.BLUE, StudentColor.YELLOW, StudentColor.BLUE, StudentColor.YELLOW, StudentColor.BLUE, StudentColor.YELLOW, StudentColor.GREEN)),
                c3 = new ExpertCastle(Team.GREY, 3, List.of(
                        StudentColor.BLUE, StudentColor.YELLOW, StudentColor.BLUE, StudentColor.YELLOW, StudentColor.BLUE, StudentColor.YELLOW, StudentColor.BLUE, StudentColor.PINK));
        List<Castle> castleList = new ArrayList<>(List.of(c1, c2, c3));
        for (Castle castle : castleList) {
            try {
                castle.addStudentsInDiningRoom(castle.getWaitingRoom());
            } catch (TooManyStudentsException e) {throw new RuntimeException(e);}
        }


        StandardCharacter taxman = new StudentCharacter(12);
        ParametersForCharacter par4C = new ParametersForCharacter();
        par4C.setPlacesList(List.of(c1, c2, c3));
        par4C.setNumberOfPlayers(3);
        par4C.setRequestedStudentList(List.of(StudentColor.BLUE));
        try {
            taxman.applyEffect(par4C);
        } catch (StudentException | IllegalAccessException e) {
            fail(e.getMessage());
        }

        assertEquals(List.of(StudentColor.YELLOW, StudentColor.YELLOW, StudentColor.YELLOW, StudentColor.PINK, StudentColor.RED).stream().sorted().toList(),
                MapToList.apply(c1.getDiningRoom()).stream().sorted().toList());
        assertEquals(List.of(StudentColor.YELLOW, StudentColor.YELLOW, StudentColor.YELLOW, StudentColor.GREEN, StudentColor.BLUE).stream().sorted().toList(),
                MapToList.apply(c2.getDiningRoom()).stream().sorted().toList());
        assertEquals(List.of(StudentColor.YELLOW, StudentColor.YELLOW, StudentColor.YELLOW, StudentColor.BLUE, StudentColor.PINK).stream().sorted().toList(),
                MapToList.apply(c3.getDiningRoom()).stream().sorted().toList());
    }
}
