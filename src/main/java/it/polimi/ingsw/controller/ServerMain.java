package it.polimi.ingsw.controller;

import com.google.gson.Gson;
import it.polimi.ingsw.communication.ClientReceiver;
import it.polimi.ingsw.communication.HeartBeatServer;
import it.polimi.ingsw.communication.packet.Packet;
import it.polimi.ingsw.communication.packet.message.Preferences;
import it.polimi.ingsw.communication.ServerReceiver;
import it.polimi.ingsw.model.StudentColor;
import org.apache.maven.plugin.logging.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.*;

public class ServerMain implements Runnable{

    private final int port;
    private ExecutorService executor;
    private ServerSocket serverSocket;
    private final Map<String, ServerReceiver> connectedPlayers;
    private HeartBeatServer heartBeatServer;
    private final WaitingRooms waitingRooms;
    private final Map<GameType, Integer> gamesNumber;
    private static final Logger logger = Logger.getLogger(ServerMain.class.getName());

    Gson parser;

    public static void init(){
        FileHandler fh;
        try{
            fh = new FileHandler("C:\\Users\\loren\\Desktop\\Università\\3° anno\\Progetto di ingegneria del software\\ingsw2022-AM01\\src\\main\\java\\it\\polimi\\ingsw\\controller\\LogFIle.log");
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);
            logger.setLevel(Level.FINE);
            logger.info("Logger has been initialized.");
        }catch (Exception e){
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
        parser = new Gson();
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
    public void acceptPlayers(){
        int gameId = 0;
        while (true) {
            try{
                System.out.println("Server: waiting for player to connect");
                Socket socket = serverSocket.accept();

                handleNewClient(socket);

                //FIXME: if a client disconnect before game starting he must be removed from queue;

                Game game = waitingRooms.computeGameType(gameId);
                if(game != null) {
                    gamesNumber.replace(game.getGameType(), gamesNumber.get(game.getGameType())+1);
                    System.out.println(StudentColor.YELLOW.colorCode + "Server: created game " + gameId + " with players: " + game.toStringPlayers() + "\u001B[0m");
                    for (ServerReceiver serverReceiver : game.getGameServerReceiverList()) {
                        serverReceiver.setGame(game);
                    }
                    gameId++; //Has to be increased only if method returns null
                }
            } catch(IOException e) {
                break; // Would get here if serversocket was to be closed.
            }
        }
        executor.shutdown();
    }

    private void handleNewClient(Socket socket){
        //listen for preferences
        String input;
        try{
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            input = in.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //decode preferences
        Packet preferencesPacket = parser.fromJson(input, Packet.class);
        Preferences preferences = parser.fromJson(preferencesPacket.getMessageJson(), Preferences.class);
        String nickname = preferences.username();
        System.out.println(nickname + " joined in");
        GameType playerGameType = GameType.getGameType(preferences.nPlayer(), preferences.expertMode());
        //add the new client
        heartBeatServer.addClient(socket); //the heartbeat ping starts before the game is started
        //start the receiver
        ServerReceiver sr = new ServerReceiver(socket, heartBeatServer, null);
        //add player to waiting room
        waitingRooms.addPlayer(playerGameType, sr, nickname);

        executor.submit(sr);
        //add player to list of connected players
        connectedPlayers.put(nickname, sr);
    }


    public Map<String, ServerReceiver> getConnectedPlayers() {
        return new HashMap<>(connectedPlayers);
    }

    public int getGamesNumber(GameType type){
        return gamesNumber.get(type);
    }
}
