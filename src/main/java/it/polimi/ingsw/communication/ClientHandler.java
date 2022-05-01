package it.polimi.ingsw.communication;

import com.google.gson.Gson;
import it.polimi.ingsw.communication.modelData.BoardData;
import it.polimi.ingsw.controller.Game;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientHandler implements Runnable {

    private final Socket socket;
    private final Game game;

    public ClientHandler(Socket socket, Game game) {
        this.socket = socket;
        this.game = game;
    }
    @Override
    public void run() {
        try {
            Gson parser = new Gson();
            Scanner in = new Scanner(socket.getInputStream());
            PrintWriter out = new PrintWriter(socket.getOutputStream());
            // Leggo e scrivo nella connessione finche' non ricevo "quit"
            while (true) {
                String line = in.nextLine();
                if (line.equals("quit")) { //needs to be changed accordingly to how we want to interrupt connection
                    break;
                } else {
                    String responseMessage = game.executeCommand(parser.fromJson(line, Command.class));
                    System.out.println(responseMessage);
                    Message message = new Message(MessageType.UPDATE, new BoardData("--",game.getBoard()));
                    System.out.println(parser.toJson(message, Message.class));
                    out.println(parser.toJson(message, Message.class));
                    out.flush();
                }
            }
            System.out.println("closing socket: "+ socket);
            // Closing stream and client socket
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
