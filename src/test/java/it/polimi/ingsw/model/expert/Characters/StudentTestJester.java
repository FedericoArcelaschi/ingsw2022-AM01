package it.polimi.ingsw.model.expert.Characters;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.exceptions.NoSuchStudentException;
import it.polimi.ingsw.model.exceptions.TooManyStudentsException;
import it.polimi.ingsw.model.expert.ExpertBoard;
import it.polimi.ingsw.model.expert.ExpertCastle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StudentTestJester {
    private ExpertBoard board;
    private Generic jester;
    @BeforeEach
    void setUp() {
        Turn t = new Turn(List.of("Lollo99", "FedericaPellegrini"));
        board = new ExpertBoard("Lollo99", "FedericaPellegrini", t);
        board.setup4CharacterTesting(7);
        jester = board.getAvailableCharacterCards().get(7);
    }

    @Test
    void applyEffectJESTER() throws NoSuchStudentException, TooManyStudentsException {
        List<Color> availableStudents
            = ((List<Color>)jester
                    .getEffect()
                    .get(Parameters.STUDENTLIST));
        String currentPlayer
                = board.getCurrentPlayer();
        ExpertCastle castle
                = (ExpertCastle) board.getCastleMap()
                .get(currentPlayer);
        List<Color> studentsInWaitingRoom =  castle.getWaitingRoom();
        List<Color> studentListForJester
                = new ArrayList<>(Arrays.asList(
                                        availableStudents.get(0),
                                        availableStudents.get(1),
                                        availableStudents.get(2),
                                        studentsInWaitingRoom.get(0),
                                        studentsInWaitingRoom.get(1),
                                        studentsInWaitingRoom.get(2)));
        board.playExpertCard(7, studentListForJester);

        assertEquals(studentsInWaitingRoom.subList(3,7), castle.getWaitingRoom().subList(0,4),
                "wrong students remaining");
        assertEquals(availableStudents.subList(0,3), castle.getWaitingRoom().subList(4,7),
                "wrong students added");
    }
}