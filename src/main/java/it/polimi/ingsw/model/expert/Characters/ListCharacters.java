package it.polimi.ingsw.model.expert.Characters;


public enum ListCharacters { //TODO: complete THE CHARACTERS
    MONK(1,"Monk: this character offers the opportunity to add\n" +
                          "a student to an island of your choice for 1 coin!" +
                          "call function: -Pay MONK(Color)"),
    FARMER(2, ""),
    GUARD(3,""),
    MAILMAN(4,""),
    WITCH(5,""),
    CENTAUR(6,""),
    JESTER(7,""),
    KNIGHT(8,""),
    COOK(9,""),
    STORYTELLER(10,""),
    QUEEN(11,""),
    TAXMAN(12,"");

    private int id;
    private String explaination;

    ListCharacters(int id, String explaination){
        this.id = id;
    }
    protected String getExplaination(){ //TODO: finish explainations
        return this.explaination;
    }
    protected int getCost(){
        return id % 3;
    }

}
