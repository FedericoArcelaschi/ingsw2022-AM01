package it.polimi.ingsw.communication;

import it.polimi.ingsw.communication.modelData.BoardData;

import java.sql.Timestamp;

public record Response(ResponseType type, String message, BoardData data, Timestamp time) {
    public Response(ResponseType type){
        this(type, null, null, new Timestamp(System.currentTimeMillis()));
    }
    public Response(ResponseType type, BoardData data){
        this(type, null, data, new Timestamp(System.currentTimeMillis()));
    }
    public Response(ResponseType type, String message){
        this(type, message, null, new Timestamp(System.currentTimeMillis()));
    }
    public Response(Response response){
        this(response.type(), response.message(), response.data(), response.time());
    }
}
