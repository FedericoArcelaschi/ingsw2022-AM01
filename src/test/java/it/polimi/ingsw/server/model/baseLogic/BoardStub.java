package it.polimi.ingsw.server.model.baseLogic;

import it.polimi.ingsw.server.model.exceptions.NoSuchStudentException;
import it.polimi.ingsw.server.model.exceptions.PhaseNotRightException;
import it.polimi.ingsw.server.model.exceptions.TooManyStudentsException;

import java.util.*;

public class BoardStub extends Board implements EndGame {

    private int maxStudentsToMove = 3;
    private int turnCounter = 0;
    private Map<String, List<Card>> availableCards = new HashMap<>();

    public BoardStub(String playerID1, String playerID2, Turn turn, long seed) {
        super(playerID1, playerID2, turn, seed);
        availableCards.put(playerID1, getCastle(playerID1).getDeck());
    }

    public BoardStub(String playerID1, String playerID2, String playerID3, Turn turn, long seed) {
        super(playerID1, playerID2, playerID3, turn, seed);
    }

    public BoardStub(String playerID1, String playerID2, String playerID3, String playerID4, Turn turn, long seed) {
        super(playerID1, playerID2, playerID3, playerID4, turn, seed);
    }

    /**
     * Constructor for ExpertBoard: doesn't generate the castles.
     *
     * @param turn
     * @param seed
     * @param nPlayer
     */
    protected BoardStub(Turn turn, long seed, int nPlayer) {
        super(turn, 1, nPlayer);
    }


    @Override
    public void getToEndGame(String player) {
        while (!isEndGame()) {
            planning();
            actions();
        }
    }

    public void planning() {
        for (String p : castleMap.keySet()) {
            playRandomCard(p);
            changePhase();
        }
    }

    public void actions() {
        for (String p : turn.getActionOrder()) {
            randomStudentsMoved(p);
            changePhase();
            randomMotherNature();
            changePhase();
            randomChooseCloud(p);
            changePhase();
            turnCounter++;
        }
    }

    public void playRandomCard(String player) {
        int chosenCard = new Random().nextInt(1, 11);
        if (getCastle(player).isCardAvailable(chosenCard)
                && !turn.getPlayedCards().containsValue(new Card(chosenCard)))
            try {
                playCard(player, chosenCard);
            } catch (PhaseNotRightException e) {
                throw new RuntimeException(e);
            }
        else
            playRandomCard(player);
    }

    /**
     * Used to add random students from the Waiting Room to the Dining Room.
     *
     * @param player
     * @return
     */
    public void randomStudentsMoved(String player) {
        Random rand = new Random();
        List<StudentColor> students = new ArrayList<>();
        List<StudentColor> waitingRoom = getCastle(player).getWaitingRoom();
        for (int i = 0; i < 3; i++) {
            StudentColor added = waitingRoom.get(rand.nextInt(waitingRoom.size()));
            students.add(added);
            waitingRoom.remove(added);
        }
        try {
            for (StudentColor s : students) {
                if (rand.nextBoolean())
                    moveStudentsToDiningRoom(player, List.of(s));
                else
                    moveStudentToIsland(player, rand.nextInt(islandList.size()), List.of(s));
            }
        } catch (NoSuchStudentException | TooManyStudentsException | PhaseNotRightException e) {
            throw new RuntimeException(e);
        }
    }

    public void randomMotherNature() {
        int steps;
        if (turn.getPossibleMovingSteps() == 1)
            steps = 1;
        else
            steps = new Random().nextInt(1, turn.getPossibleMovingSteps()+1);
        try {
            moveMotherNature(steps);
        } catch (PhaseNotRightException e) {
            throw new RuntimeException(e);
        }
    }

    public void randomChooseCloud(String player) {
        int cloud = new Random().nextInt(cloudList.size());
        if (cloudList.get(cloud).isAvailable()) {
            try {
                chooseCloud(player, cloud);
            } catch (TooManyStudentsException | PhaseNotRightException e) {
                throw new RuntimeException(e);
            }
        } else
            randomChooseCloud(player);
    }
}
