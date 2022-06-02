package it.polimi.ingsw.server.communication;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.polimi.ingsw.communication.packet.Packet;
import it.polimi.ingsw.communication.packet.PacketParser;
import it.polimi.ingsw.communication.packet.message.Preferences;
import it.polimi.ingsw.server.controller.Game;
import it.polimi.ingsw.server.controller.GameType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.Executors;

import static it.polimi.ingsw.server.controller.GameType.getGameType;

public class ServerMain implements Runnable {
    private final int port;
    private final Map<Socket, ServerReceiver> connectedPlayers = new HashMap<>();
    private final HeartBeatServer heartBeatServer = new HeartBeatServer(connectedPlayers.keySet());
    private final Map<Integer, Game> games = new HashMap<>();
    private final LobbyManager waitingRooms = new LobbyManager(games);

    //private static final Logger logger = Logger.getLogger(ServerMain.class.getName());
    private ServerSocket serverSocket;

    Gson parser = PacketParser.gson;

    public ServerMain(int port) {
        this.port = port;
    }

    public void run(){
        startServer();
        while (true) {
            acceptPlayers();
        }
    }


    /**
     * starts the server on the given port.
     * initiates the heartbeat server on a new thread. (empty)
     */
    public void startServer() {
        //creating server
        //logger.info("Starting server...");
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            System.err.println(e.getMessage()); // Porta non disponibile
            return;
        }
        System.out.println("Server: Server ready on IP: " + serverSocket.getInetAddress() + " port: " + serverSocket.getLocalPort());
        Executors.newCachedThreadPool().submit(heartBeatServer);
    }

    /**
     * Server main method.
     * Waits for players to connect and gets the player preferences.
     * checkGame generates a game when there are enough player connected and moves the players to the relative rooms.
     */
    public void acceptPlayers(){
        //System.out.println("Server: waiting for player to connect");
        try{
            Socket socket= serverSocket.accept();
            GameType playerGameType = handleNewClient(socket);
        } catch(IOException e) {
            System.out.println(e.getMessage());
            try {
                serverSocket = new ServerSocket(port);
                acceptPlayers();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
                System.exit(570); // -> ERROR_NET_OPEN_FAILED
            }// Would get here if server socket was to be closed.
        }

    }

    /**
     * Gets the preference packet and
     * initiate the server receiver relative the new client,
     * puts it in the client list -> connected players,
     * puts it in the heart beat server,
     * put the client in the right lobby looking at the preferences,
     * puts the client receiver on a separate thread
     * //TODO: è giusto un nuovo thread?
     * @return
     */
    private GameType handleNewClient(Socket socket) {
        //listen for preferences
        String input;
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            input = in.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // decode preferences
        Packet preferencesPacket = parser.fromJson(input, Packet.class);
        Preferences preferences = (Preferences) preferencesPacket.getMessage();
        System.out.println(preferences);
        // String nickname = preferences.username();
        // System.out.println(nickname + " joined in");
        GameType playerGameType = null;
        try {
            playerGameType = getGameType(preferences.nPlayer(), preferences.expertMode());
        } catch (IllegalAccessException e) {
            System.out.println(e.getMessage());
            try {
                //with invalid preferences the server closes the connection
                socket.close();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
                System.exit(351);
            }
        }
        //adds the new client
        //starts the receiver
        ServerReceiver serverReceiver = new ServerReceiver(socket, heartBeatServer, null);
        // adds player to list of connected players
        connectedPlayers.put(socket, serverReceiver); //this should also add the client to the HBS.
        //adds player to waiting room
        waitingRooms.addPlayer(playerGameType, serverReceiver, preferences.username());
        Executors.newCachedThreadPool().submit(serverReceiver);
        return playerGameType;
    }

    /**
     * Logger (idea di lorenzo)
     */
    /*public static void init(){
        FileHandler fileHandler;
        try{
            fileHandler = new FileHandler( //FIXME: don't hard code path
                    "C:\\Users\\loren\\Desktop\\Università\\3° anno\\Progetto di ingegneria del software\\ingsw2022-AM01\\src\\main\\java\\it\\polimi\\ingsw\\controller\\LogFIle.log");
            logger.addHandler(fileHandler);
            SimpleFormatter formatter = new SimpleFormatter();
            fileHandler.setFormatter(formatter);
            logger.setLevel(Level.FINE);
            logger.info("Logger has been initialized.");
        }catch (Exception e){
            logger.log(Level.WARNING, "Exception: ", e);
        }
    }*/

    public Map<Socket, ServerReceiver> getConnectedPlayers() {
        return new HashMap<>(connectedPlayers);
    }

    public int getGamesNumber(GameType type){
        int numberOfGames = 0;
        for (Game game : games.values()) {
            if(game.getGameType() == type)
                numberOfGames++;
        }
        return numberOfGames;
    }

}
