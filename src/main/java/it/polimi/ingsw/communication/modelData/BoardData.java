package it.polimi.ingsw.communication.modelData;

import java.util.List;
import java.util.Objects;

/**
 * A representation of the model containing only data useful for the clients
 */
public class BoardData {
    private final String username;
    private final int nPlayer;
    private final int motherNaturePosition;
    private final List<CloudData> cloudList;
    private final List<IslandData> islandList;
    private final CastleData myCastle;
    private final List<CastleData> otherCastles;
    private final TurnData turn;

    /**
     */
    public BoardData(String username, int nPlayer, int motherNaturePosition, List<CloudData> cloudList, List<IslandData> islandList, CastleData myCastle, List<CastleData> otherCastles, TurnData turn) {
        this.username = username;
        this.nPlayer = nPlayer;
        this.motherNaturePosition = motherNaturePosition;
        this.cloudList = cloudList;
        this.islandList = islandList;
        this.myCastle = myCastle;
        this.otherCastles = otherCastles;
        this.turn = turn;
    }

    public String getUsername() {
        return username;
    }

    public int getNPlayer() {
        return nPlayer;
    }

    public int getMotherNaturePosition() {
        return motherNaturePosition;
    }

    public List<CloudData> getCloudList() {
        return cloudList;
    }

    public List<IslandData> getIslandList() {
        return islandList;
    }

    public CastleData getMyCastle() {
        return myCastle;
    }

    public List<CastleData> getOtherCastles() {
        return otherCastles;
    }

    public TurnData getTurn() {
        return turn;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (BoardData) obj;
        return Objects.equals(this.username, that.username) &&
                this.nPlayer == that.nPlayer &&
                this.motherNaturePosition == that.motherNaturePosition &&
                Objects.equals(this.cloudList, that.cloudList) &&
                Objects.equals(this.islandList, that.islandList) &&
                Objects.equals(this.myCastle, that.myCastle) &&
                Objects.equals(this.otherCastles, that.otherCastles);
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        //print island
        s.append("Islands: ");
        for (int i = 0; i < islandList.size(); i++) {
            s.append("\n\tIsland ")
                    .append( i + 1 )
                    .append(": ")
                    .append(islandList.get(i));
            if(i == motherNaturePosition) s.append(", mother nature is Here!");
        }
        //Print cloud
        s.append("\nClouds:");
        for (int i = 0; i < cloudList.size(); i++)
            s       .append("\n\tCloud ")
                    .append(i + 1)
                    .append(" contains")
                    .append(": ")
                    .append(cloudList.get(i));
        //Print other castles
        s.append("\nOther Player castles:");
        for (CastleData otherCastle : otherCastles)
            s.append("\n\tCastle ").append(otherCastle.getUsername()).append(": ").append(otherCastle);
        //Print turn
        s.append("\nTurn: ").append(turn);
        //Print my castle with the hand of cards
        s.append("\nMy Castle:");
        s.append("\n\tCastle ").append(username).append(": ").append(myCastle);
        return s.toString();
    }

}
