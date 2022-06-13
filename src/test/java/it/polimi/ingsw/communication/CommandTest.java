package it.polimi.ingsw.communication;

import it.polimi.ingsw.communication.command.Command;
import it.polimi.ingsw.server.model.baseLogic.StudentColor;
import it.polimi.ingsw.server.model.expertLogic.character.costants.CharacterUtility;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommandTest {

    @Test
    void testMonk() {
        Command command = Command.createCommand("fede", "paychar MONK blue 2");
        assertEquals(CharacterUtility.MONK, CharacterUtility.getChar(command.getCharId()));
        assertEquals(List.of(StudentColor.BLUE), command.getStudents());
        assertEquals(2, command.getIslandId());
    }

    @Test
    void testMailman() {
        Command command = Command.createCommand("fede", "paychar MAILMAN");
        assertEquals(CharacterUtility.MAILMAN, CharacterUtility.getChar(command.getCharId()));
    }

    @Test
    void testJester() {
        Command command = Command.createCommand("fede", "paychar JESTER blue red");
        assertEquals(CharacterUtility.JESTER, CharacterUtility.getChar(command.getCharId()));
        assertEquals(List.of(StudentColor.BLUE, StudentColor.RED), command.getStudents());
    }

    @Test
    void testStoryteller() {
        Command command = Command.createCommand("fede", "paychar STORYTELLER blue red");
        assertEquals(CharacterUtility.STORYTELLER, CharacterUtility.getChar(command.getCharId()));
        assertEquals(List.of(StudentColor.BLUE, StudentColor.RED), command.getStudents());
    }

    @Test
    void testQueen() {
        Command command = Command.createCommand("fede", "paychar QUEEN blue");
        assertEquals(CharacterUtility.QUEEN, CharacterUtility.getChar(command.getCharId()));
        assertEquals(List.of(StudentColor.BLUE), command.getStudents());
    }
}
