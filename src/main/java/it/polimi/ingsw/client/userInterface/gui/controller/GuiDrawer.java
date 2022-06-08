package it.polimi.ingsw.client.userInterface.gui.controller;

import it.polimi.ingsw.communication.modelData.CastleData;
import it.polimi.ingsw.communication.modelData.CloudData;
import it.polimi.ingsw.communication.modelData.IslandData;
import it.polimi.ingsw.communication.modelData.expertMode.CharacterData;
import it.polimi.ingsw.server.model.baseLogic.StudentColor;
import it.polimi.ingsw.server.model.baseLogic.Team;
import it.polimi.ingsw.server.model.expertLogic.character.costants.CharacterExplanation;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class GuiDrawer {

    public void drawCastles(CastleData myCaslte, List<CastleData> otherCastle, BorderPane myCastlePane , FlowPane castleTabHBox){
        GuiDrawer guiDrawer = new GuiDrawer();
        int i;
        guiDrawer.drawCastle(myCaslte, myCastlePane);
        for (i = 0; i < otherCastle.size()+1; i++) {
            VBox vbox = (VBox) castleTabHBox.getChildren().get(i);
            BorderPane borderPane = (BorderPane) vbox.getChildren().get(1);
            guiDrawer.drawCastle( i == 0? myCaslte : otherCastle.get(i-1), borderPane);
        }
        while (i<castleTabHBox.getChildren().size()) {
            VBox vbox = (VBox) castleTabHBox.getChildren().get(i);
            castleTabHBox.getChildren().remove(vbox);
        }
    }

    public void drawCards(List<String> assistants, FlowPane cardsFlowPane, EventHandler<MouseEvent> playCard){
        for (int i=1; i<=10; ++i) {
            String s = "[" + i + ", " + (i+1)/2 + "]";
            if(assistants.contains(s)) {
                Pane pane = new Pane();
                pane.setPrefSize(111, 200);
                pane.setOnMouseClicked(playCard);
                pane.setAccessibleText("card"+i);
                pane.getStyleClass().addAll("cardAssistant" + (i), "assistant");
                cardsFlowPane.getChildren().add(pane);
            }
        }
    }

    public void drawCharacters(List<CharacterData> characters, FlowPane characterPane, Tab characterTab){
        if(characters.size() == 0)
            return;
        characterTab.setDisable(false);
        for (int i = 0; i < characters.size(); i++) {
            Pane pane = (Pane) characterPane.getChildren().get(i);
            pane.getStyleClass().add(CharacterExplanation.getInstance(characters.get(i).getName()).getCSS());
        }
    }

    public void drawIslands(List<IslandData> islandList, int motherNaturePosition, HBox islandRow1, HBox islandRow2) {
        List<Pane> paneList = new ArrayList<>();
        for (Node n : islandRow1.getChildren()) {
            paneList.add((Pane) n);
        }
        for (Node n : islandRow2.getChildren()) {
            paneList.add((Pane) n);
        }

        for (int i = 0; i < islandList.size(); i++) {
            IslandData islandData = islandList.get(i);
            Pane pane = paneList.get(i);
            //Adding students
            for (StudentColor student : islandData.students().keySet()) {
                for (int j = 0; j < islandData.students().get(student); j++) {
                    ToggleButton toggleButton = new ToggleButton();
                    toggleButton.getStyleClass().add("student");
                    toggleButton.setDisable(true);
                    toggleButton.setPrefSize(25, 25);
                    setStudentButtonColor(toggleButton, student);
                    FlowPane flowPane = (FlowPane) pane.getChildren().get(0);
                    flowPane.getChildren().add(toggleButton);
                }
            }
            //adding mother nature
            if (motherNaturePosition == i) {
                ToggleButton toggleButton = new ToggleButton();
                toggleButton.getStyleClass().add("motherNature");
                toggleButton.setDisable(true);
                toggleButton.setPrefSize(35, 35);
                FlowPane flowPane = (FlowPane) pane.getChildren().get(0);
                flowPane.getChildren().add(toggleButton);
            }
            //Adding towers
            for (int j = 0; j < islandData.getIslandSize() && islandData.getOwnership() != null; j++) {
                ToggleButton toggleButton = new ToggleButton();
                toggleButton.getStyleClass().add("tower");
                toggleButton.setDisable(true);
                toggleButton.setPrefSize(35, 35);
                setTowerButtonColor(toggleButton, islandData.getOwnership());
                FlowPane flowPane = (FlowPane) pane.getChildren().get(0);
                flowPane.getChildren().add(toggleButton);
            }
        }
    }

    public void drawClouds(List<CloudData> cloudList, StackPane cloudStackPane){
        FlowPane cloudFlowPane = (FlowPane) cloudStackPane.getChildren().get(cloudList.size()-2);
        cloudFlowPane.setVisible(true);
        for (int i=0; i<cloudList.size(); ++i) {
            CloudData cloud = cloudList.get(i);
            Pane pane = (Pane) cloudFlowPane.getChildren().get(i);
            for (int j = 0; j < cloud.studentList().size(); j++)  {
                setStudentButtonColor((ToggleButton)pane.getChildren().get(j) , cloud.studentList().get(j));
            }
        }
    }

    private void drawCastle(CastleData castleData, BorderPane castle) {
        Pane waitingRoomPane = (Pane) castle.getBottom();
        Pane towerPane = (Pane) castle.getTop();
        Pane teacherTabelPane = (Pane) ((VBox) castle.getCenter()).getChildren().get(0);
        Pane diningRoomPane = (Pane) ((VBox) castle.getCenter()).getChildren().get(1);
        ToggleButton coinButton = (ToggleButton) waitingRoomPane.getChildren().get(9);

        drawWaitingRoom(castleData.waitingRoom(),waitingRoomPane);
        drawDiningRoom(castleData.diningRoom(), diningRoomPane);
        drawTeachers(castleData.teachers(), castleData.towerColor(), teacherTabelPane);
        drawTower(castleData.towerColor(), castleData.nTower(), towerPane);
        drawCoin(castleData.coins(), coinButton);
    }

    private void drawWaitingRoom(List<StudentColor> waitingRoom, Pane waitingRoomPane) {
        for (int i = 0; i < waitingRoom.size()-1; i++) {
            setStudentButtonColor((ToggleButton) waitingRoomPane.getChildren().get(i), waitingRoom.get(i));
        }
    }

    private void drawDiningRoom(EnumMap<StudentColor, Integer> studentColorIntegerMap, Pane waitingRoomPane) {
        for (StudentColor studentColor : studentColorIntegerMap.keySet()) {
            Pane pane = (Pane) waitingRoomPane.getChildren().get(studentColor.ordinal());
            for (int i = 0; i < studentColorIntegerMap.get(studentColor); i++) {
                ToggleButton toggleButton = (ToggleButton) pane.getChildren().get(i);
                setStudentButtonColor(toggleButton, studentColor);
            }
        }
    }

    private void drawTeachers(Map<StudentColor, Team> teachers, Team team, Pane teacherTablePane){
        for (int i=0; i<teacherTablePane.getChildren().size(); i++){
            Node node = teacherTablePane.getChildren().get(i);
            ToggleButton teacher = (ToggleButton) node;
            StudentColor teacherColor = StudentColor.values()[i];
            if(teachers.get(teacherColor) == team) setTeacherButtonColor(teacher, teacherColor);
        }
    }

    private void drawTower(Team towerColor, int usedTowers, Pane towerPane){
        for (int i = usedTowers; i < towerPane.getChildren().size(); i++) {
            Node node = towerPane.getChildren().get(i);
            ToggleButton toggleButton = (ToggleButton) node;
            setTowerButtonColor(toggleButton, towerColor);
        }
    }

    //FIXME
    private void drawCoin(int nCoin, ToggleButton coinButton){
        if(nCoin > 0) {
            coinButton.getStyleClass().add("coinBackground");
            coinButton.setText(String.valueOf(nCoin));
        }
    }

    private void setStudentButtonColor(ToggleButton button, StudentColor studentColor) {
        button.getStyleClass().add(studentColor.getStudentCSS());
        button.setAccessibleText(studentColor.name());
    }

    private void setTeacherButtonColor(ToggleButton button, StudentColor studentColor) {
        button.getStyleClass().add(studentColor.getTeacherCSS());
    }
    private void setTowerButtonColor(ToggleButton button, Team team) {
        button.getStyleClass().add(team.getCSS());
    }
}
