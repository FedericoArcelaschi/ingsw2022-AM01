package it.polimi.ingsw.communication;

import it.polimi.ingsw.model.Board;

public record Response(String message, Board board) {}
