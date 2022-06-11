package it.polimi.ingsw.server.model.baseLogic;

import org.jetbrains.annotations.Contract;

import java.util.*;
import java.util.stream.Collectors;

public class Turn {

    private final List<String> sittingOrder;
    private List<String> actionOrder;
    private String currentPlayer;
    private TurnPhase currentPhase;
    private final Map<String, Integer> playedCards;
    private final int N_PLAYERS;
    private final int FIRST_PLANNING_TURN = 1;
    private int planningCounter = FIRST_PLANNING_TURN;

    /**
     * For the first round the PlanningPhase is the Sitting Order
     * @param sittingOrder players in the ordered they joined the server
     */
    public Turn(List<String> sittingOrder) {
        this.sittingOrder = new ArrayList<>(sittingOrder);
        actionOrder = new ArrayList<>();
        playedCards = new HashMap<>();
        currentPlayer = sittingOrder.get(0);
        currentPhase = TurnPhase.PLANNING;
        N_PLAYERS = sittingOrder.size();
    }

    /** Sets the current turn to the player besides him. Used in the planning phase of the turn.
     * @return playerTurn
     * @requires planningCounter < numberOfPlayers && currentPhase == PLANNING
     */
    private String nextPlayerPlanning() {
        planningCounter++;
        return currentPlayer = next(sittingOrder, currentPlayer);
    }

    /**
     * Sets the new turn order.
     * @param map to be sorted
     */
    private void setNewRound(Map<String, Integer> map) {
        Map<String, Integer> sortedMap =  map.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
        List<String> newOrder = new ArrayList<>(sortedMap.keySet());
        setActionOrder(newOrder);
        currentPhase = TurnPhase.STUDENTS;      //The next player is now ready to play.
        playedCards.clear();
    }

    /**
     * Support method for setNewTurn.
     */
    private void setActionOrder(List<String> newerTurns) throws IllegalArgumentException {
        if(new HashSet<>(newerTurns).containsAll(sittingOrder) && new HashSet<>(sittingOrder).containsAll(newerTurns))
            this.actionOrder = newerTurns;
        else
            throw new IllegalArgumentException();
        currentPlayer = actionOrder.get(0);
    }

    /**
     * Changes the phase and the player according to the rules of the game.
     */
    public void changePhase() {
        switch (currentPhase) {
            case PLANNING -> {
                if (planningCounter < N_PLAYERS) {
                    currentPlayer = nextPlayerPlanning();   //we don't change phase, and we change the player that needs to play the card
                } else                                      //If we're done all the way through the planning phase we can switch phase and set the new order
                    setNewRound(playedCards);               //Method that sets the new order for the turn and switches turn player to the new one
            }
            default -> currentPhase = currentPhase.next();  //We can move on to the next phase as normal and the turn player stays the same
            case CLOUD -> {
                if (actionOrder.indexOf(currentPlayer) < N_PLAYERS - 1) { //If there are other players that need to play
                    currentPlayer = next(actionOrder, currentPlayer);           //then we change the current player
                    currentPhase = TurnPhase.STUDENTS;                          //and set the phase to students
                } else {                                    //If we're done throughout the turn
                    currentPlayer = actionOrder.get(0);     //then set the right player as the first that has to play the card.
                    currentPhase = TurnPhase.PLANNING;      //and we go back to planning
                    planningCounter = FIRST_PLANNING_TURN;
                }
            }
        }
    }


    public void addCard(String player, int card) {
        playedCards.put(player, card);
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

    @Contract(pure = true)
    public List<String> getSittingOrder() {
        return new ArrayList<>(sittingOrder);
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
    public boolean isAlreadyPlayed(int card) {
        return playedCards.containsValue(card);
    }

    @Contract(pure = true)
    public boolean isLastActionTurn() {
        return actionOrder.get(N_PLAYERS).equals(currentPlayer);
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
