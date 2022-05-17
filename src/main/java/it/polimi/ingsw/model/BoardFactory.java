package it.polimi.ingsw.model;

import java.util.List;
import java.util.Random;

public class BoardFactory {
    public static Board getBoard(List<String> playerList, boolean expert, Turn turn){
        Random random = new Random();
        long seed = random.nextLong();
        return createBoard(playerList, turn, seed);
    }

    public static Board getBoard(List<String> playerList, boolean expert, Turn turn, int seed){
        return createBoard(playerList, turn, seed);
    }

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
