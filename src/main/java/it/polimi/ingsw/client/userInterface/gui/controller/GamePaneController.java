package it.polimi.ingsw.client.userInterface.gui.controller;

import it.polimi.ingsw.communication.modelData.BoardData;
import it.polimi.ingsw.server.model.baseLogic.StudentColor;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.util.*;

public class GamePaneController {
    @FXML public BorderPane castlePane0;
    @FXML public HBox castleTabHBox;
    @FXML private Pane turnPane;
    @FXML private Pane greenDRPane, redDRPane, blueDRPane, yellowDRPane, pinkDRPane, waitingRoomPane, teacherTablePane, towerPane;
    @FXML private HBox islandRow1, islandRow2;
    @FXML private FlowPane cardsFlowPane;
    @FXML private StackPane cloudStackPane;
    private ToggleGroup waitingRoomToggleGroup;
    //private Map<StudentColor ,ToggleGroup> waitingRoomMap;
    public void initialize() {
        waitingRoomToggleGroup = new ToggleGroup();
        setToggleGroup(waitingRoomToggleGroup, waitingRoomPane.getChildren());
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
        guiDrawer.drawCards(boardData.myCastle().deck(), cardsFlowPane);
    }

    public void moveStudentToDiningRoom(MouseEvent mouseEvent) {
        ToggleButton selected = (ToggleButton) waitingRoomToggleGroup.getSelectedToggle();
        if(selected == null)
            return;
        StudentColor studentColor = StudentColor.getColor(selected.getAccessibleText());
        //TODO: Create and send command
    }
}
