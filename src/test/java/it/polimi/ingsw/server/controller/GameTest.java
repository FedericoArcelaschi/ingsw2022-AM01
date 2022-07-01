package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.communication.command.Command;
import it.polimi.ingsw.communication.message.Message;
import it.polimi.ingsw.communication.message.subclasses.EndGame;
import it.polimi.ingsw.communication.message.subclasses.Error;
import it.polimi.ingsw.communication.message.subclasses.Update;
import it.polimi.ingsw.communication.modelData.BoardData;
import it.polimi.ingsw.server.communication.HeartBeatServer;
import it.polimi.ingsw.server.communication.ServerReceiver;
import it.polimi.ingsw.server.model.baseLogic.Card;
import it.polimi.ingsw.server.model.baseLogic.Team;
import it.polimi.ingsw.server.model.baseLogic.TurnPhase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.Socket;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    String player1 = "Lucio Battisti", player2 = "Lucio Dalla";
    Game game;
    @BeforeEach
    void setUp() {
        game = new Game(GameType.NORMAL_2_PLAYER, List.of(player1, player2));
    }

    @Test
    void testPlayCard() throws ParseException {
        Command playCardCommand = new Command("playcard 3");
        playCardCommand.setUsername(player1);
        BoardData newGameState
                = ((Update) game
                .executeCommand(playCardCommand)
                .get(player1))
                .getBoardData();
        assertEquals("Lucio Dalla", newGameState.turn().currentPlayer());
        assertEquals(TurnPhase.PLANNING, newGameState.turn().currentPhase());
        assertEquals(new Card(3).toString(), newGameState.myCastle().lastPlayedCard());
    }

    @Test
    void testMoveStudentIslandException() throws ParseException {
        Command moveStudentIslandExceptionCommand = new Command("movestudentisland green 3");
        moveStudentIslandExceptionCommand.setUsername(player1);
        Message errorMessage = game
                .executeCommand(moveStudentIslandExceptionCommand)
                .get(player1);
        assertTrue(errorMessage instanceof Error);
    }

    @Test
    void wrongCurrentPlayer() throws ParseException {
        Command moveStudentIslandExceptionCommand = new Command("playcard 3");
        assertEquals(game.getBoard().getCurrentPlayer(), player1);
        moveStudentIslandExceptionCommand.setUsername(player2);
        Message errorMessage = game
                .executeCommand(moveStudentIslandExceptionCommand)
                .get(player2);
        assertTrue(errorMessage instanceof Error);
    }

    @Test
    void winUpdateTest() {
        Map<String, Message> stringMessageMap =
                game.winUpdate(Team.GREY);
        assertTrue(stringMessageMap.get(player1) instanceof EndGame,
                "winUpdates returns 1 endGame message for 'Lucio Battisti'");
        assertTrue(stringMessageMap.get(player2) instanceof EndGame,
                "winUpdates returns 1 endGame message for 'Lucio Dalla'");
        assertEquals(stringMessageMap.entrySet().size(), 2,
                "winUpdates returns 2 messages");
    }
}