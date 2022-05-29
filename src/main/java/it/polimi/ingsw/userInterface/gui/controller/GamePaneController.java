package it.polimi.ingsw.userInterface.gui.controller;

import it.polimi.ingsw.communication.modelData.BoardData;
import it.polimi.ingsw.communication.modelData.CastleData;
import it.polimi.ingsw.model.StudentColor;
import javafx.fxml.FXML;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GamePaneController {
    @FXML public Pane turnPane;
    @FXML public ToggleButton student1ToggleButton, student2ToggleButton, student3ToggleButton, student4ToggleButton, student5ToggleButton, student6ToggleButton, student7ToggleButton, student8ToggleButton, student9ToggleButton;
    @FXML public ToggleButton redStudent1DRPane, redStudent2DRPane, redStudent3DRPane, redStudent4DRPane, redStudent5DRPane, redStudent6DRPane, redStudent7DRPane, redStudent8DRPane, redStudent9DRPane, redStudent10DRPane;
    @FXML public ToggleButton pinkStudent1DRPane, pinkStudent2DRPane, pinkStudent3DRPane, pinkStudent4DRPane, pinkStudent5DRPane, pinkStudent6DRPane, pinkStudent7DRPane, pinkStudent8DRPane, pinkStudent9DRPane, pinkStudent10DRPane;
    @FXML public ToggleButton blueStudent1DRPane, blueStudent2DRPane, blueStudent3DRPane, blueStudent4DRPane, blueStudent5DRPane, blueStudent6DRPane, blueStudent7DRPane, blueStudent8DRPane, blueStudent9DRPane, blueStudent10DRPane;
    @FXML public ToggleButton greenStudent1DRPane, greenStudent2DRPane, greenStudent3DRPane, greenStudent4DRPane, greenStudent5DRPane, greenStudent6DRPane, greenStudent7DRPane, greenStudent8DRPane, greenStudent9DRPane, greenStudent10DRPane;
    @FXML public ToggleButton yellowStudent1DRPane, yellowStudent2DRPane, yellowStudent3DRPane, yellowStudent4DRPane, yellowStudent5DRPane, yellowStudent6DRPane, yellowStudent7DRPane, yellowStudent8DRPane, yellowStudent9DRPane, yellowStudent10DRPane;
    @FXML public Pane greenDRPane, redDRPane, blueDRPane, yellowDRPane, pinkDRPane;
    private ToggleGroup waitingRoomToggleGroup, greenDiningRoomToggleGroup, redDiningRoomToggleGroup, pinkDiningRoomToggleGroup, blueDiningRoomToggleGroup, yellowDiningRoomToggleGroup;

    private Map<StudentColor ,ToggleGroup> waitingRoomMap;
    public void initialize() {
        waitingRoomToggleGroup = new ToggleGroup();
        greenDiningRoomToggleGroup = new ToggleGroup();
        redDiningRoomToggleGroup = new ToggleGroup();
        pinkDiningRoomToggleGroup = new ToggleGroup();
        blueDiningRoomToggleGroup = new ToggleGroup();
        yellowDiningRoomToggleGroup = new ToggleGroup();
        waitingRoomMap = new HashMap<>();

        setToggleGroup(
                waitingRoomToggleGroup,
                student1ToggleButton,
                student2ToggleButton,
                student3ToggleButton,
                student4ToggleButton,
                student5ToggleButton,
                student6ToggleButton,
                student7ToggleButton,
                student8ToggleButton,
                student9ToggleButton
                );

        setToggleGroup(
                redDiningRoomToggleGroup,
                redStudent1DRPane,
                redStudent2DRPane,
                redStudent3DRPane,
                redStudent4DRPane,
                redStudent5DRPane,
                redStudent6DRPane,
                redStudent7DRPane,
                redStudent8DRPane,
                redStudent9DRPane,
                redStudent10DRPane
        );

        setToggleGroup(
                greenDiningRoomToggleGroup,
                greenStudent1DRPane,
                greenStudent2DRPane,
                greenStudent3DRPane,
                greenStudent4DRPane,
                greenStudent5DRPane,
                greenStudent6DRPane,
                greenStudent7DRPane,
                greenStudent8DRPane,
                greenStudent9DRPane,
                greenStudent10DRPane
        );

        setToggleGroup(
                yellowDiningRoomToggleGroup,
                yellowStudent1DRPane,
                yellowStudent2DRPane,
                yellowStudent3DRPane,
                yellowStudent4DRPane,
                yellowStudent5DRPane,
                yellowStudent6DRPane,
                yellowStudent7DRPane,
                yellowStudent8DRPane,
                yellowStudent9DRPane,
                yellowStudent10DRPane
        );

        setToggleGroup(
                pinkDiningRoomToggleGroup,
                pinkStudent1DRPane,
                pinkStudent2DRPane,
                pinkStudent3DRPane,
                pinkStudent4DRPane,
                pinkStudent5DRPane,
                pinkStudent6DRPane,
                pinkStudent7DRPane,
                pinkStudent8DRPane,
                pinkStudent9DRPane,
                pinkStudent10DRPane
        );

        setToggleGroup(
                blueDiningRoomToggleGroup,
                blueStudent1DRPane,
                blueStudent2DRPane,
                blueStudent3DRPane,
                blueStudent4DRPane,
                blueStudent5DRPane,
                blueStudent6DRPane,
                blueStudent7DRPane,
                blueStudent8DRPane,
                blueStudent9DRPane,
                blueStudent10DRPane
        );

        waitingRoomMap.put(StudentColor.GREEN, greenDiningRoomToggleGroup);
        waitingRoomMap.put(StudentColor.BLUE, blueDiningRoomToggleGroup);
        waitingRoomMap.put(StudentColor.RED, redDiningRoomToggleGroup);
        waitingRoomMap.put(StudentColor.PINK, pinkDiningRoomToggleGroup);
        waitingRoomMap.put(StudentColor.YELLOW, yellowDiningRoomToggleGroup);
    }

    private void setToggleGroup(ToggleGroup toggleGroup, ToggleButton... toggleButtons) {
        for (ToggleButton toggleButton: toggleButtons) {
            toggleButton.setToggleGroup(toggleGroup);
        }
    }

    public void draw(BoardData boardData) {
        drawCastle(boardData.myCastle());
    }
    
    private void drawCastle(CastleData castleData) {
        drawWaitingRoom(castleData.waitingRoom());
        drawDiningRoom(castleData.diningRoom());
    }

    private void drawWaitingRoom(List<StudentColor> waitingRoom) {
        for (int i = 0; i < waitingRoom.size(); i++) {
            setStudentButtonColor((ToggleButton) waitingRoomToggleGroup.getToggles().get(i), waitingRoom.get(i));
        }
    }

    private void drawDiningRoom(Map<StudentColor, Integer> studentColorIntegerMap) {
        for (StudentColor color: StudentColor.values()) {
            for (int i = 0; i < studentColorIntegerMap.get(color); i++) {
                setStudentButtonColor((ToggleButton) waitingRoomMap.get(color).getToggles().get(i), color);
            }
        }
    }

    private void setStudentButtonColor(ToggleButton button, StudentColor studentColor) {
        button.getStyleClass().add(studentColor.cssClass);
    }
}
