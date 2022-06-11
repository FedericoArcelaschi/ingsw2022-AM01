package it.polimi.ingsw.communication.modelData;

import com.google.gson.annotations.JsonAdapter;

import it.polimi.ingsw.communication.modelData.expertMode.CharacterData;
import it.polimi.ingsw.server.model.expertLogic.character.costants.CharacterUtility;

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

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        //print island
        s.append("Islands: ");
        for (int i = 0; i < islandList.size(); i++) {
            s.append("\n\tIsland ")
                    .append(i + 1)
                    .append(": ")
                    .append(islandList.get(i));
            if (i == motherNaturePosition) s.append(", mother nature is Here!");
        }
        //Print cloud
        s.append("\n\nClouds: ");
        for (int i = 0; i < cloudList.size(); i++)
        s.append("\n\tCloud ")
                .append(i + 1)
                .append(" contains: ")
                .append(cloudList.get(i));
        //Print other castles
        s.append("\n\nOther Player castles:");
        for (CastleData otherCastle : otherCastles)
            s.append("\n\tCastle ").append(otherCastle.username()).append(": ").append(otherCastle);
        //Print turn
        s.append("\n\nTurn: ").append(turn);
        //Print my castle with the hand of cards
        s.append("\n\nMy Castle:");
        s.append("\n\tCastle ").append(username).append(": ").append(myCastle).append("\n");
        return s.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BoardData that)) return false;
        if (nPlayer != that.nPlayer) return false;
        if (motherNaturePosition != that.motherNaturePosition) return false;
        if (!username.equals(that.username)) return false;
        if (!cloudList.equals(that.cloudList)) return false;
        if (!islandList.equals(that.islandList)) return false;
        if (!myCastle.equals(that.myCastle)) return false;
        if (!otherCastles.equals(that.otherCastles)) return false;
        return turn.equals(that.turn);
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

    public List<CharacterData> characters() { return null;}

    @Override
    public int hashCode() {
        return Objects.hash(username, nPlayer, motherNaturePosition, cloudList, islandList, myCastle, otherCastles, turn);
    }


    public CharacterUtility activeChar() { return null;}
}
