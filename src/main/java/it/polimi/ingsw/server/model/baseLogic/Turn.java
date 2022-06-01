package it.polimi.ingsw.server.model.baseLogic;

import java.util.*;
import java.util.stream.Collectors;

public class Turn {
    private final List<String> sittingOrder;
    private List<String> actionOrder;
    private String currentPlayerTurn;
    private TurnPhase currentPhase;
    private Map<String, Integer> playedCards;
    private int turnPhaseCounter = 0;

    /**
     * For the first round the PlanningPhase is the Sitting Order
     * @param sittingOrder players in the ordered they joined the server
     */
    public Turn(List<String> sittingOrder) {
        this.sittingOrder = new ArrayList<>(sittingOrder);
        actionOrder
                = new ArrayList<>();
        playedCards
                = new HashMap<>();
        currentPlayerTurn = sittingOrder.get(0);
        currentPhase = TurnPhase.PLANNING;
    }

    /** Sets the current turn to the player besides him. Used in the planning phase of the turn.
     * @return playerTurn
     */
    private String nextTurnPlanning(){
        if(currentPhase == TurnPhase.PLANNING
                && turnPhaseCounter != sittingOrder.size()){
            turnPhaseCounter++;
            return currentPlayerTurn = next(sittingOrder,currentPlayerTurn);
        }
        return null;
    }

    /** Sets the current turn to the next player chose in the planning phase.
     * Returns also the first player for the planning phase.
     * Doesn't need tp be called for the first player. It's already on currentPlayerTurn
     * @return playerTurn
     */
    private String nextTurnAction(){
        int nextPlayerPosition = actionOrder.indexOf(currentPlayerTurn) + 1;
        return currentPlayerTurn = actionOrder.get(nextPlayerPosition);
    }

    /**
     * Sets the new turn order.
     * @param map to be sorted
     */
    private void setNewTurn(Map<String, Integer> map){
        Map<String, Integer> sortedMap =  map.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
        List<String> newOrder = new ArrayList<>(sortedMap.keySet());
        setActionOrder(newOrder);
        turnPhaseCounter = 0;
        currentPhase = TurnPhase.STUDENTS; //The next player is now ready to play.
    }

    /**
     * Support method for setNewTurn.
     */
    private void setActionOrder(List<String> newerTurns) throws IllegalArgumentException{
        if(newerTurns.containsAll(sittingOrder) && sittingOrder.containsAll(newerTurns))
            this.actionOrder = newerTurns;
        else
            throw new IllegalArgumentException();
        currentPlayerTurn = actionOrder.get(0);
    }

    /**
     * Changes the phase and the player according to the rules of the game.
     */
    public void changePhase(){
        if (currentPhase.equals(TurnPhase.PLANNING)) { //If we are in the planning phase
            if(turnPhaseCounter != sittingOrder.size()-1){ //If we still have to go through the planning phase
                currentPlayerTurn = nextTurnPlanning(); //we don't change phase, and we change the player that needs to play the card
            }else{ //If we're done all the way through the planning phase we can switch phase and set the new order
                setNewTurn(playedCards); //Method that sets the new order for the turn and switches turn player to the new one
            }
        } else { //if we are not in the planning phase...
            if (!currentPhase.equals(TurnPhase.CLOUD)) { //if the phase is NOT the cloud phase NOR the planning phase
                currentPhase = TurnPhase.values()[Arrays.asList(TurnPhase.values()).indexOf(currentPhase)+1]; //We can move on to the next phase as normal and the turn player stays the same
            } else { //If the current phase is the cloud phase
                if (!currentPlayerTurn.equals(actionOrder.get(actionOrder.size() - 1))){ //If there are other players that need to play
                    currentPlayerTurn = nextTurnAction(); //then we change the current player
                    currentPhase = TurnPhase.STUDENTS; //and set the phase to students
                } else { //If we're done throughout the turn
                    currentPhase = TurnPhase.PLANNING; //then we go back to planning
                    currentPlayerTurn = actionOrder.get(0); //and set the right player as the first that has to play the card.
                }
            }
        }
    }


    public boolean addCard(String player, int c){
        playedCards.put(player, c);
        return true;
    }

    /**
     * @return the turn player
     */
    public String getCurrentPlayer(){
        return currentPlayerTurn;
    }

    public TurnPhase getCurrentPhase() {
        return currentPhase;
    }

    public List<String> getActionOrder() {
        return actionOrder;
    }

    public List<String> getSittingOrder() {
        return sittingOrder;
    }

    public int getTurnPhaseCounter() {
        return turnPhaseCounter;
    }

    private String next(List<String> list, String element){
        int dim = list.size();
        int index = list.indexOf(element);
        if(index == dim -1)
            return list.get(0);
        else return list.get(index +1);
    }

    //TODO: DETERMINE ORDER OF ACTIONS
}
