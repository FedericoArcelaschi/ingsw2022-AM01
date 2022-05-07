package it.polimi.ingsw.model.expert.character.costants;

enum CharacterExplanation{
    MONK("""
            Monk: this character offers the opportunity to add
            a student to an island of your choice for 1 coin!
            call function: Pay MONK: student, islandNumber
            More MONK: returns the possible students that can be moved \s"""),
    FARMER( """
            Farmer: this character gives you the chance to have
            more influence than your competitor.
            Pay 2 or 3 coins to breaktie the number of students
            in your Castle and take the control over of the professors!
            call function: Pay FARMER
            More FARMER: \s"""),
    GUARD("""
            Guard: this character offers you the chance to conquer another
            island, besides the one where mother nature in on.
            call function Pay GUARD islandNumber
            More GUARD: \s"""),
    MAILMAN("""
            Mailman: this character makes you move
            mother nature further by 2 steps.
            call function: Pay MAILMAN
            More MAILMAN: \s"""),
    WITCH("""
            Witch: this character can prevent anyone from conquering
            an island, the effect is valid through someone moves
            mother nature on that island.
            call function: Pay WITCH islandNumber
            More WITCH: \s"""),
    CENTAUR("""
            Centaur: this character gives you the chance to conquer an island
            calculating the influence with the students and not the towers
            call function: Pay CENTAUR
            More CENTAUR: \s"""),
    JESTER("""
            Jester: this character can chance the students in you waiting room.
            You can swap up to three students from your waiting room, with the
            one available in this card.
            call function: Pay JESTER: in(c1 [c2 c3])out(c4 [c5 c6])
            More JESTER: returns the available students to move \s"""),
    KNIGHT("""
            Knight: this character gives you 2 additional points of influence.
            call function: Pay KNIGHT
            More KNIGHT: \s"""),
    COOK("""
            Cook: this character offers you the opportunity to overcome your
            opponent in an island conquer. It makes a given students' Color
            be worthless in the influence count.
            call function: Pay COOK: Color
            More COOK: \s"""),
    STORYTELLER("""
            Story teller: this character lets you swap two students between
            your dining room and your waiting room.
            call function: Pay STORYTELLER: inWR(student1 [student2]) inDR(student1 [student2])
            More STORYTELLER: \s"""),
    QUEEN("""
            Queen: this character let's you add a student to your dining room.
            call function: Pay QUEEN: student
            More QUEEN: \s"""),
    TAXMAN("""
            Taxman: this character forces every player to remove 3 students
            of the chosen color. If a player has less students, he/she must
            remove all students of that color.
            call function: Pay TAXMAN: Color
            More TAXMAN: \s""");


    private final String explanation;
    CharacterExplanation(String explanation) {
        this.explanation = explanation;
    }

    public String getDescription() {
        return explanation;
    }

    public static CharacterExplanation getInstance(int id) {
        return CharacterExplanation.values()[id-1];
    }
}
