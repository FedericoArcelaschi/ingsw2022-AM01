package it.polimi.ingsw.server.model.expertLogic.characters.student;

import it.polimi.ingsw.server.model.baseLogic.Bag;
import it.polimi.ingsw.server.model.baseLogic.StudentColor;
import it.polimi.ingsw.server.model.baseLogic.Team;
import it.polimi.ingsw.server.model.exceptions.*;
import it.polimi.ingsw.server.model.expertLogic.ExpertCastle;
import it.polimi.ingsw.server.model.expertLogic.character.applyEffect.ParametersForCharacter;
import it.polimi.ingsw.server.model.expertLogic.character.charTypes.StudentCharacter;
import it.polimi.ingsw.server.model.expertLogic.character.costants.CharacterExplanation;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.shuffle;
import static org.junit.jupiter.api.Assertions.*;

public class Jester { //7° character
    CharacterExplanation explaination = CharacterExplanation.JESTER;

    void JesterApplyEffect(int studentsToMove) {
        List<StudentColor> requestedStudents = new ArrayList<>();
        StudentCharacter jester = new StudentCharacter(7, new Bag(2));
        List<StudentColor> studentsOnJester = jester.getAvailableStudents();
        shuffle(studentsOnJester);
        requestedStudents.addAll(studentsOnJester.subList(0, studentsToMove));

        List<StudentColor> studentsInWaitingRoom
                = new ArrayList<>(
                        List.of(StudentColor.BLUE, StudentColor.GREEN, StudentColor.BLUE, StudentColor.BLUE, StudentColor.YELLOW, StudentColor.RED, StudentColor.PINK)
                );
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

        assertEquals(   studentsInWaitingRoom.size(),
                castle.getWaitingRoom().size(),
                "same number of students");

        List<StudentColor> waitingRoomForComparison
                = studentsInWaitingRoom.subList(studentsToMove, studentsInWaitingRoom.size());
        waitingRoomForComparison
                .addAll(studentsOnJester
                        .subList(0, studentsToMove));

        assertEquals(   waitingRoomForComparison.stream().sorted().toList(),
                        castle.getWaitingRoom().stream().sorted().toList(),
                "new waiting room  contains the untouched waiting room + the students moved from Jester ");
    }

    @Test
    void TestAllPossibilities() {
        for (int i = 1; i < 4; i++)
            JesterApplyEffect(i);
    }
}
