package it.polimi.ingsw.model;

import java.util.List;

public class BoardFactory {
    public static Board getBoard(List<String> playerList, Turn turn){
        Board b;
        switch (playerList.size()){
            case 2 -> b = new Board(playerList.get(0),playerList.get(1), turn);
            case 3 -> b = new Board(playerList.get(0),playerList.get(1),playerList.get(2), turn);
            case 4 -> b = new Board(playerList.get(0),playerList.get(1),playerList.get(2),playerList.get(3), turn);
            default -> throw new IllegalArgumentException("too many students");
        }
        return b;
    }
}
