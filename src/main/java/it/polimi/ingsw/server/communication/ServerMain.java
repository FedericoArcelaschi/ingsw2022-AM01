package it.polimi.ingsw.server.communication;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import it.polimi.ingsw.communication.message.Message;
import it.polimi.ingsw.communication.message.subclasses.Preferences;
import it.polimi.ingsw.server.controller.Game;
import it.polimi.ingsw.server.controller.GameType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.*;

public class ServerMain implements Runnable {

    private final int port;
    private ExecutorService executor;
    private ServerSocket serverSocket;
    private final Map<String, ServerReceiver> connectedPlayers;
    private HeartBeatServer heartBeatServer;
    private final WaitingRooms waitingRooms;
    private final Map<GameType, Integer> gamesNumber;
    private static final Logger logger = Logger.getLogger(ServerMain.class.getName());
    private int gameId = 0;

    public static void init() {
        FileHandler fileHandler;
        try {
            fileHandler = new FileHandler(System.getProperty("user.dir"));
            logger.addHandler(fileHandler);
            SimpleFormatter formatter = new SimpleFormatter();
            fileHandler.setFormatter(formatter);
            logger.setLevel(Level.FINE);
            logger.info("Logger has been initialized.");
        } catch (Exception e){
            logger.log(Level.WARNING, "Exception: ", e);
        }
    }

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

        //creating server socket
        logger.info("Starting server...");
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
    public void acceptPlayers() {

        while (true) {
            Socket socket;
            try{
                System.out.println("Server: waiting for player to connect");
                socket = serverSocket.accept();
                //FIXME: if a client disconnects before game starting he must be removed from queue;
            } catch(IOException e) {
                System.err.println(e.getMessage());
                break; // Would get here if server socket was to be closed.
            }
            GameType playerGameType = handleNewClient(socket);
            if(playerGameType != null)
                handleGame(playerGameType);
        }
        executor.shutdown();
    }


    private void handleGame(GameType gameType) {
        Game game = waitingRooms.submitGame(gameId, gameType);
        if(game != null) {
            gamesNumber.replace(game.getGameType(), gamesNumber.get(game.getGameType()) + 1);
            logger.info("Server: created game " + gameId + " with players: " + game.toStringPlayers());
            for (ServerReceiver serverReceiver : game.getGameServerReceiverList()) {
                serverReceiver.setGame(game);
            }
            gameId++; //Has to be increased only if method does not return null
        }
    }


    private GameType handleNewClient(Socket socket) {
        String input;
        try{ //server receives preferences
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            input = in.readLine();
        } catch (IOException e) {
            System.err.println(e.getMessage());
            return null;
        }
        Preferences preferences;
        try {
            preferences = (Preferences) new Gson().fromJson(input, Message.class);
        } catch(JsonParseException e) {
            System.err.println("not a preference packet!!");
            e.printStackTrace();
            return null;
        }
        System.out.println(preferences.username() + " joined in");
        heartBeatServer.addClient(socket);
        ServerReceiver sr = new ServerReceiver(socket, heartBeatServer, preferences.username());
        waitingRooms.addPlayer(preferences.getGameType(), sr);
        executor.submit(sr);
        connectedPlayers.put(preferences.username(), sr);
        return preferences.getGameType();
    }

    public Map<String, ServerReceiver> getConnectedPlayers() {
        return new HashMap<>(connectedPlayers);
    }

    public int getGamesNumber(GameType type){
        return gamesNumber.get(type);
    }

}
