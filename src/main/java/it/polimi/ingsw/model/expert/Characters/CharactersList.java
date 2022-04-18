package it.polimi.ingsw.model.expert.Characters;

public enum CharactersList { //TODO: complete characters' explaination
    MONK(1,"Monk: this character offers the opportunity to add\n" +
            "a student to an island of your choice for 1 coin!\n" +
            "call function: -Pay MONK: colorToPut\n" +
            "-More MONK: returns the possible students that can be moved"),
    FARMER(2, "Farmer: this character gives you the chance to have\n" +
            "more influence than your competitor.\n" +
            "Pay 2 coin to break-tie the number of students" +
            "in your Castle and take the control of the professors!" +
            "call function: -Pay FARMER\n" +
            "-More FARMER: "),
    GUARD(3,"Guard: this character offers you the chance to conquer\n" +
            "another island, besides the one where mother nature\n" +
            "in on.\n" +
            "call function -Pay GUARD: islandNumber"),
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
    private String explanation;

    /**
     * The cost is also dynamically added to the explanation
     */
    CharactersList(int id, String explanation){
        this.id = id;
    }
    protected String getExplanation(){ //TODO: finish explainations
        return this.explanation;
    }
    protected int getCost(){
        return id % 3;
    }

}
