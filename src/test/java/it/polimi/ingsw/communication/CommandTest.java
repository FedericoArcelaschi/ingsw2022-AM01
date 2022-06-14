package it.polimi.ingsw.communication;

import it.polimi.ingsw.communication.command.Command;
import it.polimi.ingsw.server.model.baseLogic.StudentColor;
import it.polimi.ingsw.server.model.expertLogic.character.costants.CharacterUtility;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommandTest {

    @Test
    void testMonk() throws ParseException {
        Command command = new Command("fede", "paychar MONK 2 blue");
        assertEquals(CharacterUtility.MONK, CharacterUtility.getChar(command.getCharId()));
        assertEquals(List.of(StudentColor.BLUE), command.getStudents());
        assertEquals(2, command.getIslandId());
    }

    @Test
    void testMailman() throws ParseException {
        Command command = new Command("fede", "paychar MAILMAN");
        assertEquals(CharacterUtility.MAILMAN, CharacterUtility.getChar(command.getCharId()));
    }

    @Test
    void testJester() throws ParseException {
        Command command = new Command("fede", "paychar JESTER blue red");
        assertEquals(CharacterUtility.JESTER, CharacterUtility.getChar(command.getCharId()));
        assertEquals(List.of(StudentColor.BLUE, StudentColor.RED), command.getStudents());
    }

    @Test
    void testStoryteller() throws ParseException {
        Command command = new Command("fede", "paychar STORYTELLER blue red");
        assertEquals(CharacterUtility.STORYTELLER, CharacterUtility.getChar(command.getCharId()));
        assertEquals(List.of(StudentColor.BLUE, StudentColor.RED), command.getStudents());
    }

    @Test
    void testQueen() throws ParseException {
        Command command = new Command("fede", "paychar QUEEN blue");
        assertEquals(CharacterUtility.QUEEN, CharacterUtility.getChar(command.getCharId()));
        assertEquals(List.of(StudentColor.BLUE), command.getStudents());
    }

    @Test
    void createCommand() throws ParseException {
        Command command = new Command("fede", "\npaychar QUEEN blue");
        assertEquals(CharacterUtility.QUEEN, CharacterUtility.getChar(command.getCharId()));
        assertEquals(List.of(StudentColor.BLUE), command.getStudents());
    }
}
