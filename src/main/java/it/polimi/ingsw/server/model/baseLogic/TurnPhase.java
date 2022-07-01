package it.polimi.ingsw.server.model.baseLogic;

import org.jetbrains.annotations.Nullable;

public enum TurnPhase {
    PLANNING,       //Phase in which players play cards
    STUDENTS,       //Phase in which players move the students
    MOTHERNATURE,   //Phase in which players move Mother Nature
    CLOUD;          //Phase in which players choose the cloud.
                    //Aside from the planning phase, players can play  expert character-cards at any time during their turn.

    public @Nullable TurnPhase next() {
        if(this != CLOUD)
            return TurnPhase.values()[this.ordinal() + 1];
        return null;
    }
}
