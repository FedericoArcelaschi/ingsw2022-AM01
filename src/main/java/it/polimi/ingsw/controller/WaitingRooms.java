package it.polimi.ingsw.controller;

import com.google.gson.Gson;
import it.polimi.ingsw.communication.ServerReceiver;
import it.polimi.ingsw.communication.packet.MessageType;
import it.polimi.ingsw.communication.packet.Packet;
import it.polimi.ingsw.communication.packet.message.LobbyInfoMessage;
import it.polimi.ingsw.communication.packet.message.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WaitingRooms {

    private Map<GameType, List<ServerReceiver>> gameSocketMap = new HashMap<>();
    private Map<GameType, List<String>> nicknameMap = new HashMap<>();
    private Map<GameType, Integer> gameTypeSize = new HashMap<>();
    Gson parser = new Gson();

    public WaitingRooms(){
        for(GameType g : GameType.values()){
            gameSocketMap.computeIfAbsent(g, k -> new ArrayList<>());
            nicknameMap.computeIfAbsent(g, k -> new ArrayList<>());
            gameTypeSize.putIfAbsent(g, 0);
        }
    }

    public Map<GameType, List<ServerReceiver>> getGameSocketMap() {
        return gameSocketMap;
    }

    public Map<GameType, List<String>> getNicknameMap() {
        return nicknameMap;
    }


    /**
     * Adds a player to the requested lobby. Informs players of the same lobby that the state of the lobby changed.
     * @param gameType the provided gametype.
     * @param serverReceiver the serverReceiver of the new player.
     * @param nickname the player name.
     * @throws IOException
     */
    public void addPlayer(GameType gameType, ServerReceiver serverReceiver, String nickname) {
        gameSocketMap.get(gameType).add(serverReceiver);
        nicknameMap.get(gameType).add(nickname);
        System.out.println(nicknameMap.get(gameType));
        switch(gameType){
            case NORMAL_2_PLAYER, EXPERT_2_PLAYER -> informPlayers(gameType, 2);
            case NORMAL_3_PLAYER, EXPERT_3_PLAYER -> informPlayers(gameType, 3);
            case NORMAL_4_PLAYER, EXPERT_4_PLAYER -> informPlayers(gameType, 4);
        }
    }

    /**
     * Method used to send to the rest of the players who else joined the lobby.
     * @param gameType the provided gametype.
     * @param numberOfPlayers the amount of players expected by the game type.
     * @throws IOException
     */
    private void informPlayers(GameType gameType, int numberOfPlayers) {
        if(nicknameMap.get(gameType).size() >= 1 & nicknameMap.get(gameType).size()%numberOfPlayers != 0){
            List<String> playersIn = new ArrayList<>(nicknameMap.get(gameType).subList((nicknameMap.get(gameType).size()-nicknameMap.get(gameType).size()%numberOfPlayers), nicknameMap.get(gameType).size()));
            Message l = new LobbyInfoMessage(nicknameMap.get(gameType), gameType);
            Packet packet = new Packet(MessageType.LOBBY, l);
            List<ServerReceiver> playersInLobby = new ArrayList<>(gameSocketMap.get(gameType).subList((gameSocketMap.get(gameType).size()-nicknameMap.get(gameType).size()%numberOfPlayers),nicknameMap.get(gameType).size()));
            for(ServerReceiver sr : playersInLobby){
                try {
                    PrintWriter out = new PrintWriter(sr.getSocket().getOutputStream(), true);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                //out.println(parser.toJson(packet));
            }
        }
    }


    /**
     * Computes the game type according to the player's preferences, if there are enough players.
     * Returns null otherwise.
     * @param gameId
     * @return the correct game.
     */
    public Game computeGameType(int gameId){
        Game game = null;
        for(GameType g : nicknameMap.keySet()){
            switch (g){  //TODO: REDO SUBMITGAME FUNCTION TO HANDLE EXPERTMODE
                case NORMAL_2_PLAYER, EXPERT_2_PLAYER -> game =  submitGame(g, gameId, 2);
                case NORMAL_3_PLAYER, EXPERT_3_PLAYER -> game =  submitGame(g, gameId, 3);
                case NORMAL_4_PLAYER, EXPERT_4_PLAYER -> game =  submitGame(g, gameId, 4);
            }
        }
        return game;
    }

    private Game submitGame(GameType g, int gameId, int numberOfPlayers){
        if(nicknameMap.get(g).size()%numberOfPlayers==0 && nicknameMap.get(g).size()>1 && nicknameMap.get(g).size()!=gameTypeSize.get(g)) {
            System.out.println("Gets here");
            List<String> nickMap = nicknameMap.get(g).subList((nicknameMap.get(g).size()-numberOfPlayers), nicknameMap.get(g).size());
            List<ServerReceiver> socketMap = gameSocketMap.get(g).subList((gameSocketMap.get(g).size()-numberOfPlayers), gameSocketMap.get(g).size());
            gameTypeSize.replace(g, nicknameMap.get(g).size());
            return new Game(g, gameId, nickMap, socketMap);
        }
        return null;
    }

    /**
     * Removes a player from the queue.
     * @param player the player to remove.
     * @param g the lobby from which the player should be removed.
     */
    public void removeFromQueue(String player, GameType g){
        switch (g){
            case NORMAL_2_PLAYER, EXPERT_2_PLAYER ->{
                if(nicknameMap.get(g).size()%2 != 0 && nicknameMap.get(g).get(nicknameMap.get(g).size()).equals(player)) nicknameMap.get(g).remove(player);
            }
            case NORMAL_3_PLAYER, EXPERT_3_PLAYER ->{
                if(nicknameMap.get(g).size()%3 != 0 &&
                        nicknameMap.get(g).subList((nicknameMap.get(g).size()-nicknameMap.get(g).size()%3), nicknameMap.get(g).size()).contains(player))
                    nicknameMap.get(g).remove(player);
            }
            case NORMAL_4_PLAYER, EXPERT_4_PLAYER ->{
                if(nicknameMap.get(g).size()%4 != 0 &&
                        nicknameMap.get(g).subList((nicknameMap.get(g).size()-nicknameMap.get(g).size()%4), nicknameMap.get(g).size()).contains(player))
                    nicknameMap.get(g).remove(player);
            }
        }
    }
}
