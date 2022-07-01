package it.polimi.ingsw.server.model.baseLogic;

import it.polimi.ingsw.server.model.baseLogic.interfaces.IterableList;
import org.jetbrains.annotations.Contract;

import java.util.*;
import java.util.stream.Collectors;

public class Turn implements IterableList {

    /**
     * Ordered list of players as they joined the game.
     */
    private final List<String> sittingOrder;

    /**
     * Ordered list of players as of the current turn computed priority.
     */
    private List<String> actionOrder;

    private String currentPlayer;

    private TurnPhase currentPhase;

    /**
     * Current hands played card in planning-phase order.
     */
    private final Map<String, Card> playedCards = new HashMap<>();;

    private final int N_PLAYERS;

    private final int FIRST_PLANNING_TURN = 1;

    private int planningCounter = FIRST_PLANNING_TURN;

    private boolean skipCloudPhase;

    /**
     * For the first round the PlanningPhase is the Sitting Order
     * @param sittingOrder players in the ordered they joined the server
     */
    public Turn(List<String> sittingOrder) {
        this.sittingOrder = new ArrayList<>(sittingOrder);
        this.actionOrder = new ArrayList<>(sittingOrder);
        this.currentPhase = TurnPhase.PLANNING;
        this.currentPlayer = sittingOrder.get(0);
        this.N_PLAYERS = sittingOrder.size();
        skipCloudPhase = false;
    }

    /**
     * Changes the phase and the player according to the rules of the game.
     */
    public void changePhase() {
        switch (currentPhase) {
            case PLANNING -> {
                if (planningCounter < N_PLAYERS ) {
                    currentPlayer = next(sittingOrder, currentPlayer);
                    planningCounter++;
                } else {
                    setNewRound();
                    currentPlayer = actionOrder.get(0);
                    currentPhase = TurnPhase.STUDENTS;
                }
            }
            default -> {
                if(skipCloudPhase && currentPhase == TurnPhase.MOTHERNATURE)
                    nextTurn();
                else
                    currentPhase = currentPhase.next();
            }
            case CLOUD -> {
                if (!isLastActionTurn())                                    //If there are other players that need to play
                    nextTurn();                                             //and set the phase to students
                else {                                                      //If we're done throughout the turn
                    currentPhase = TurnPhase.PLANNING;
                    currentPlayer = actionOrder.get(0);
                    playedCards.clear();
                    planningCounter = FIRST_PLANNING_TURN;
                }
            }
        }
    }

    public void addCard(String player, Card card) {
        playedCards.put(player, card);
    }

    /**
     * Sets the new turn order.
     */
    void setNewRound() {
        Map<String, Integer> priorityMap = new HashMap<>();
        playedCards.forEach((key, value) -> priorityMap.put(key, value.priority()));
        Map<String, Integer> sortedMap =
                priorityMap.entrySet().stream()
                        .sorted(Map.Entry.comparingByValue())
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
        actionOrder = new ArrayList<>(sortedMap.keySet());
    }



    @Contract(pure = true)
    public boolean isLastActionTurn() {
        return actionOrder.get(N_PLAYERS - 1).equals(currentPlayer);
    }

    @Contract(pure = true)
    public boolean isAlreadyPlayed(int card) {
        return playedCards.containsValue(new Card(card));
    }

    public int getPossibleMovingSteps() {
        return playedCards.get(currentPlayer).distance();
    }

    @Contract(pure = true)
    public String getCurrentPlayer() {
        return currentPlayer;
    }

    @Contract(pure = true)
    public TurnPhase getCurrentPhase() {
        return currentPhase;
    }

    @Contract(pure = true)
    public List<String> getTurnOrder() {
        List<String> newPlanningOrder;
        int indexFirstPlayer = sittingOrder.indexOf(currentPlayer);
        newPlanningOrder = new ArrayList<>(sittingOrder.subList(indexFirstPlayer, sittingOrder.size()));
        newPlanningOrder.addAll(sittingOrder.subList(0, indexFirstPlayer));
        return currentPhase == TurnPhase.PLANNING ?
                new ArrayList<>(newPlanningOrder) :
                new ArrayList<>(actionOrder);
    }

    public boolean isSkipCloudPhase() {
        return skipCloudPhase;
    }

    public void setSkipCloudPhase(boolean skipCloudPhase) {
        this.skipCloudPhase = skipCloudPhase;
    }

    private void nextTurn() {
        currentPlayer = next(actionOrder, currentPlayer);
        currentPhase = TurnPhase.STUDENTS;
    }
    @Override
    public String toString() {
        return "Turn{" +
                ((sittingOrder != null) ? ("sittingOrder=" + sittingOrder) : "") +
                ((actionOrder != null) ? ("actionOrder=" + actionOrder) : "") +
                ", currentPlayer='" + currentPlayer + '\'' +
                ", currentPhase=" + currentPhase +
                ", playedCards=" + playedCards +
                "}\n";
    }
}
