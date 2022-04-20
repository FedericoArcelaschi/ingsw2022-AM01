package it.polimi.ingsw.model.expert.Characters;

import it.polimi.ingsw.model.Turn;
import it.polimi.ingsw.model.expert.ExpertBoard;
import org.junit.jupiter.api.BeforeEach;

import java.util.Arrays;

class ActionTestMailMan {
    private ExpertBoard board;
    private Generic mailmanChar;

    @BeforeEach
    void setUp() {
        Turn t = new Turn(Arrays.asList("LolloBenzo", "GiaCarte"));
        board = new ExpertBoard("LolloBenzo", "GiaCarte", t);
        mailmanChar = board.extract4CharacterTesting(4);
    }

    //TODO: mailmanTest
}