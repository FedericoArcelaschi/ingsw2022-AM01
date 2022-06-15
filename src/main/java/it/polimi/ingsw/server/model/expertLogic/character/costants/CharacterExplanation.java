package it.polimi.ingsw.server.model.expertLogic.character.costants;

public enum CharacterExplanation{
    MONK("""
            Monk: this character offers the opportunity to add
            a student from the list of characters on this card to an island of your choice.
            call function: paychar MONK: student islandNumber\s"""),
    FARMER( """
            Farmer: this character gives you the chance to have
            more influence than your competitor.
            Pay 2 or 3 coins to break tie the number of students
            in your Castle and take the control over of the professors!
            call function: paychar FARMER\s"""),
    GUARD("""
            Guard: this character offers you the chance to conquer another
            island, besides the one where mother nature in on.
            call function: paychar GUARD islandNumber\s"""),
    MAILMAN("""
            Mailman: this character allows you to optionally move
            mother nature further by up to 2 steps during the MOTHERNATURE phase.
            call function: paychar MAILMAN\s"""),
    WITCH("""
            Witch: this character can prevent anyone from conquering
            an island, the effect is valid through someone moves
            mother nature on that island.
            call function: paychar WITCH islandNumber\s"""),
    CENTAUR("""
            Centaur: this character gives you the chance to conquer an island
            ignoring the influence provided by towers.
            call function: paychar CENTAUR\s"""),
    JESTER("""
            Jester: this character can change the students in your waiting room.
            You can swap one, two or three students from your waiting room, with the
            ones available in this card.
            call function: paychar JESTER: in(c1 [c2 c3]) out(c4 [c5 c6])\s"""),
    KNIGHT("""
            Knight: this character gives you 2 additional points of influence.
            call function: paychar KNIGHT\s"""),
    COOK("""
            Cook: this character offers you the opportunity to overcome your
            opponent when conquering an island. It makes a given student Color
            be worthless in the influence count.
            call function: paychar COOK: Color\s"""),
    STORYTELLER("""
            Storyteller: this character lets you swap one or two students
            between your dining room and your waiting room.
            call function: paychar STORYTELLER: inWR(student1 [student2]) inDR(student1 [student2])\s"""),
    QUEEN("""
            Queen: this character lets you add a student to your dining room from the
            list of characters on this card.
            call function: paychar QUEEN: studentToMove\s"""),
    TAXMAN("""
            Taxman: this character forces every player to remove 3 students
            of the chosen studentColor. If a player has less students, he/she must
            remove all students of that studentColor.
            call function: paychar TAXMAN: Color\s""");

    private final String explanation;
    CharacterExplanation(String explanation) {
        this.explanation = explanation;
    }

    public String getDescription() {
        return explanation;
    }

    public String getCSS() {
        return "cardCharacter" + name().substring(0,1).toUpperCase() + name().substring(1).toLowerCase();
    }

    public static CharacterExplanation getInstance(int id) {
        return CharacterExplanation.values()[id-1];
    }
    public static CharacterExplanation getInstance(String name) {
        for (CharacterExplanation ce: CharacterExplanation.values()) {
            if(name.equals(ce.name()))
                return ce;
        }
        return null;
    }
}
