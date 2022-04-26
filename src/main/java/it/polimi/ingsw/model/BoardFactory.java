package it.polimi.ingsw.model;

import it.polimi.ingsw.model.expert.ExpertBoard;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class BoardFactory {
    public static Board getBoard(List<String> playerList, boolean expert, Turn turn){
        return createBoard(playerList, turn, 1);
    }

    public static Board getBoard(List<String> playerList, Turn turn, int seed){
        return createBoard(playerList, turn, seed);
    }

    private static Board createBoard(List<String> playerList, Turn turn, int seed) {
        Board b;
        switch (playerList.size()){
            case 2 -> b = new Board(playerList.get(0),playerList.get(1), turn, seed);
            case 3 -> b = new Board(playerList.get(0),playerList.get(1),playerList.get(2), turn, seed);
            case 4 -> b = new Board(playerList.get(0),playerList.get(1),playerList.get(2),playerList.get(3), turn, seed);
            default -> throw new IllegalArgumentException("too many students");
        }
        return b;
    }

    private static ExpertBoard createExpertBoard(List<String> playerList, Turn turn, int seed) {
        ExpertBoard b;
        switch (playerList.size()){
            case 2 -> b = new ExpertBoard(playerList.get(0),playerList.get(1), turn, seed);
            case 3 -> b = new ExpertBoard(playerList.get(0),playerList.get(1),playerList.get(2), turn, seed);
            case 4 -> b = new ExpertBoard(playerList.get(0),playerList.get(1),playerList.get(2),playerList.get(3), turn, seed);
            default -> throw new IllegalArgumentException("too many students");
        }
        return b;
    }
}
