package it.polimi.ingsw.server.communication;

import it.polimi.ingsw.server.controller.GameType;
import org.apache.logging.log4j.LogManager;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.apache.logging.log4j.*;
import org.jetbrains.annotations.NotNull;

public class ServerMain implements Runnable {

    private static final Logger logger = LogManager.getLogger(ServerMain.class);

    private int port;
    private final int DEFAULT_PORT = 12345;
    private final ExecutorService executor = Executors.newCachedThreadPool();
    private ServerSocket serverSocket;
    private HeartBeatServer heartBeatServer;
    private final LobbyManager lobbyManager = new LobbyManager();

    public ServerMain() {
        this.port = DEFAULT_PORT;
    }

    public ServerMain(int port) {
        this.port = port;
    }

    public void run() {
        startServer();
        acceptPlayers();
    }

    public void startServer() {
        logger.info("Starting server...");
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            logger.error(e.getMessage() + "address already in use");
            e.printStackTrace();
            port++;
            startServer();
        }
        logger.info("ready on IP: " + serverSocket.getInetAddress() + " port: " + serverSocket.getLocalPort());
        heartBeatServer = new HeartBeatServer();
        executor.submit(heartBeatServer);

    }

    /**
     * wait for players to connect. adds the player to the heartbeatserver and the lobby.
     */
    public void acceptPlayers() {
        Socket socket;
        while (true) {
            logger.info("Server: waiting for player to connect");
            try{
                socket = serverSocket.accept();
            } catch(IOException e) {
                logger.error(e);
                break;
            }
            logger.info("player connected on port " + socket.getPort());
            Client client = new Client(socket, heartBeatServer, lobbyManager, executor);
            lobbyManager.addPlayerNoPreferences(client);
            //those methods need to be split because of future changes
        }
        executor.shutdown();
    }

    public int getGamesNumber(GameType type){
        return lobbyManager.countGames(type);
    }
}
