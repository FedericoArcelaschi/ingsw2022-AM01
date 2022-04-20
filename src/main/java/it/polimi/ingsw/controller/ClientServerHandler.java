package it.polimi.ingsw.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientServerHandler implements Runnable {

    private final Socket socket;
    private final Game game;

    public ClientServerHandler(Socket socket, Game game) {
        this.socket = socket;
        this.game = game;
    }
    @Override
    public void run() {
        try {
            Scanner in = new Scanner(socket.getInputStream());
            PrintWriter out = new PrintWriter(socket.getOutputStream());
            // Leggo e scrivo nella connessione finche' non ricevo "quit"
            while (true) {
                String line = in.nextLine();
                if (line.equals("quit")) {
                    break;
                } else {
                    game.executeCommand(line);
                    out.println("Received: " + line);
                    out.flush();
                }
            }
            System.out.println("closing socket: "+ socket.toString());
            // Closing stream and client socket
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
