package it.polimi.ingsw.controller;

import com.google.gson.Gson;
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

    private Map<GameType, List<Socket>> gameSocketMap = new HashMap<>();
    private Map<GameType, List<String>> nicknameMap = new HashMap<>();
    private int oldsize2 = 0, oldsize3 = 0, oldsize4 = 0;
    Gson parser = new Gson();

    public WaitingRooms(){
        for(GameType g : GameType.values()){
            gameSocketMap.computeIfAbsent(g, k -> new ArrayList<>());
            nicknameMap.computeIfAbsent(g, k -> new ArrayList<>());
        }
    }

    public Map<GameType, List<Socket>> getGameSocketMap() {
        return gameSocketMap;
    }

    public Map<GameType, List<String>> getNicknameMap() {
        return nicknameMap;
    }

    public void addPlayer(GameType gameType, Socket socket, String string) throws IOException {
        gameSocketMap.get(gameType).add(socket);
        nicknameMap.get(gameType).add(string);
        switch(gameType){
            case NORMAL_2_PLAYER, EXPERT_2_PLAYER ->{
                List<String> playersIn = new ArrayList<>(nicknameMap.get(gameType).subList((nicknameMap.get(gameType).size()-2),nicknameMap.get(gameType).size()));
                Message l = new LobbyInfoMessage(String.join(", ", playersIn));
                Packet packet = new Packet(MessageType.LOBBY, l);
                for(Socket s : gameSocketMap.get(gameType).subList((gameSocketMap.get(gameType).size()-2),nicknameMap.get(gameType).size())){
                    PrintWriter out = new PrintWriter(s.getOutputStream(), true);
                    out.println(parser.toJson(packet));
                }
            }
            case NORMAL_3_PLAYER, EXPERT_3_PLAYER ->{
                List<String> playersIn = new ArrayList<>(nicknameMap.get(gameType).subList((nicknameMap.get(gameType).size()-3),nicknameMap.get(gameType).size()));
                Message l = new LobbyInfoMessage(String.join(", ", playersIn));
                Packet packet = new Packet(MessageType.LOBBY, l);
                for(Socket s : gameSocketMap.get(gameType).subList((gameSocketMap.get(gameType).size()-3),gameSocketMap.get(gameType).size())){
                    PrintWriter out = new PrintWriter(s.getOutputStream(), true);
                    out.println(parser.toJson(packet));
                }
            }
            case NORMAL_4_PLAYER, EXPERT_4_PLAYER ->{
                List<String> playersIn = new ArrayList<>(nicknameMap.get(gameType).subList((nicknameMap.get(gameType).size()-4),nicknameMap.get(gameType).size()));
                Message l = new LobbyInfoMessage(String.join(", ", playersIn));
                Packet packet = new Packet(MessageType.LOBBY, l);
                for(Socket s : gameSocketMap.get(gameType).subList((gameSocketMap.get(gameType).size()-4),gameSocketMap.get(gameType).size())){
                    PrintWriter out = new PrintWriter(s.getOutputStream(), true);
                    out.println(parser.toJson(packet));
                }
            }
        }

    }

    public Game computeGameType(int gameId){
        for(GameType g : nicknameMap.keySet()){
            switch (g){
                case NORMAL_2_PLAYER, EXPERT_2_PLAYER -> {
                    if(nicknameMap.get(g).size()%2 == 0 && nicknameMap.get(g).size()>1 && nicknameMap.get(g).size()!=oldsize2) {
                        List<String> nickMap = nicknameMap.get(g).subList((nicknameMap.get(g).size()-2), nicknameMap.get(g).size());
                        List<Socket> socketMap = gameSocketMap.get(g).subList((gameSocketMap.get(g).size()-2), gameSocketMap.get(g).size());
                        oldsize2 = nicknameMap.get(g).size();
                        return new Game(GameType.NORMAL_2_PLAYER, gameId, nickMap, socketMap);
                    }
                }
                case NORMAL_3_PLAYER, EXPERT_3_PLAYER -> {
                    if(nicknameMap.get(g).size()%3==0 && nicknameMap.get(g).size()>1 && nicknameMap.get(g).size()!=oldsize3) {
                        List<String> nickMap = nicknameMap.get(g).subList((nicknameMap.get(g).size()-3), nicknameMap.get(g).size());
                        List<Socket> socketMap = gameSocketMap.get(g).subList((gameSocketMap.get(g).size()-3), gameSocketMap.get(g).size());
                        oldsize3 = nicknameMap.get(g).size();
                        return new Game(GameType.NORMAL_3_PLAYER, gameId, nickMap, socketMap);
                    }
                }
                case NORMAL_4_PLAYER, EXPERT_4_PLAYER -> {
                    if(nicknameMap.get(g).size()%4==0 && nicknameMap.get(g).size()>1 && nicknameMap.get(g).size()!=oldsize4) {
                        List<String> nickMap = nicknameMap.get(g).subList((nicknameMap.get(g).size()-4), nicknameMap.get(g).size());
                        List<Socket> socketMap = gameSocketMap.get(g).subList((gameSocketMap.get(g).size()-4), gameSocketMap.get(g).size());
                        oldsize4 = nicknameMap.get(g).size();
                        return new Game(GameType.NORMAL_4_PLAYER, gameId, nickMap, socketMap);
                    }
                }
            }
        }
        return null;
    }

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
        //nicknameMap.get(g).remove(player);
    }
}
