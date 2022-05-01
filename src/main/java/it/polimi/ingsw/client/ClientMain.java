package it.polimi.ingsw.client;

import com.google.gson.Gson;
import it.polimi.ingsw.communication.*;
import it.polimi.ingsw.model.Board;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientMain {
    private final String username;
    private final int preferenceNPlayer;
    private final boolean preferenceExpertMode;
    private final String IP;
    private final int port;
    public Socket socket = null;
    private ClientSender cs;
    private ClientReceiver cr;
    private Board board;

    public ClientMain(String username, int preferenceNPlayer, boolean preferenceExpertMode, String IP, int port) {
        this.username=username;
        this.preferenceNPlayer=preferenceNPlayer;
        this.preferenceExpertMode=preferenceExpertMode;
        this.IP = IP;
        this.port = port;
    }

    public void connect(){
        System.out.println(username + ":  attempting connection");
        try {
            ExecutorService executor = Executors.newCachedThreadPool();
            Gson parser = new Gson();
            socket = new Socket(IP, port);
            System.out.println(username + ":  connected");
            cs = new ClientSender(socket);
            cr = new ClientReceiver(this,socket);
            //send player preferences to the server;
            Preferences preferences = new Preferences(username, preferenceNPlayer, preferenceExpertMode);
            cs.sendPreferences(preferences);
            //run the ClientReceiver
            executor.submit(cr);
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + IP);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +
                    IP);
            System.exit(1);
        }
    }

    public boolean runCommand(String stringCommand){
        if (socket == null || socket.isClosed()) {
            return false;
        }
        //compose command and send
        Command command = Command.createCommand(username, stringCommand);
        if((command != null ? command.getType() : null) == CommandType.GET){
            System.out.println(getCommand(command));
        }
        else cs.sendCommand(command);
        return true;
    }


    private String getCommand(Command command){
        Map<CommandAttribute,String> attributeMap = command.getAttributesMap();
        String username = command.getUsername();
        switch (attributeMap.get(CommandAttribute.WHAT)) {
            case "deck" -> {
                return board.getDeck(username).toString();
            }
            case "professors" -> {
                return board.getProfessorsMap().toString();
            }
        }
        return "Command was not successful. Please, try again.";
    }

    public String getUsername() {
        return username;
    }
}
