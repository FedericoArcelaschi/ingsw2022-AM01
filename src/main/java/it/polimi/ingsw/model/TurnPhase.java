package it.polimi.ingsw.model;

public enum TurnPhase {
    PLANNING(0), //Phase in which players play cards
    STUDENTS(1), //Phase in which players move the students
    MOTHERNATURE(2), //Phase in which players move Mother Nature
    CLOUD(3); //Phase in which players choose the cloud.

    private int phase;

    TurnPhase(int phase){
        this.phase = phase;
    }
    //Aside from the planning phase, players can play character cards at any time during their turn.
}
