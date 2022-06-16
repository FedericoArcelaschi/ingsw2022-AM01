package it.polimi.ingsw.communication;

import it.polimi.ingsw.communication.command.Command;
import it.polimi.ingsw.server.controller.Game;
import it.polimi.ingsw.server.controller.GameType;
import it.polimi.ingsw.server.model.baseLogic.StudentColor;
import it.polimi.ingsw.server.model.exceptions.TooManyStudentsException;
import it.polimi.ingsw.server.model.exceptions.WrongGameModeException;
import it.polimi.ingsw.server.model.expertLogic.character.charTypes.StandardCharacter;
import it.polimi.ingsw.server.model.expertLogic.character.costants.CharacterUtility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommandTest {
    Game g;

    @BeforeEach
    void setup() {
        g = new Game(GameType.EXPERT_2_PLAYER, List.of("lore", "fede"));
    }

    @Test
    void testMonk() throws ParseException {
        Command command = new Command("fede", "paychar MONK 2 blue");
        assertEquals(CharacterUtility.MONK, CharacterUtility.getChar(command.getCharId()));
        assertEquals(List.of(StudentColor.BLUE), command.getStudents());
        assertEquals(2, command.getIslandId());
    }

    @Test
    void testMailman() throws ParseException, WrongGameModeException {
        Command command = new Command("fede", "paychar MAILMAN");
        assertEquals(CharacterUtility.MAILMAN, CharacterUtility.getChar(command.getCharId()));
        g.executeCommand(new Command("lore", "playcard 2"));
        g.executeCommand(new Command("fede", "playcard 1"));
        g.executeCommand(command);
        assertEquals(3, g.getBoard().getPossibleMovingSteps());
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

    @Test
    void testTaxman() throws ParseException {
        Command command = new Command("lore", "paychar taxman blue");
        assertEquals(CharacterUtility.TAXMAN, CharacterUtility.getChar(command.getCharId()));
        assertEquals(List.of(StudentColor.BLUE), command.getStudents());
        try {
            g.getBoard().getCastle("fede").addStudentsInDiningRoom(List.of(StudentColor.BLUE, StudentColor.BLUE, StudentColor.BLUE));
        } catch (TooManyStudentsException e) {
            throw new RuntimeException(e);
        }
        assertEquals(3, g.getBoard().getCastle("fede").getDiningRoom().get(StudentColor.BLUE));
        g.executeCommand(new Command("lore", "playcard 2"));
        g.executeCommand(new Command("fede", "playcard 1"));
        g.executeCommand(command);
        assertEquals(0, g.getBoard().getCastle("fede").getDiningRoom().get(StudentColor.BLUE));
    }
}
