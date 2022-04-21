package it.polimi.ingsw.controller;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerMain {

    private final int port;

    ExecutorService executor = Executors.newCachedThreadPool();
    ServerSocket serverSocket;

    public ServerMain(int port) {
        this.port = port;
    }

    /**
     * start the server
     */
    public void startServer() {
        executor = Executors.newCachedThreadPool();

        //creating serverSocket
        System.out.println("Starting server...");
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            System.err.println(e.getMessage()); // Porta non disponibile
            return;
        }

        System.out.println("Server ready");
    }

    /**
     * wait for players to connect and generate a game when there are 2 players connected
     */
    public void acceptPlayers(){
        int gameId = 0;
        while (true) {
            List<Socket> gameList= new ArrayList<>();
            List<String> nicknameList= new ArrayList<>();
            try{
                for (int numPlayers = 0; numPlayers < 2; numPlayers++) {
                    System.out.println("waiting for player to connect");
                    Socket socket = serverSocket.accept();
                    String nickname = new Scanner(socket.getInputStream()).nextLine();
                    System.out.println(nickname+" joined in");
                    gameList.add(socket);
                    nicknameList.add(nickname);
                }
                System.out.println("creating game " + gameId);
                Game g = new Game(gameId, nicknameList);
                for(Socket player : gameList){
                    executor.submit(new ClientHandler(player, g));
                }
            } catch(IOException e) {
                break; // Entrerei qui se serverSocket venisse chiuso
            }
            gameId++;
        }
        executor.shutdown();
    }
    public static void main(String[] args) {
        ServerMain echoServer = new ServerMain(1234);

        echoServer.startServer();
        echoServer.acceptPlayers();
    }
}
