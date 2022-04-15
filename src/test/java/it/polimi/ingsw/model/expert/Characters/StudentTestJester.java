package it.polimi.ingsw.model.expert.Characters;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.exceptions.NoSuchStudentException;
import it.polimi.ingsw.model.exceptions.TooManyStudentsException;
import it.polimi.ingsw.model.expert.ExpertBoard;
import it.polimi.ingsw.model.expert.ExpertCastle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class StudentTestJester {
    private ExpertBoard board;
    private Generic jester;
    @BeforeEach
    void setUp() {
        Turn t = new Turn(List.of("a", "b"));
        board = new ExpertBoard("a", "b", t);
        board.setup4CharacterTesting(7);
        jester = board.getAvailableCharacterCards().get(7);
    }

    @Test
    void applyEffect() throws NoSuchStudentException, TooManyStudentsException {
        List<Color> availableStudents
            = ((List<Color>)jester
                    .getEffect()
                    .get(Parameters.STUDENTLIST));
        String currentPlayer
                = board.getTurn();
        ExpertCastle castle
                = (ExpertCastle) board.getCastleMap()
                .get(currentPlayer);
        List<Color> studentsInWaitingRoom =  castle.getWaitingRoom();
        List<Color> studentListForJester
                = new ArrayList<Color>(Arrays.asList(
                                        availableStudents.get(0),
                                        availableStudents.get(1),
                                        availableStudents.get(2),
                                        studentsInWaitingRoom.get(0),
                                        studentsInWaitingRoom.get(1),
                                        studentsInWaitingRoom.get(2)));
        board.playExpertCard(7, null, 0,studentListForJester);

        assertEquals(studentsInWaitingRoom.subList(3,7), castle.getWaitingRoom().subList(0,4),
                "wrong students remaining");
        assertEquals(availableStudents.subList(0,3), castle.getWaitingRoom().subList(4,7),
                "wrong students added");
    }
}