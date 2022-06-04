package it.polimi.ingsw.server.controller;

public enum GameType {
    NORMAL_2_PLAYER(2, false),
    NORMAL_3_PLAYER(3, false),
    NORMAL_4_PLAYER(4, false),
    EXPERT_2_PLAYER(2, true),
    EXPERT_3_PLAYER(3, true),
    EXPERT_4_PLAYER(4, true);

    public final int nPlayer;
    public final boolean expertMode;

    GameType(int nPlayer, boolean expertMode){
        this.nPlayer = nPlayer;
        this.expertMode = expertMode;
    }

    public static GameType getGameType(int nPlayer, boolean expertMode) throws IllegalAccessException {
        for (GameType gameType : GameType.values())
            if((nPlayer == gameType.nPlayer) && (expertMode == gameType.expertMode))
                return gameType;
        throw new IllegalArgumentException("not a valid game type");
    }

    @Override
    public String toString() {
        return nPlayer +" players, " + (expertMode ? "expert-mode" : "normal-mode");
    }
}
