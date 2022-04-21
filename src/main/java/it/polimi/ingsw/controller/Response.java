package it.polimi.ingsw.controller;

public class Response {
    private String message;

    public Response(String message) {
        this.message = message;
    }

    public String toString(){
        return message;
    }
}
