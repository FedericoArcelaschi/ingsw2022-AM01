package it.polimi.ingsw.client.userInterface.gui.controller;

import it.polimi.ingsw.communication.command.Command;
import it.polimi.ingsw.communication.modelData.BoardData;
import it.polimi.ingsw.server.model.baseLogic.StudentColor;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.util.function.Consumer;

public class GamePaneController {
    @FXML public BorderPane castlePane0;
    @FXML public FlowPane castleTabHBox, characterPane, cardsFlowPane;
    @FXML private Pane waitingRoomPane;
    @FXML private StackPane cloudStackPane;
    @FXML private HBox islandRow1, islandRow2;
    @FXML private Tab characterTab;
    private ToggleGroup waitingRoomToggleGroup;
    private Consumer<Command> send;
    public void initialize(Consumer<Command> send) {
        waitingRoomToggleGroup = new ToggleGroup();
        setToggleGroup(waitingRoomToggleGroup, waitingRoomPane.getChildren());
        this.send = send;
    }

    private void setToggleGroup(ToggleGroup toggleGroup, ObservableList<Node> children) {
        for (Node node : children) {
            ToggleButton toggleButton = (ToggleButton) node;
            toggleButton.setToggleGroup(toggleGroup);
        }
    }

    public void draw(BoardData boardData) {
        GuiDrawer guiDrawer = new GuiDrawer();
        guiDrawer.drawCastles(boardData.myCastle(), boardData.otherCastles(), castlePane0, castleTabHBox);
        guiDrawer.drawClouds(boardData.cloudList(), cloudStackPane);
        guiDrawer.drawIslands(boardData.islandList(), boardData.motherNaturePosition(), islandRow1, islandRow2);
        guiDrawer.drawCards(boardData.myCastle().deck(), cardsFlowPane, this::playCard);
        guiDrawer.drawCharacters(boardData.characters(), characterPane, characterTab);
    }

    public void moveStudentToDiningRoom(MouseEvent mouseEvent) {
        ToggleButton selected = (ToggleButton) waitingRoomToggleGroup.getSelectedToggle();
        if(selected == null)
            return;
        StudentColor studentColor = StudentColor.getColor(selected.getAccessibleText());
        //TODO: Create and send command
    }

    public void moveStudentToIsland(MouseEvent mouseEvent) {
        ToggleButton selected = (ToggleButton) waitingRoomToggleGroup.getSelectedToggle();
        if(selected == null)
            return;
        StudentColor studentColor = StudentColor.getColor(selected.getAccessibleText());
        //TODO: Create and send command
    }


    public void chooseCloud(MouseEvent mouseEvent) {
        int cloudId;
        //take cloud id from accessibleText
        String accessibleText = ((Pane) mouseEvent.getTarget()).getAccessibleText();
        cloudId = Integer.parseInt(accessibleText.substring(accessibleText.length()-1));
        //TODO: Create and send command
    }

    public void playCard(MouseEvent mouseEvent) {
        int cardId;
        //take card id from accessibleText
        String accessibleText = ((Pane) mouseEvent.getTarget()).getAccessibleText();
        cardId = Integer.parseInt(accessibleText.substring(accessibleText.length()-1));
        //if cardId is 0 replace it with 10
        cardId = cardId == 0 ? 10 : cardId;
        //TODO: Create and send command
    }
}
