package it.polimi.ingsw.controller;

public enum GameType {
    NORMAL_2_PLAYER(2, false),
    NORMAL_3_PLAYER(3, false),
    NORMAL_4_PLAYER(4, false),
    EXPERT_2_PLAYER(2, true),
    EXPERT_3_PLAYER(3, true),
    EXPERT_4_PLAYER(4, true);

    private final int nPlayer;
    private final boolean expertMode;

    GameType(int nPlayer, boolean expertMode){
        this.nPlayer = nPlayer;
        this.expertMode = expertMode;
    }

    public static GameType getGameType(int nPlayer, boolean expertMode){
        for (GameType gameType : GameType.values()) {
            if((nPlayer == gameType.nPlayer) && (expertMode == gameType.expertMode))
                return gameType;
        }
        return null;
    }

    @Override
    public String toString() {
        return "[" +
                "nPlayer=" + nPlayer +
                ", expertMode=" + expertMode +
                ']';
    }
}
