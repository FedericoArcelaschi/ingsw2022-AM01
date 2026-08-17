package it.polimi.ingsw.server.model.expertLogic.characters.student;

import it.polimi.ingsw.communication.command.Command;
import it.polimi.ingsw.server.controller.Game;
import it.polimi.ingsw.server.controller.GameType;
import it.polimi.ingsw.server.model.baseLogic.*;
import it.polimi.ingsw.server.model.baseLogic.interfaces.MapToList;
import it.polimi.ingsw.server.model.exceptions.*;
import it.polimi.ingsw.server.model.expertLogic.ExpertBoard;
import it.polimi.ingsw.server.model.expertLogic.ExpertBoardStub;
import it.polimi.ingsw.server.model.expertLogic.ExpertCastle;
import it.polimi.ingsw.server.model.expertLogic.character.applyEffect.ParametersForCharacter;
import it.polimi.ingsw.server.model.expertLogic.character.StandardCharacter;
import it.polimi.ingsw.server.model.expertLogic.character.costants.CharacterExplanation;
import it.polimi.ingsw.server.model.expertLogic.character.costants.CharacterUtility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.random.RandomGenerator;

import static org.junit.jupiter.api.Assertions.*;

public class TaxmanTest12 {

    private final CharacterExplanation explanation = CharacterExplanation.TAXMAN;
    private ExpertBoardStub expertBoard;
    private final String player1 = "pippo", player2 = "baudo";
    private Turn turn;

    private Game game;

    @BeforeEach
    void setUp() {
        turn = new Turn(List.of(player1, player2));
        expertBoard = new ExpertBoardStub(player1, player2, CharacterUtility.TAXMAN);
        expertBoard.playPlanningPhaseFirstPlayer1();
        //here is in student phase
        expertBoard.add1Coin(player1);
        expertBoard.add1Coin(player1);
    }

    @Test
    void applyEffectTest() {
        Castle  c1 = new ExpertCastle(Team.WHITE, 3, List.of(
                StudentColor.BLUE,
                StudentColor.BLUE,
                StudentColor.YELLOW,
                StudentColor.YELLOW,
                StudentColor.YELLOW,
                StudentColor.PINK,
                StudentColor.RED)),
                c2 = new ExpertCastle(Team.BLACK, 3, List.of(
                StudentColor.BLUE,
                StudentColor.BLUE,
                StudentColor.BLUE,
                StudentColor.BLUE,
                StudentColor.YELLOW,
                StudentColor.YELLOW,
                StudentColor.YELLOW,
                StudentColor.GREEN)),
                c3 = new ExpertCastle(Team.GREY, 3, List.of(
                StudentColor.BLUE,
                StudentColor.BLUE,
                StudentColor.BLUE,
                StudentColor.BLUE,
                StudentColor.YELLOW,
                StudentColor.YELLOW,
                StudentColor.YELLOW,
                StudentColor.PINK));
        List<Castle> castleList = new ArrayList<>(List.of(c1, c2, c3));
        for (Castle castle : castleList) {
            try {
                castle.addStudentsInDiningRoom(castle.getWaitingRoom());
            } catch (TooManyStudentsException e) {throw new RuntimeException(e);}
        }


        StandardCharacter taxman = new StandardCharacter(12);
        ParametersForCharacter par4C = new ParametersForCharacter();
        par4C.setPlacesList(List.of(c1, c2, c3));
        par4C.setNumberOfPlayers(3);
        par4C.setRequestedStudentList(List.of(StudentColor.BLUE));
        try {
            taxman.applyEffect(par4C);
        } catch (StudentException | IllegalAccessException e) {
            fail(e.getMessage());
        }
        assertEquals(   List.of(
                        StudentColor.RED,
                        StudentColor.YELLOW,
                        StudentColor.YELLOW,
                        StudentColor.YELLOW,
                        StudentColor.PINK),
                MapToList.apply(c1.getDiningRoom()).stream().sorted().toList());
        assertEquals(List.of(
                        StudentColor.GREEN,
                        StudentColor.YELLOW,
                        StudentColor.YELLOW,
                        StudentColor.YELLOW,
                        StudentColor.BLUE),
                MapToList.apply(c2.getDiningRoom()).stream().sorted().toList());
        assertEquals(List.of(
                        StudentColor.YELLOW,
                        StudentColor.YELLOW,
                        StudentColor.YELLOW,
                        StudentColor.PINK,
                        StudentColor.BLUE),
                MapToList.apply(c3.getDiningRoom()).stream().sorted().toList());
    }

    @Test
    void TestWrongInput1() {
        assertThrowsExactly(IllegalArgumentException.class,
                () -> expertBoard.playExpertCard(12, 0, List.of()),
                "Taxman Requires a color to be activated.");
    }

    @Test
    void testTaxman() throws ParseException {
        setupTaxman();
        Castle fedesCastle = game.getBoard().getCastle("123");
        try {
            fedesCastle.addStudentsInDiningRoom(List.of(StudentColor.BLUE, StudentColor.BLUE, StudentColor.BLUE));
        } catch (TooManyStudentsException e) {
            e.printStackTrace();
            fail();
        }
        assertEquals(3, fedesCastle.getDiningRoom().get(StudentColor.BLUE));
        setupTaxman();
        Command command = new Command("paychar taxman blue");
        command.setUsername("123");
        game.executeCommand(command);
        assertEquals(0,
                game.getBoard()
                        .getCastle("123")
                        .getDiningRoom()
                        .get(StudentColor.BLUE));
    }

    private void setupTaxman() throws ParseException {
        game = new Game(GameType.NORMAL_2_PLAYER, List.of("123", "456"), 5);
        Command command = new Command("playcard 1");
        command.setUsername("123");
        game.executeCommand(command);
        command = new Command("playcard 2");
        command.setUsername("456");
        game.executeCommand(command);
        command = new Command("movestudentcastle pink pink pink");
        command.setUsername("123");
        game.executeCommand(command);
        command = new Command("movemothernature 1");
        command.setUsername("123");
        game.executeCommand(command);
        command = new Command("choosecloud 2");
        command.setUsername("123");
        game.executeCommand(command);
        command = new Command("movestudentcastle red red pink");
        command.setUsername("456");
        game.executeCommand(command);
        command = new Command("movemothernature 1");
        command.setUsername("123");
        game.executeCommand(command);
        command = new Command("choosecloud 1");
        command.setUsername("123");
        game.executeCommand(command);
        command = new Command("playcard 3");
        command.setUsername("123");
        game.executeCommand(command);
        command = new Command("playcard 4");
        command.setUsername("456");
        game.executeCommand(command);
        command = new Command("movestudentcastle green green green");
        command.setUsername("123");
        game.executeCommand(command);
    }
}
