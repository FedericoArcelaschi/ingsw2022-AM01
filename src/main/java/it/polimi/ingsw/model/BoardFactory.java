package it.polimi.ingsw.model;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class BoardFactory {
    public static Board getBoard(List<String> playerList, Turn turn){
        return createBoard(playerList, turn, 1);
    }

    public static Board getBoard(List<String> playerList, Turn turn, long seed){
        return createBoard(playerList, turn, seed);
    }

    @NotNull
    private static Board createBoard(List<String> playerList, Turn turn, long seed) {
        Board b;
        switch (playerList.size()){
            case 2 -> b = new Board(playerList.get(0),playerList.get(1), turn, seed);
            case 3 -> b = new Board(playerList.get(0),playerList.get(1),playerList.get(2), turn, seed);
            case 4 -> b = new Board(playerList.get(0),playerList.get(1),playerList.get(2),playerList.get(3), turn, seed);
            default -> throw new IllegalArgumentException("too many students");
        }
        return b;
    }
}
