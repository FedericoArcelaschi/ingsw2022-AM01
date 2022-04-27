package it.polimi.ingsw.communication;

import it.polimi.ingsw.communication.modelData.BoardData;

import java.sql.Timestamp;

public record Response(ResponseType type, BoardData data, Timestamp time) {
    public Response(ResponseType type){
        this(type, null, new Timestamp(System.currentTimeMillis()));
    }
    public Response(ResponseType type, BoardData data){
        this(type, data, new Timestamp(System.currentTimeMillis()));
    }
    public Response(Response response){
        this(response.type(), response.data(), response.time());
    }
}
