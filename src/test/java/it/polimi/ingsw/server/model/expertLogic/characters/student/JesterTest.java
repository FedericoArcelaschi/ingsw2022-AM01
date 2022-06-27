package it.polimi.ingsw.server.model.expertLogic.characters.student;

import it.polimi.ingsw.server.model.baseLogic.*;
import it.polimi.ingsw.server.model.exceptions.*;
import it.polimi.ingsw.server.model.expertLogic.ExpertBoard;
import it.polimi.ingsw.server.model.expertLogic.ExpertCastle;
import it.polimi.ingsw.server.model.expertLogic.character.applyEffect.ParametersForCharacter;
import it.polimi.ingsw.server.model.expertLogic.character.specializedCharacters.charTypes.StudentCharacter;
import it.polimi.ingsw.server.model.expertLogic.character.costants.CharacterExplanation;
import it.polimi.ingsw.server.model.expertLogic.character.costants.CharacterUtility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.random.RandomGenerator;

import static java.util.Collections.shuffle;
import static org.junit.jupiter.api.Assertions.*;

public class JesterTest { //7° character

    private final CharacterExplanation explaination = CharacterExplanation.JESTER;
    private ExpertBoard expertBoard;
    private final String player1 = "pietro", player2 = "paolo";
    private final Turn turn = new Turn(List.of(player1, player2));

    @BeforeEach
    void setUp() {
        expertBoard = new ExpertBoard(player1, player2, turn, RandomGenerator.getDefault().nextLong());
        if(!expertBoard.getAvailableCharacters().containsKey(CharacterUtility.JESTER)) {
            setUp();
            return;
        }
        try {
            expertBoard.playCard(player1, 5);
            expertBoard.changePhase();
            expertBoard.playCard(player2, 8);
        } catch (PhaseNotRightException e) {
            throw new RuntimeException(e);
        }
        expertBoard.getTurn().addCard(player1, new Card(5));
        expertBoard.getTurn().addCard(player2, new Card(8));
        expertBoard.getTurn().changePhase();
        //here is in student phase
    }

    @Test
    void testJesterBoard() {
        var castle = expertBoard.getCastle(player1);
        var studentsOnJester = expertBoard.getAvailableCharacters().get(CharacterUtility.JESTER).getAvailableStudents();
        var studentsInWaitingRoom = castle.getWaitingRoom();
        var requestedStudents = new ArrayList<>(studentsOnJester.subList(0, 2));
        requestedStudents.addAll(studentsInWaitingRoom.subList(0, 2));
        try {
            expertBoard.playExpertCard(CharacterUtility.JESTER.getId(), 0,requestedStudents);
        } catch (StudentException | CoinException | PhaseNotRightException e) {
            throw new RuntimeException(e);
        }

        assertEquals(studentsInWaitingRoom.size(), castle.getWaitingRoom().size(),
                "there is the same number of students");

        var waitingRoomForComparison
                = studentsInWaitingRoom.subList(2, studentsInWaitingRoom.size());
        waitingRoomForComparison
                .addAll(studentsOnJester
                        .subList(0, 2));

        assertEquals(waitingRoomForComparison,
                castle.getWaitingRoom(),
                "new waiting room contains the untouched waiting room + the students moved from JesterTest ");

        assertThrowsExactly(IllegalStateException.class,
                ()-> expertBoard.playExpertCard(CharacterUtility.JESTER.getId(), 0, requestedStudents));


    }

    void JesterApplyEffect(int studentsToMove) {
        StudentCharacter jester
                = new StudentCharacter(7, new Bag(2, RandomGenerator.getDefault().nextLong()));
        List<StudentColor> studentsOnJester = jester.getAvailableStudents();
        shuffle(studentsOnJester);
        List<StudentColor> requestedStudents = new ArrayList<>(studentsOnJester.subList(0, studentsToMove));
        List<StudentColor> studentsInWaitingRoom
                = new ArrayList<>(List.of(StudentColor.BLUE, StudentColor.GREEN, StudentColor.BLUE, StudentColor.BLUE, StudentColor.YELLOW, StudentColor.RED, StudentColor.PINK));
        shuffle(studentsInWaitingRoom);
        requestedStudents.addAll(studentsInWaitingRoom.subList(0, studentsToMove));

        ParametersForCharacter par4C = new ParametersForCharacter();
        par4C.setRequestedStudentList(requestedStudents);

        ExpertCastle castle = new ExpertCastle(Team.WHITE, 2, studentsInWaitingRoom);
        par4C.setPlacesList(new ArrayList<>(List.of(castle)));

        assertEquals(studentsInWaitingRoom, castle.getWaitingRoom(),
                "initially the students are untouched.");

        try {
            jester.applyEffect(par4C);
        } catch (StudentException | IllegalAccessException e) {
            fail(e.getCause());
        }

        assertEquals(studentsInWaitingRoom.size(), castle.getWaitingRoom().size(),
                "there is the same number of students");

        List<StudentColor> waitingRoomForComparison
                = studentsInWaitingRoom.subList(studentsToMove, studentsInWaitingRoom.size());
        waitingRoomForComparison
                .addAll(studentsOnJester
                        .subList(0, studentsToMove));

        assertEquals(   waitingRoomForComparison,
                        castle.getWaitingRoom(),
                "new waiting room contains the untouched waiting room + the students moved from JesterTest ");
    }

    @Test
    void TestAllPossibilities() {
        for (int i = 1; i < 4; i++)
            JesterApplyEffect(i);
    }
}
