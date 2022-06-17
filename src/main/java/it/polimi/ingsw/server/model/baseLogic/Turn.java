package it.polimi.ingsw.server.model.baseLogic;

import org.jetbrains.annotations.Contract;

import java.util.*;
import java.util.stream.Collectors;

public class Turn {

    /**
     * ordered list of players as they joined the game.
     */
    private final List<String> sittingOrder;
    /**
     * ordered list of players as of the current turn computed priority.
     */
    private List<String> actionOrder;
    private String currentPlayer;
    private TurnPhase currentPhase;
    /**
     * Current hands played card.
     */
    private final Map<String, Card> playedCards;
    private final int N_PLAYERS;
    private final int FIRST_PLANNING_TURN = 1;
    private int planningCounter = FIRST_PLANNING_TURN;

    /**
     * For the first round the PlanningPhase is the Sitting Order
     * @param sittingOrder players in the ordered they joined the server
     */
    public Turn(List<String> sittingOrder) {
        this.sittingOrder = new ArrayList<>(sittingOrder);
        this.actionOrder = new ArrayList<>(sittingOrder);
        this.playedCards = new HashMap<>();
        this.currentPlayer = sittingOrder.get(0);
        this.currentPhase = TurnPhase.PLANNING;
        this.N_PLAYERS = sittingOrder.size();
    }

    /**
     * Changes the phase and the player according to the rules of the game.
     */
    public void changePhase() {
        switch (currentPhase) {
            case PLANNING -> {
                if (planningCounter < N_PLAYERS ) {
                    currentPlayer = nextPlayerPlanning();   //we don't change phase, and we change the player that needs to play the card
                    planningCounter++;
                } else                                      //If we're done all the way through the planning phase we can switch phase and set the new order
                    setNewRound(playedCards);               //Method that sets the new order for the turn and switches turn player to the new one
            }
            default -> currentPhase = currentPhase.next();  //We can move on to the next phase as normal and the turn player stays the same
            case CLOUD -> {
                if (actionOrder.indexOf(currentPlayer) < N_PLAYERS - 1) {          //If there are other players that need to play
                    currentPlayer = next(actionOrder, currentPlayer);
                    currentPhase = TurnPhase.STUDENTS;                          //and set the phase to students
                } else {                                                        //If we're done throughout the turn
                    currentPlayer = next(actionOrder, currentPlayer);
                    currentPhase = TurnPhase.PLANNING;                          //and we go back to planning
                    planningCounter = FIRST_PLANNING_TURN;
                    playedCards.clear();
                }
            }
        }
    }

    public void addCard(String player, Card card) {
        playedCards.put(player, card);
    }

    /**
     * Sets the new turn order.
     *
     * @param playerCardMap to be sorted
     */
    void setNewRound(Map<String, Card> playerCardMap) {
//        FIXME: handle case of equal cards.
        Map<String, Integer> priorityMap = new HashMap<>();
        playerCardMap.forEach((key, value) -> priorityMap.put(key, value.priority()));
        Map<String, Integer> sortedMap =
                priorityMap.entrySet().stream()
                        .sorted(Map.Entry.comparingByValue())
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
        setActionOrder(new ArrayList<>(sortedMap.keySet()));
        currentPhase = TurnPhase.STUDENTS;
    }

    /**
     * Support method for setNewTurn.
     */
    private void setActionOrder(List<String> newerTurns) throws IllegalArgumentException {
        if(new HashSet<>(newerTurns).containsAll(sittingOrder) && new HashSet<>(sittingOrder).containsAll(newerTurns))
            this.actionOrder = newerTurns;
        else
            throw new IllegalArgumentException("Error in Turn .setActionOrder()");
        currentPlayer = actionOrder.get(0);
    }

    /** Sets the current turn to the player besides him. Used in the planning phase of the turn.
     * @return playerTurn
     * @requires planningCounter < numberOfPlayers && currentPhase == PLANNING
     */
    @Contract(pure = true)
    private String nextPlayerPlanning() {
        return currentPlayer = next(sittingOrder, currentPlayer);
    }

    @Contract(pure = true)
    private String next(List<String> list, String element) {
        int dim = list.size();
        int index = list.indexOf(element);
        if(index == dim - 1)
            return list.get(0);
        else return list.get(index +1);
    }

    @Contract(pure = true)
    public boolean isLastActionTurn() {
        return actionOrder.get(N_PLAYERS - 1).equals(currentPlayer);
    }

    @Contract(pure = true)
    public boolean isAlreadyPlayed(int card) {
        return playedCards.containsValue(new Card(card));
    }

    public IntegerBoxing getPossibleMovingSteps() {
        return new IntegerBoxing(playedCards.get(currentPlayer).distance());
    }

    @Contract(pure = true)
    public List<String> getSittingOrder() {
        return new ArrayList<>(sittingOrder);
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
    public List<String> getActionOrder() {
        return new ArrayList<>(actionOrder);
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
