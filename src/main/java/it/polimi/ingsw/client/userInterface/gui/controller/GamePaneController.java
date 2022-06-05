package it.polimi.ingsw.client.userInterface.gui.controller;

import it.polimi.ingsw.communication.modelData.BoardData;
import it.polimi.ingsw.communication.modelData.CastleData;
import it.polimi.ingsw.server.model.baseLogic.StudentColor;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GamePaneController {
    @FXML private Pane turnPane;
    @FXML private Pane greenDRPane, redDRPane, blueDRPane, yellowDRPane, pinkDRPane, waitingRoomPane;
    @FXML private HBox cardsHBox, islandRow1, islandRow2, cloudRow;
    private ToggleGroup waitingRoomToggleGroup;
    private Map<StudentColor ,ToggleGroup> waitingRoomMap;
    public void initialize() {
        waitingRoomToggleGroup = new ToggleGroup();
        ToggleGroup greenDiningRoomToggleGroup = new ToggleGroup();
        ToggleGroup redDiningRoomToggleGroup = new ToggleGroup();
        ToggleGroup pinkDiningRoomToggleGroup = new ToggleGroup();
        ToggleGroup blueDiningRoomToggleGroup = new ToggleGroup();
        ToggleGroup yellowDiningRoomToggleGroup = new ToggleGroup();
        waitingRoomMap = new HashMap<>();

        setToggleGroup(waitingRoomToggleGroup, waitingRoomPane.getChildren());

        setToggleGroup(redDiningRoomToggleGroup, redDRPane.getChildren());
        setToggleGroup(greenDiningRoomToggleGroup, greenDRPane.getChildren());
        setToggleGroup(yellowDiningRoomToggleGroup, yellowDRPane.getChildren());
        setToggleGroup(pinkDiningRoomToggleGroup, pinkDRPane.getChildren());
        setToggleGroup(blueDiningRoomToggleGroup, blueDRPane.getChildren());

        waitingRoomMap.put(StudentColor.GREEN, greenDiningRoomToggleGroup);
        waitingRoomMap.put(StudentColor.BLUE, blueDiningRoomToggleGroup);
        waitingRoomMap.put(StudentColor.RED, redDiningRoomToggleGroup);
        waitingRoomMap.put(StudentColor.PINK, pinkDiningRoomToggleGroup);
        waitingRoomMap.put(StudentColor.YELLOW, yellowDiningRoomToggleGroup);

        for (int i=0; i<10; ++i) {
            Node node = cardsHBox.getChildren().get(i);
            Pane pane = (Pane) node;
            pane.getStyleClass().add("cardAssistant" + (i+1));
        }
    }

    private void setToggleGroup(ToggleGroup toggleGroup, ObservableList<Node> children) {
        for (Node node : children) {
            ToggleButton toggleButton = (ToggleButton) node;
            toggleButton.setToggleGroup(toggleGroup);
        }
    }

    public void draw(BoardData boardData) {
        drawCastle(boardData.myCastle());
    }
    
    private void drawCastle(CastleData castleData) {
        drawWaitingRoom(castleData.waitingRoom());
        //drawDiningRoom(castleData.getDiningRoom());
    }

    private void drawWaitingRoom(List<StudentColor> waitingRoom) {
        for (int i = 0; i < waitingRoom.size(); i++) {
            setStudentButtonColor((ToggleButton) waitingRoomToggleGroup.getToggles().get(i), waitingRoom.get(i));
        }
    }

    private void drawDiningRoom(Map<StudentColor, Integer> studentColorIntegerMap) {
        for (StudentColor color: StudentColor.values()) {
            for (int i = 0; i < 5; i++) {
                setStudentButtonColor((ToggleButton) waitingRoomMap.get(color).getToggles().get(i), color);
            }
        }
    }

    private void setStudentButtonColor(ToggleButton button, StudentColor studentColor) {
        button.getStyleClass().add(studentColor.getCSS());
    }

    public void moveStudentToDiningRoom(MouseEvent mouseEvent) {
        //TODO: create and send command of move student
    }
}
