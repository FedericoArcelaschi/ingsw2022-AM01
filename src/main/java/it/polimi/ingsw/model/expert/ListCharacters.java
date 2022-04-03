package it.polimi.ingsw.model.expert;


public enum ListCharacters { //TODO: complete THE CHARACTERS
    MONK(1),            //Student //adds a student to an island
    BANDIT(2),          //Influence //Sposta l'influenza anche in caso di pareggio
    AMBASSADOR(3),
    MAGICIAN(4),
    ERBORISTA(5),
    CENTAUR(6),
    JESTER(7),
    KNIGHT(8),
    VASSAL(9),
    SINGER(10),
    MADAME(11),
    MERCHANT(12);

    private final int id;

    ListCharacters(int id){
        this.id = id;
    }
}
