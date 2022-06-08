package it.polimi.ingsw.communication.modelData;

import com.google.gson.annotations.JsonAdapter;

import com.google.gson.annotations.JsonAdapter;
import it.polimi.ingsw.communication.modelData.expertMode.BoardDataAdapter;

import java.util.List;
import java.util.Objects;

/**
 * A representation of the model containing only data useful for the clients
 */
@JsonAdapter(BoardDataAdapter.class)
public class BoardData {
    protected final String username;
    protected final int nPlayer;
    protected final int motherNaturePosition;
    protected final List<CloudData> cloudList;
    protected final List<IslandData> islandList;
    protected final CastleData myCastle;
    protected final List<CastleData> otherCastles;
    protected final TurnData turn;

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

    public String username() {
        return username;
    }

    public int nPlayer() {
        return nPlayer;
    }

    public int motherNaturePosition() {
        return motherNaturePosition;
    }

    public List<CloudData> cloudList() {
        return cloudList;
    }

    public List<IslandData> islandList() {
        return islandList;
    }

    public CastleData myCastle() {
        return myCastle;
    }

    public List<CastleData> otherCastles() {
        return otherCastles;
    }

    public TurnData turn() {
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
            if (i == motherNaturePosition) s.append(", mother nature is Here!");
        }
        //Print cloud
        s.append("\nClouds: ");
        for (int i = 0; i < cloudList.size(); i++)
            s       .append("\n\tCloud ")
                    .append(i + 1)
                    .append(" contains: ")
                    .append(cloudList.get(i));
        //Print other castles
        s.append("\nOther Player castles:");
        for (CastleData otherCastle : otherCastles)
            s.append("\n\tCastle ").append(otherCastle.username()).append(": ").append(otherCastle);
        //Print turn
        s.append("\nTurn: ").append(turn);
        //Print my castle with the hand of cards
        s.append("\nMy Castle:");
        s.append("\n\tCastle ").append(username).append(": ").append(myCastle).append("\n");
        return s.toString();
    }

}
