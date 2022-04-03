package it.polimi.ingsw.model.expert;


public enum ListCharacters { //TODO: complete THE CHARACTERS
    MONK(1),
    BANDIT(2),
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
    private int id;
    ListCharacters(int id){
        this.id = id;
    }
    protected String getExplaination(){ //TODO: finish explainations
        String explaination = null;
        switch(id){
            case 1: explaination = "Character Student: this card offers the opportunity" +
                    "to add a student to an island of your choice" +
                    "call method: -Pay MONK(Color)";
            case 2: explaination = "Character Influence: this card offers the opportunity to gain the influence for agiven color even if you have as many students in your castle as another player has.";
        }
        return explaination;
    }
    protected int getCost(){
        return id % 3;
    }

   /* protected static ListCharacters values(int id){
        this.id = id;
        return ListCharacters.id;
    }*/
}
