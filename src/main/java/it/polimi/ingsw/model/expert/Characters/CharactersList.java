package it.polimi.ingsw.model.expert.Characters;

public enum CharactersList { //TODO: complete characters' explaination
    MONK(1, """
            Monk: this character offers the opportunity to add
            a student to an island of your choice for 1 coin!
            call function: -Pay MONK: student, islandNumber
            -More MONK: returns the possible students that can be moved \s"""),
    FARMER(2, """
            Farmer: this character gives you the chance to have
            more influence than your competitor.
            Pay 2 or 3 coins to break-tie the number of students
            in your Castle and take the control over of the professors!
            call function: -Pay FARMER
            -More FARMER: \s"""),
    GUARD(3, """
            Guard: this character offers you the chance to conquer another
            island, besides the one where mother nature in on.
            call function -Pay GUARD: islandNumber
            -More GUARD: \s"""),
    MAILMAN(4, """
            Mailman: this character makes you move
            mother nature further by 2 steps.
            call function: -Pay MAILMAN
            -More MAILMAN: \s"""),
    WITCH(5, """
            Witch: this character can prevent anyone from conquering
            an island, the effect is valid through someone moves
            mother nature on that island.
            call function: -Pay WITCH
            -More WITCH: \s"""),
    CENTAUR(6,"""
            Centaur: this character gives you the chance to conquer an island
            calculating the influence with the students and not the towers
            call function: -Pay CENTAUR
            -More CENTAUR: \s"""),
    JESTER(7, """
            Jester: this character can chance the students in you waiting room.
            You can swap up to three students from your waiting room, with the
            one available in this card.
            call function: -Pay JESTER: in(c1 [c2 c3])out(c4 [c5 c6])
            -More JESTER: returns the available students to move \s"""),
    KNIGHT(8, """
            Knight: this character gives you 2 additional points of influence.
            call function: -Pay KNIGHT
            -More KNIGHT: \s"""),
    COOK(9, """
            Cook: this character offers you the opportunity to overcome your
            opponent in an island conquer. It makes a given students' Color
            be worthless in the influence count.
            call function: -Pay COOK: Color
            -More COOK: \s"""),
    STORYTELLER(10, """
            Story teller: this character lets you swap two students between
            your dining room and your waiting room.
            call function: -Pay STORYTELLER: inWR(student1 [student2]) inDR(student1 [student2])
            -More STORYTELLER: \s"""),
    QUEEN(11, """
            Queen: this character let's you add a student to your dining room.
            call function: -Pay QUEEN: student
            -More QUEEN: \s"""),
    TAXMAN(12, """
            Taxman: this character forces every player to remove 3 students
            of the chosen color. If a player has less students, he/she must
            remove all students of that color.
            call function: -Pay TAXMAN: Color
            -More TAXMAN: \s""");

    private final int id;
    private String explanation;

    /**
     * The cost is also dynamically added to the explanation
     */
    CharactersList(int id, String explanation) {
        this.id = id;
        this.explanation = explanation;
    }

    CharactersList(int id) {
        this.id = id;
    }

    static CharactersList getChar(int charId) {
        return CharactersList.values()[charId - 1];
    }

    String getExplanation() { //TODO: finish explainations
        return this.explanation;
    }

    int getCost() {
        return id % 3;
    }

}
