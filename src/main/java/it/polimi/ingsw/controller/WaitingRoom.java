package it.polimi.ingsw.controller;

import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WaitingRoom {

    private Map<GameType, List<Socket>> gameSocketMap = new HashMap<>();
    private Map<GameType, List<String>> nicknameMap = new HashMap<>();
    private int oldsize2, oldsize3, oldsize4;

    public WaitingRoom(){
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

    public void addPlayer(GameType gameType, Socket socket, String string){
        gameSocketMap.get(gameType).add(socket);
        nicknameMap.get(gameType).add(string);
    }

    public Game computeGameType(int gameId){
        for(GameType g : nicknameMap.keySet()){
            switch (g){
                case NORMAL_2_PLAYER, EXPERT_2_PLAYER -> {
                    if(nicknameMap.get(g).size()%2 == 0 && nicknameMap.get(g).size()>1 && nicknameMap.get(g).size()!=oldsize2) {
                        List<String> nickMap = nicknameMap.get(g).subList((nicknameMap.get(g).size()-2), nicknameMap.get(g).size());
                        List<Socket> socketMap = gameSocketMap.get(g).subList((gameSocketMap.get(g).size()-2), gameSocketMap.get(g).size());
                        oldsize2 = nicknameMap.get(g).size();
                        return new Game(gameId, nickMap, socketMap);
                    }
                }
                case NORMAL_3_PLAYER, EXPERT_3_PLAYER -> {
                    if(nicknameMap.get(g).size()%3==0 && nicknameMap.get(g).size()>1 && nicknameMap.get(g).size()!=oldsize3) {
                        List<String> nickMap = nicknameMap.get(g).subList((nicknameMap.get(g).size()-3), nicknameMap.get(g).size());
                        List<Socket> socketMap = gameSocketMap.get(g).subList((gameSocketMap.get(g).size()-3), gameSocketMap.get(g).size());
                        oldsize3 = nicknameMap.get(g).size();
                        return new Game(gameId, nickMap, socketMap);
                    }
                }
                case NORMAL_4_PLAYER, EXPERT_4_PLAYER -> {
                    if(nicknameMap.get(g).size()%4==0 && nicknameMap.get(g).size()>1 && nicknameMap.get(g).size()!=oldsize4) {
                        List<String> nickMap = nicknameMap.get(g).subList((nicknameMap.get(g).size()-4), nicknameMap.get(g).size());
                        List<Socket> socketMap = gameSocketMap.get(g).subList((gameSocketMap.get(g).size()-4), gameSocketMap.get(g).size());
                        oldsize3 = nicknameMap.get(g).size();
                        return new Game(gameId, nickMap, socketMap);
                    }
                }
            }
        }
        return null;
    }
}
