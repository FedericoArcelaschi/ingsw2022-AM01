package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.communication.command.Command;
import it.polimi.ingsw.communication.command.CommandType;
import it.polimi.ingsw.communication.message.Message;
import it.polimi.ingsw.server.communication.Client;
import it.polimi.ingsw.server.communication.ClientList;
import org.junit.jupiter.api.Test;

import java.net.Socket;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

public class GameInterfaceTest {

    private class GameInterfaceStub {
        private Game game;
        private ClientList clients;
        private GameType gameType;

        GameInterfaceStub(GameType gameType, ClientList clients) {
            this.clients = clients;
            this.game = new Game(gameType, clients.getClients().stream().map(Client::username).toList());
            System.out.println(game.updateAll().values().stream().map(MessageUsername::message).toList());
            this.gameType = gameType;
        }

        public void executeCommand(Command command, Socket socket) {
            System.out.println("executing command: " + command.getType().name().toLowerCase());
            if (clients.getClients().stream()
                    .anyMatch(i -> (i.clientsSocket() == socket) && (i.username().equals(command.getUsername())))) {
                System.out.println(game.executeCommand(command).values().stream().map(MessageUsername::message).toList());
            } else
                throw new IllegalCallerException("wrong username-socket match. Deleted command.");

        }

        private void send(MessageUsernameSet messageUsernameSet) {
            messageUsernameSet.values().forEach(messageUsername ->
                    send(messageUsername.message(),
                            clients.getClients().stream()
                                    .filter(i -> i.username().equals(messageUsername.addressee()))
                                    .findAny().get().clientsSocket()));
        }

        private void send(Message message, Socket addressee) {
        }

        public GameType getGameType() {
            return gameType;
        }

    }
}

//    @Test
//    void GameInterfaceStubTest() {
//        Socket socketGiulio = new Socket();
//        Socket socketVerita = new Socket();
//        ClientList clientList = new ClientList(new Client("giulio regeni", socketGiulio, null)).add(new Client("verità per", socketVerita, null));
//        GameInterfaceStub gameInterface = new GameInterfaceStub(GameType.NORMAL_2_PLAYER, clientList);
//
//
//        gameInterface.executeCommand(new Command("verità per", CommandType.PLAY_CARD, List.of("3")), socketVerita);
//        assertThrowsExactly(IllegalCallerException.class,
//                ()-> gameInterface.executeCommand(new Command("giulio regeni", CommandType.PLAY_CARD, List.of("4")), socketVerita),
//                "SOCKET != dal mittente del messaggio");
//        gameInterface.executeCommand(new Command("giulio regeni", CommandType.PLAY_CARD, List.of("4")), socketGiulio);
//    }
//}

