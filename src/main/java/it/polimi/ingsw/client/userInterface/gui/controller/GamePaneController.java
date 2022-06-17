package it.polimi.ingsw.client.userInterface.gui.controller;

import it.polimi.ingsw.communication.command.Command;
import it.polimi.ingsw.communication.command.CommandType;
import it.polimi.ingsw.communication.modelData.BoardData;
import it.polimi.ingsw.server.model.baseLogic.StudentColor;
import it.polimi.ingsw.server.model.baseLogic.TurnPhase;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class GamePaneController {

    @FXML public BorderPane castlePane0;
    @FXML public FlowPane castleTabHBox, cardsFlowPane, charFlowPane;
    @FXML public Pane turnPane;
    public StackPane bottomStackPane;
    @FXML private Pane waitingRoomPane;
    @FXML private StackPane cloudStackPane;
    @FXML private FlowPane islandRow1, islandRow2;
    @FXML private Tab characterTab;
    @FXML private ToggleButton expertMode;
    private MultipleToggleGroup waitingRoomToggleGroup;
    private Consumer<Command> send;
    private String username;
    private BoardData boardData;
    private final List<String> parameters = new ArrayList<>();

    public void initialize(Consumer<Command> send) {
        this.send = send;
    }

    private void setToggleGroup(MultipleToggleGroup toggleGroup, ObservableList<Node> children) {
        for (Node node : children) {
            ToggleButton toggleButton = (ToggleButton) node;
            toggleGroup.add(toggleButton);
        }
    }

    public void draw(BoardData boardData) {
        this.username = boardData.username();
        this.boardData = boardData;

        waitingRoomToggleGroup = new MultipleToggleGroup(boardData.nPlayer() == 3 ? 4 : 3);
        setToggleGroup(waitingRoomToggleGroup, waitingRoomPane.getChildren());

        GuiDrawer guiDrawer = new GuiDrawer();
        guiDrawer.drawCastles(boardData.myCastle(), boardData.otherCastles(), castlePane0, castleTabHBox);
        guiDrawer.drawClouds(boardData.cloudList(), cloudStackPane);
        guiDrawer.drawIslands(boardData.islandList(), boardData.motherNaturePosition(), islandRow1, islandRow2, this::island);
        guiDrawer.drawCards(boardData.myCastle().deck(), cardsFlowPane, this::playCard);
        guiDrawer.drawCharacter(boardData.characters(), charFlowPane, this::payCharacter);
        guiDrawer.drawTurn(boardData.turn(), turnPane);
        switchCommandMode();
    }

    public void printError(String error){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(error);
        alert.show();
    }

    public void moveStudentToDiningRoom() throws ParseException {
        List<ToggleButton> selected = waitingRoomToggleGroup.getSelectedToggles();
        if (selected.size() == 0)
            return;
        List<String> parameters = new ArrayList<>();
        for (ToggleButton toggleButton: selected) {
            StudentColor studentColor = StudentColor.parseColor(toggleButton.getAccessibleText());
            parameters.add(studentColor.name());
        }
        Command command = new Command(username, CommandType.MOVE_STUDENT_TO_CASTLE, parameters);
        System.out.println(command);
        send.accept(command);
    }

    public void island(MouseEvent mouseEvent) {
        Pane island = (Pane) mouseEvent.getTarget();
        if(!expertMode.isSelected()){
            if (boardData.turn().currentPhase() == TurnPhase.STUDENTS) {
                List<ToggleButton> selected = waitingRoomToggleGroup.getSelectedToggles();
                if (selected.size() == 0)
                    return;
                List<String> parameters = new ArrayList<>();
                parameters.add(island.getAccessibleText());
                for (ToggleButton toggleButton : selected) {
                    StudentColor studentColor = null;
                    try {
                        studentColor = StudentColor.parseColor(toggleButton.getAccessibleText());
                    } catch (ParseException ignored) {}
                    parameters.add(studentColor.name());
                }
                Command command = null;
                try {
                    command = new Command(username, CommandType.MOVE_STUDENT_TO_ISLAND, parameters);
                } catch (ParseException ignored) {}
                System.out.println(command);
                send.accept(command);
            } else if (boardData.turn().currentPhase() == TurnPhase.MOTHERNATURE) {
                List<String> parameters = new ArrayList<>();
                parameters.add(island.getAccessibleText());
                Command command = null;
                try {
                    command = new Command(username, CommandType.MOVE_MOTHER_NATURE, parameters);
                } catch (ParseException ignored) {}
                System.out.println(command);
                send.accept(command);
            }
        }
        else if(!parameters.isEmpty()) {
            parameters.add(island.getAccessibleText());
            System.out.println(parameters);
            Command command = null;
            try {
                command = new Command(username, CommandType.PAY_CHARACTER, parameters);
            } catch (ParseException ignored) {}
            System.out.println(command);
        }
    }

    public void chooseCloud(MouseEvent mouseEvent) {
        int cloudId;
        //take cloud id from accessibleText
        String accessibleText = ((Pane) mouseEvent.getTarget()).getAccessibleText();
        cloudId = Integer.parseInt(accessibleText.substring(accessibleText.length()-1));
        List<String> parameters = new ArrayList<>();
        parameters.add(String.valueOf(cloudId));
        Command command = null;
        try {
            command = new Command(username, CommandType.CHOOSE_CLOUD, parameters);
        } catch (ParseException ignored) {
        }
        System.out.println(command);
        send.accept(command);
    }

    public void playCard(MouseEvent mouseEvent) {
        int cardId;
        //take card id from accessibleText
        String accessibleText = ((Pane) mouseEvent.getTarget()).getAccessibleText();
        cardId = Integer.parseInt(accessibleText.substring(accessibleText.length()-1));
        //if cardId is 0 replace it with 10
        cardId = cardId == 0 ? 10 : cardId;
        List<String> parameters = new ArrayList<>();
        parameters.add(String.valueOf(cardId));
        Command command = null;
        try {
            command = new Command(username, CommandType.PLAY_CARD, parameters);
        } catch (ParseException ignored) {}
        System.out.println(command);
        send.accept(command);
    }

    public void payCharacter(MouseEvent mouseEvent) {
        parameters.clear();
        CharacterPane pane = (CharacterPane) mouseEvent.getTarget();
        parameters.add(pane.getAccessibleText());
        parameters.addAll(pane.getMultipleToggleGroup().getSelectedToggles().stream().map(ToggleButton::getAccessibleText).toList());
        /*Command command = new Command(username, CommandType.PAY_CHARACTER, parameters);
        System.out.println(command);*/
    }

    public void switchCommandMode() {
        if(expertMode.isSelected()) {
            cardsFlowPane.setVisible(false);
            charFlowPane.setVisible(true);
        }
        else{
            cardsFlowPane.setVisible(true);
            charFlowPane.setVisible(false);
        }
    }
}
