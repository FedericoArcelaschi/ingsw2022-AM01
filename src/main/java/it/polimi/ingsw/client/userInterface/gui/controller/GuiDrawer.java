package it.polimi.ingsw.client.userInterface.gui.controller;

import it.polimi.ingsw.client.userInterface.gui.graphicObjects.*;
import it.polimi.ingsw.communication.modelData.CastleData;
import it.polimi.ingsw.communication.modelData.CloudData;
import it.polimi.ingsw.communication.modelData.IslandData;
import it.polimi.ingsw.communication.modelData.TurnData;
import it.polimi.ingsw.communication.modelData.expertMode.CharacterData;
import it.polimi.ingsw.server.model.baseLogic.StudentColor;
import it.polimi.ingsw.server.model.baseLogic.Team;
import it.polimi.ingsw.server.model.expertLogic.character.costants.CharacterExplanation;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class GuiDrawer {

    public GraphicCastle drawCastles(CastleData myCastle, List<CastleData> otherCastle, Pane myCastlePane , FlowPane castleFlowPane, EventHandler<MouseEvent> moveStudentToCasle) {
        int i;
        GraphicCastle myGraphicCastle = new GraphicCastle(myCastle,false,otherCastle.size() == 2);
        myGraphicCastle.getDiningRoom().setOnMouseClicked(moveStudentToCasle);
        myCastlePane.getChildren().add(myGraphicCastle);

        castleFlowPane.getChildren().add(new OtherGraphicCastle(myCastle,otherCastle.size() == 2));

        for (i = 1; i < otherCastle.size()+1; i++) {
            CastleData castleData = otherCastle.get(i-1);
            castleFlowPane.getChildren().add(new OtherGraphicCastle(castleData,otherCastle.size() == 2));
        }
        return myGraphicCastle;
    }

    public void cleanCastles(Pane myCastlePane, FlowPane castleFlowPane) {
        cleanCastle(myCastlePane);
        for (int i = 0; i < castleFlowPane.getChildren().size(); i++) {
            VBox vbox = (VBox) castleFlowPane.getChildren().get(i);
            //Label nameLabel = (Label) ((Pane) vbox.getChildren().get(0)).getChildren().get(0);
            BorderPane borderPane = (BorderPane) vbox.getChildren().get(1);
            //nameLabel.setText("");
            cleanCastle(i == 0 ? myCastlePane : borderPane);
        }
    }

    public void drawCards(List<String> assistants, FlowPane cardsFlowPane, EventHandler<MouseEvent> playCard){
        for (int i=1; i<=10; ++i) {
            String s = "[" + i + ", " + (i+1)/2 + "]";
            if(assistants.contains(s)) {
                GraphicAssistant assistant = new GraphicAssistant(i);
                assistant.setOnMouseClicked(playCard);
                cardsFlowPane.getChildren().add(assistant);
            }
        }
    }

    public void cleanCards(FlowPane cardsFlowPane) {
        cardsFlowPane.getChildren().clear();
    }

    public void drawCharacters(List<CharacterData> characters, FlowPane charFlowPane, EventHandler<MouseEvent> payCharacter){
        for (CharacterData character : characters) {
            GraphicCharacter graphicCharacter = new GraphicCharacter(character.getName(), character.getStudents().orElse(new ArrayList<>()), character.getDescription());
            graphicCharacter.setOnMouseClicked(payCharacter);
            charFlowPane.getChildren().add(graphicCharacter);
        }
    }

    public void cleanCharacters(FlowPane charFlowPane) {
        charFlowPane.getChildren().clear();
    }

    public void drawIslands(List<IslandData> islandList, int motherNaturePosition, Pane left, Pane right, FlowPane topRow, FlowPane botRow, EventHandler<MouseEvent> onClick) {
        List<Pane> islandPaneList = new ArrayList<>();
        int i, j=0;
        for (i = 0; i < islandList.size(); i++) {
            IslandData islandData = islandList.get(i);
            islandPaneList.add(drawIsland(islandData, i, motherNaturePosition, onClick));
        }
        //add left
        FlowPane fp = (FlowPane) islandPaneList.get(j).getChildren().get(0);
        left.getChildren().add(islandPaneList.get(j++));
        //add in top row
        for (; j < (islandPaneList.size()+1)/2; j++)
            topRow.getChildren().add(islandPaneList.get(j));
        //add right
        right.getChildren().add(islandPaneList.get(j++));
        //add in bottom row
        for (; j < islandPaneList.size(); j++)
            botRow.getChildren().add(islandPaneList.get(j));
    }

    public void cleanIslands(Pane left, Pane right, FlowPane topRow, FlowPane botRow){
        left.getChildren().clear();
        right.getChildren().clear();
        topRow.getChildren().clear();
        botRow.getChildren().clear();
    }

    public void drawClouds(List<CloudData> cloudList, FlowPane cloudFlowPane, EventHandler<MouseEvent> cloudClick) {
        int i=0;
        for (CloudData cloud : cloudList) {
            GraphicCloud graphicCloud = new GraphicCloud(cloud.studentList(), i++);
            graphicCloud.setOnMouseClicked(cloudClick);
            cloudFlowPane.getChildren().add(graphicCloud);
        }
    }

    public void cleanClouds(FlowPane cloudFlowPane){
        cloudFlowPane.getChildren().clear();
    }

    public void drawTurn(TurnData turnData, Pane turnPane) {
        Label phaseLabel = (Label) turnPane.getChildren().get(1);
        TextFlow playerOrderLabel = (TextFlow) turnPane.getChildren().get(2);
        int i=0;

        StringBuilder phase;
        phase = new StringBuilder("Turn Phase:  ");
        playerOrderLabel.getChildren().add(new Text("Player Order:  "));

        phase.append(turnData.currentPhase());

        phaseLabel.setText(phase.toString());
        for (String player: turnData.actionOrder()) {
            Text text = new Text();
            if(i != 0){
               playerOrderLabel.getChildren().add(new Text(",  "));
            }
            i++;
            text.setText(player);
            if(player.equals(turnData.currentPlayer()))
                text.setStyle("-fx-font-size: 16px");
            playerOrderLabel.getChildren().add(text);
        }
    }

    public void cleanTurn(Pane turnPane) {
        Label phaseLabel = (Label) turnPane.getChildren().get(1);
        TextFlow playerOrderLabel = (TextFlow) turnPane.getChildren().get(2);

        phaseLabel.setText("");
        playerOrderLabel.getChildren().clear();
    }

    private Pane drawIsland(IslandData islandData, int index, int motherNaturePosition, EventHandler<MouseEvent> onClick){
        GraphicIsland island = new GraphicIsland(islandData.getStudents(), islandData.getIslandSize(), islandData.getOwnership(), index, motherNaturePosition == index);
        island.setOnMouseClicked(onClick);
        return island;
    }

    private void drawCastle(CastleData castleData, BorderPane castle, Label nameLabel, boolean disabled) {
        /*Pane waitingRoomPane = (Pane) castle.getBottom();
        Pane towerPane = (Pane) castle.getTop();
        Pane teacherTablePane = (Pane) ((VBox) castle.getCenter()).getChildren().get(0);
        Pane diningRoomPane = (Pane) ((VBox) castle.getCenter()).getChildren().get(1);
        ToggleButton coinButton = (ToggleButton) waitingRoomPane.getChildren().get(9);
        if(nameLabel != null)
            nameLabel.setText(castleData.username());

        drawWaitingRoom(castleData.waitingRoom(), waitingRoomPane, disabled);
        drawDiningRoom(castleData.diningRoom(), diningRoomPane);
        drawTeachers(castleData.teachers(), castleData.towerColor(), teacherTablePane);
        drawTower(castleData.towerColor(), castleData.nTower(), towerPane);
        drawCoin(castleData.coins(), coinButton);*/

    }

    private void cleanCastle(Pane castle) {
        castle.getChildren().clear();
    }

    private void drawWaitingRoom(List<StudentColor> waitingRoom, Pane waitingRoomPane, boolean disabled) {
        for (int i = 0; i < waitingRoomPane.getChildren().size(); i++) {
            ToggleButton toggleButton = (ToggleButton) waitingRoomPane.getChildren().get(i);
            if(i < waitingRoom.size()) {
                setStudentButtonColor(toggleButton, waitingRoom.get(i));
                toggleButton.setDisable(disabled);
            }
            else
                toggleButton.setDisable(true);
        }
    }

    private void cleanWaitingRoom(Pane waitingRoomPane) {
        for (int i = 0; i < waitingRoomPane.getChildren().size(); i++) {
            ToggleButton toggleButton = (ToggleButton) waitingRoomPane.getChildren().get(i);
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

    private void drawCoin(int nCoin, ToggleButton coinButton) {
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
