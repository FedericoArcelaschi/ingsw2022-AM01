package it.polimi.ingsw.startUp;

public enum Outputs {
    USER_TYPE_REQUEST(
    """
    
    What application do you want to launch?
    "server"    -> you start an Eriantys host. You'll need this in order to play a game with your friends on the local network.
    "t-client"  -> you start an Eriantys textual client. You'll be able to join a game and play it directly on your terminal.
    "g-client"  -> you start an Erintys graphical client. You'll be able to join a game and play it on the graphical interface.
    input: \s"""
    ),
    USER_TYPE_INVALID("""
    Not a valid application inputs.\s
    """),
    START("Welcome to Eriantys")
    ;

    public final String out;

    Outputs(String out) {
        this.out = out;
    }
}
