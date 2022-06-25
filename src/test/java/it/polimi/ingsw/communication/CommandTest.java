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
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommandTest {
    Game g;

    @BeforeEach
    void setup() {
        //SEED 5: Taxman gets extracted
        g = new Game(GameType.EXPERT_2_PLAYER, List.of("fede"), 5);
    }

    @Test
    void testMonk() throws ParseException {
        Command command = new Command("paychar MONK 2 blue");
        assertEquals(CharacterUtility.MONK, CharacterUtility.getChar(command.getCharId()));
        assertEquals(List.of(StudentColor.BLUE), command.getStudents());
        assertEquals(2, command.getIslandId());
    }

    @Test
    void testMailman() throws ParseException, WrongGameModeException {
        Command command = new Command("paychar MAILMAN");
        assertEquals(CharacterUtility.MAILMAN, CharacterUtility.getChar(command.getCharId()));
        g.executeCommand(command);
        //fixme assertEquals(3, g.getBoard().getPossibleMovingSteps());
    }

    @Test
    void testJester() throws ParseException {
        Command command = new Command( "paychar JESTER blue red");
        assertEquals(CharacterUtility.JESTER, CharacterUtility.getChar(command.getCharId()));
        assertEquals(List.of(StudentColor.BLUE, StudentColor.RED), command.getStudents());
    }

    @Test
    void testStoryteller() throws ParseException {
        Command command = new Command( "paychar STORYTELLER blue red");
        assertEquals(CharacterUtility.STORYTELLER, CharacterUtility.getChar(command.getCharId()));
        assertEquals(List.of(StudentColor.BLUE, StudentColor.RED), command.getStudents());
    }

    @Test
    void testQueen() throws ParseException {
        Command command = new Command("paychar QUEEN blue");
        assertEquals(CharacterUtility.QUEEN, CharacterUtility.getChar(command.getCharId()));
        assertEquals(List.of(StudentColor.BLUE), command.getStudents());
    }

    @Test
    void createCommand() throws ParseException {
        Command command = new Command("\npaychar QUEEN blue");
        assertEquals(CharacterUtility.QUEEN, CharacterUtility.getChar(command.getCharId()));
        assertEquals(List.of(StudentColor.BLUE), command.getStudents());
    }

    @Test
    void testTaxman() throws ParseException {
        //RUN THIS COMMAND WITH SEED 5.
        Command command = new Command("paychar taxman blue");
        assertEquals(CharacterUtility.TAXMAN, CharacterUtility.getChar(command.getCharId()));
        assertEquals(List.of(StudentColor.BLUE), command.getStudents());
        try {
            g.getBoard().getCastle("fede").addStudentsInDiningRoom(List.of(StudentColor.BLUE, StudentColor.BLUE, StudentColor.BLUE));
        } catch (TooManyStudentsException e) {
            throw new RuntimeException(e);
        }
        assertEquals(3, g.getBoard().getCastle("fede").getDiningRoom().get(StudentColor.BLUE));
        setupTaxman();
        g.executeCommand(command);
        assertEquals(0, g.getBoard().getCastle("fede").getDiningRoom().get(StudentColor.BLUE));
    }

    private void setupTaxman() throws ParseException {
        g.executeCommand(new Command("playcard 1"));
        g.executeCommand(new Command("playcard 2"));
        g.executeCommand(new Command("movestudentcastle pink pink pink"));
        g.executeCommand(new Command("movemothernature 1"));
        g.executeCommand(new Command("choosecloud 2"));
        g.executeCommand(new Command("movestudentcastle red red pink"));
        g.executeCommand(new Command("movemothernature 1"));
        g.executeCommand(new Command("choosecloud 1"));
        g.executeCommand(new Command("playcard 3"));
        g.executeCommand(new Command("playcard 4"));
        g.executeCommand(new Command("movestudentcastle green green green"));
    }
}
