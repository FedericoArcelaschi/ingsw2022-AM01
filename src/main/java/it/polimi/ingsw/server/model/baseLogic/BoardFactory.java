package it.polimi.ingsw.server.model.baseLogic;

import it.polimi.ingsw.server.model.expertLogic.ExpertBoard;

import java.util.List;
import java.util.random.RandomGenerator;

public class BoardFactory {
    @Deprecated
    public static Board getBoard(List<String> playerList, Turn turn){
        return getBoard(playerList, false, turn, RandomGenerator.getDefault().nextLong());
    }

    public static Board getBoard(List<String> playerList, boolean expert, Turn turn){
        if(expert)
            return createExpertBoard(playerList, turn, RandomGenerator.getDefault().nextLong());
        return createBoard(playerList, turn, RandomGenerator.getDefault().nextLong());
    }

    public static Board getBoard(List<String> playerList, boolean expert, Turn turn, long seed){
        if(expert)
            return createExpertBoard(playerList, turn, seed);
        return createBoard(playerList, turn, seed);
    }

    private static ExpertBoard createExpertBoard(List<String> playerList, Turn turn, long seed) {
        return
            switch (playerList.size()){
                case 2 -> new ExpertBoard(playerList.get(0), playerList.get(1), turn, seed);
                case 3 -> new ExpertBoard(playerList.get(0), playerList.get(1),playerList.get(2), turn, seed);
                case 4 -> new ExpertBoard(playerList.get(0), playerList.get(1),playerList.get(2),playerList.get(3), turn, seed);
                default -> throw new IllegalArgumentException("too many students");
            };
    }

    private static Board createBoard(List<String> playerList, Turn turn, long seed) {
        return
            switch (playerList.size()){
                case 2 -> new Board(playerList.get(0),playerList.get(1), turn, seed);
                case 3 -> new Board(playerList.get(0),playerList.get(1),playerList.get(2), turn, seed);
                case 4 -> new Board(playerList.get(0),playerList.get(1),playerList.get(2),playerList.get(3), turn, seed);
                default -> throw new IllegalArgumentException("too many students");
            };
    }
}
