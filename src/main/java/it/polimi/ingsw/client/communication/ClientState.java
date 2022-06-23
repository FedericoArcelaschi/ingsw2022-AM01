package it.polimi.ingsw.client.communication;

public enum ClientState {
    NOT_CONNECTED, //needs to insert the server's IP and Port to connect to the server
    OUTSIDE_LOBBY, //the client can see the LobbyInfo, but must put the preferences to join a game
    INSIDE_LOBBY, //the client is waiting for the LobbyInfo. Can quit to the outside of the Lobby and put new preferences
    IN_GAME, //the client can only interface with the game
    GAME_ENDED, //the client needs to decide whether to play another game or quit Eriantys
}
