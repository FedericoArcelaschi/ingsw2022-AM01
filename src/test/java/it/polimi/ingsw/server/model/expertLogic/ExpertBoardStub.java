package it.polimi.ingsw.server.model.expertLogic;

import it.polimi.ingsw.server.model.baseLogic.*;
import it.polimi.ingsw.server.model.exceptions.*;
import it.polimi.ingsw.server.model.expertLogic.character.specializedCharacters.charTypes.Tavern;
import it.polimi.ingsw.server.model.expertLogic.character.costants.CharacterUtility;

import java.util.List;
import java.util.random.RandomGenerator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class ExpertBoardStub extends ExpertBoard {
    List<StudentColor> studentColors = List.of(StudentColor.GREEN, StudentColor.GREEN, StudentColor.GREEN, StudentColor.GREEN, StudentColor.GREEN, StudentColor.GREEN, StudentColor.YELLOW);
    /**
     * Constructor with 6 greens in the waiting room to gain the 2 coins
     */
    public ExpertBoardStub(String playerID1, String playerID2, CharacterUtility characterToExtract) {
        super(playerID1, playerID2, new Turn(List.of(playerID1, playerID2)), RandomGenerator.getDefault().nextLong());
        construct();

        do {
            expertCharactersCards = new Tavern(new Bag(20)).extract();
        } while (!expertCharactersCards.containsKey(characterToExtract));

        castleMap.replace(playerID1, new ExpertCastle(Team.WHITE, 2, studentColors));
        castleMap.replace(playerID2, new ExpertCastle(Team.BLACK, 2, studentColors));
    }

    public void remove1Coin(String playerID) {
        try {
            ((ExpertCastle) castleMap.get(playerID)).payCharacter(1);
        } catch (CoinException e) {
            System.err.println(e.getMessage());
        }
    }

    public void add1Coin(String playerID) {
        for (StudentColor student : StudentColor.values()) {
            try {
                castleMap.get(playerID).addStudentsInDiningRoom(List.of(student, student, student));
                break;
            } catch (TooManyStudentsException repeat) {}
        }
    }

    public void playPlanningPhaseFirstPlayer1() {
        try {
            playCard(getCurrentPlayer(), 9);
            turn.addCard(getCurrentPlayer(), new Card(9));
            changePhase();
            playCard(getCurrentPlayer(), 10);
            turn.addCard(getCurrentPlayer(), new Card(10));
            turn.changePhase();
        } catch (PhaseNotRightException e) {
            throw new RuntimeException(e);
        }
        //here is in student phase - player 1
    }
    public void playPlanningPhaseFirstPlayer2() {
        try {
            playCard(getCurrentPlayer(), 10);
            turn.addCard(getCurrentPlayer(), new Card(10));
            changePhase();
            playCard(getCurrentPlayer(), 9);
            turn.addCard(getCurrentPlayer(), new Card(9));
            turn.changePhase();
        } catch (PhaseNotRightException e) {
            throw new RuntimeException(e);
        }
        //here is in student phase - player 2
    }

    /**
     * moves all the students to the dining room.
     * current player gains two coins.
     * Only for setUp.
     * @throws NoSuchStudentException if the waiting room doesn't
     * contain all the {@link ExpertBoardStub#studentColors}
     */
    public void easyMoveStudentsToDiningRoom() {
        try {
            moveStudentsToDiningRoom(getCurrentPlayer(), studentColors);
            //gains 2 coins because of the stub.
        } catch (PhaseNotRightException | TooManyStudentsException | NoSuchStudentException e) {
            throw new RuntimeException(e);
        }
        try {
            assertEquals(3, castleMap.get(getCurrentPlayer()).getCoins());
        } catch (WrongGameModeException e) {
            e.printStackTrace();
            fail();
        }
        try {
            castleMap.get(getCurrentPlayer()).addStudentsInWaitingRoom(studentColors);
        } catch (TooManyStudentsException e) {
            e.printStackTrace();
            fail();
        }
    }

    public void easyMoveStudentsToDiningRoom(String playerID) {
        try {
            moveStudentsToDiningRoom(playerID, studentColors);
            //gains 2 coins because of the stub.
        } catch (NoSuchStudentException | PhaseNotRightException | TooManyStudentsException e) {
            throw new RuntimeException(e);
        }
        try {
            assertEquals(3, castleMap.get(playerID).getCoins());
        } catch (WrongGameModeException e) {
            e.printStackTrace();
            fail();
        }
    }
}
