package it.polimi.ingsw.communication.modelData;

import it.polimi.ingsw.model.Board;

import java.util.List;
import java.util.Objects;

/**
 * A representation of the model containing only data useful for the clients
 */
public final class BoardData {
    private final String username;
    private final int nPlayer;
    private final int motherNaturePosition;
    private final List<CloudData> cloudList;
    private final List<IslandData> islandList;
    private final CastleData myCastle;
    private final List<CastleData> otherCastles;

    /**
     */
    public BoardData(
            String username,
            int nPlayer,
            int motherNaturePosition,
            List<CloudData> cloudList,
            List<IslandData> islandList,
            CastleData myCastle,
            List<CastleData> otherCastles
    ) {
        this.username = username;
        this.nPlayer = nPlayer;
        this.motherNaturePosition = motherNaturePosition;
        this.cloudList = cloudList;
        this.islandList = islandList;
        this.myCastle = myCastle;
        this.otherCastles = otherCastles;
    }

    public BoardData(String username, Board board) {
        this(username, board.getNPlayer(),
                board.getMotherNaturePosition(),
                board.getCloudList().stream().map(CloudData::new).toList(),
                board.getIslandList().stream().map(IslandData::new).toList(),
                new CastleData(username, board.getCastle(username)),
                board.getCastleMap().keySet().stream().filter(key -> !Objects.equals(key, username)).map(key -> new CastleData(key, board.getCastle(key))).toList()
        );
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
    public int hashCode() {
        return Objects.hash(username, nPlayer, motherNaturePosition, cloudList, islandList, myCastle, otherCastles);
    }

    @Override
    public String toString() {
        return "BoardData[" +
                "username=" + username + ", " +
                "nPlayer=" + nPlayer + ", " +
                "motherNaturePosition=" + motherNaturePosition + ", " +
                "cloudList=" + cloudList + ", " +
                "islandList=" + islandList + ", " +
                "myCastle=" + myCastle + ", " +
                "otherCastles=" + otherCastles + ']';
    }

}
