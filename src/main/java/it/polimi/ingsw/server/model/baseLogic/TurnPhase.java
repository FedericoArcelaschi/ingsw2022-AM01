package it.polimi.ingsw.server.model.baseLogic;

public enum TurnPhase {
    PLANNING(), //Phase in which players play cards
    STUDENTS(), //Phase in which players move the students
    MOTHERNATURE(), //Phase in which players move Mother Nature
    CLOUD(); //Phase in which players choose the cloud.

    TurnPhase(){
    }
    //Aside from the planning phase, players can play character cards at any time during their turn.
}
