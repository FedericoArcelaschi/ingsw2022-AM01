package it.polimi.ingsw.controller;

import com.google.gson.Gson;
import it.polimi.ingsw.communication.HeartBeatServer;
import it.polimi.ingsw.communication.packet.Packet;
import it.polimi.ingsw.communication.packet.message.Preferences;
import it.polimi.ingsw.communication.ServerReceiver;
import it.polimi.ingsw.model.Color;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerMain implements Runnable{

    private final int port;
    private ExecutorService executor;
    private ServerSocket serverSocket;
    private final Map<String,Socket> connectedPlayers;
    private HeartBeatServer heartBeatServer;
    private final WaitingRooms waitingRooms;
    private final Map<GameType, Integer> gamesNumber;

    public ServerMain(int port) {
        this.port = port;
        this.connectedPlayers = new HashMap<>();
        this.waitingRooms = new WaitingRooms();
        this.gamesNumber = new HashMap<>();
        for (GameType gt: GameType.values()) {
            gamesNumber.put(gt,0);
        }
    }

    public void run(){
        startServer();
        acceptPlayers();
    }


    /**
     * start the server
     */
    public void startServer() {
        executor = Executors.newCachedThreadPool();

        //creating serverSocket
        System.out.println("Server: Starting server...");
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            System.err.println(e.getMessage()); // Porta non disponibile
            return;
        }

        System.out.println("Server: Server ready on IP: " + serverSocket.getInetAddress() + " port: " + serverSocket.getLocalPort());
        heartBeatServer = new HeartBeatServer();
        executor.submit(heartBeatServer);
    }

    /**
     * wait for players to connect and generate a game when there are 2 players connected
     */
    public void acceptPlayers(){
        int gameId = 0;
        Gson parser = new Gson();
        while (true) {
            try{
                System.out.println("Server: waiting for player to connect");
                Socket socket = serverSocket.accept();
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String input
                        = in.readLine();
                Packet preferencesPacket
                        = parser.fromJson(input, Packet.class);
                Preferences preferences
                        = parser.fromJson(preferencesPacket.getMessageJson(), Preferences.class);
                String nickname
                        = preferences.username();
                System.out.println(nickname + " joined in");
                connectedPlayers.put(nickname, socket);
                GameType playerGameType
                        = GameType.getGameType(preferences.nPlayer(), preferences.expertMode());
                waitingRooms.addPlayer(playerGameType, socket, nickname);
                heartBeatServer.addClient(socket); //the heartbeat ping start before the game is started
                //FIXME: if the client before game starting he must be removed from queue;
                Game game = waitingRooms.computeGameType(gameId);
                if(game != null) {
                    gamesNumber.replace(game.getGameType(), gamesNumber.get(game.getGameType())+1);
                    System.out.println(Color.YELLOW.colorCode + "Server: created game " + gameId + " with players: " + game.toStringPlayers() + "\u001B[0m");
                    for (Socket player : game.getGameSocketList()) {
                        executor.submit(new ServerReceiver(player, heartBeatServer, game));
                    }
                    gameId++; //Has to be increased only if method returns null
                }
            } catch(IOException e) {
                break; // Entrerei qui se serverSocket venisse chiuso
            }
        }
        executor.shutdown();
    }

    public Map<String, Socket> getConnectedPlayers() {
        Map<String,Socket> newConnectedPlayers;
        synchronized (connectedPlayers){
            newConnectedPlayers = new HashMap<>(connectedPlayers);
            return newConnectedPlayers;
        }
    }

    public int getGamesNumber(GameType type){
        return gamesNumber.get(type);
    }
}
