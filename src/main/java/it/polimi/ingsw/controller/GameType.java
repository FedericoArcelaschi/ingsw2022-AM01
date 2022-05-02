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
        switch (nPlayer) {
            case 2 -> {
                if (expertMode) return EXPERT_2_PLAYER;
                return NORMAL_2_PLAYER;
            }
            case 3 -> {
                if (expertMode) return EXPERT_3_PLAYER;
                return NORMAL_3_PLAYER;
            }
            case 4 -> {
                if (expertMode) return EXPERT_4_PLAYER;
                return NORMAL_4_PLAYER;
            }
        }
        return null;
    }

    public int getNPlayer() {
        return nPlayer;
    }

    public boolean isExpertMode() {
        return expertMode;
    }
}
