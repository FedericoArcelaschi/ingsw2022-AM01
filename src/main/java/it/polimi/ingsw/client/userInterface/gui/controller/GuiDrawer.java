package it.polimi.ingsw.client.userInterface.gui.controller;

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
import javafx.scene.control.Tab;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import javax.swing.*;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Flow;

public class GuiDrawer {

    public void drawCastles(CastleData myCastle, List<CastleData> otherCastle, BorderPane myCastlePane , FlowPane castleTabHBox) {
        GuiDrawer guiDrawer = new GuiDrawer();
        int i;
        guiDrawer.drawCastle(myCastle, myCastlePane, null, false);
        for (i = 0; i < otherCastle.size()+1; i++) {
            VBox vbox = (VBox) castleTabHBox.getChildren().get(i);
            Label nameLabel = (Label) ((Pane) vbox.getChildren().get(0)).getChildren().get(0);
            BorderPane borderPane = (BorderPane) vbox.getChildren().get(1);
            guiDrawer.drawCastle( i == 0? myCastle : otherCastle.get(i-1), borderPane, nameLabel, true);
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

    public void drawCharacter(List<CharacterData> characters, FlowPane charFlowPane, EventHandler<MouseEvent> payCharacter){
        for (CharacterData character : characters) {
            CharacterPane pane = new CharacterPane();
            pane.setPrefSize(111, 200);
            pane.getStyleClass().addAll(List.of("character", CharacterExplanation.getInstance(character.getName()).getCSS())); //FIXME usa l'utility pls
            pane.setOnMouseClicked(payCharacter);

            FlowPane flowPane = new FlowPane();
            flowPane.setPrefSize(111, 100);
            flowPane.setHgap(5);
            flowPane.setAlignment(Pos.CENTER);
            flowPane.setLayoutY(50);
            MultipleToggleGroup toggleGroup = new MultipleToggleGroup(character.getStudents().orElse(new ArrayList<>()).size());
            pane.setMultipleToggleGroup(toggleGroup);

            for (StudentColor studentColor: character.getStudents().orElse(new ArrayList<>())) {
                ToggleButton toggleButton = createElementToggleButton("student", 25, 25, false);
                toggleGroup.add(toggleButton);
                setStudentButtonColor(toggleButton, studentColor);
                flowPane.getChildren().add(toggleButton);
            }

            Tooltip tooltip = new Tooltip(character.getDescription());
            Tooltip.install(pane, tooltip);
            Tooltip.install(flowPane, tooltip);

            pane.setAccessibleText(character.getName());
            flowPane.setAccessibleText(character.getName());

            pane.getChildren().add(flowPane);
            charFlowPane.getChildren().add(pane);
        }
    }

    /*public void drawCharacters(List<CharacterData> characters,FlowPane charFlowPane, FlowPane characterPane, Tab characterTab){
        if(characters.size() == 0)
            return;
        characterTab.setDisable(false);
        for (int i = 0; i < characters.size(); i++) {
            Pane pane = (Pane) characterPane.getChildren().get(i);
            pane.getStyleClass().add(CharacterExplanation.getInstance(characters.get(i).getName()).getCSS());
            pane.setAccessibleText(characters.get(i).getName());
            Tooltip tooltip = new Tooltip(characters.get(i).getDescription());
            FlowPane flowPane = (FlowPane) pane.getChildren().get(0);
            Tooltip.install(pane, tooltip);
            Tooltip.install(flowPane, tooltip);
            flowPane.setAccessibleText(characters.get(i).getName());
            for (StudentColor studentColor: characters.get(i).getStudents().orElse(new ArrayList<>())) {
                ToggleButton toggleButton = new ToggleButton();
                toggleButton.getStyleClass().add("student");
                toggleButton.setPrefSize(25, 25);
                setStudentButtonColor(toggleButton, studentColor);
                flowPane.getChildren().add(toggleButton);
            }
        }
    }*/

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
            for (StudentColor color : islandData.students().keySet()) {
                for (int j = 0; j < islandData.students().get(color); j++) {
                    ToggleButton toggleButton = createElementToggleButton("student", 25, 25, true);
                    setStudentButtonColor(toggleButton, color);
                    FlowPane flowPane = (FlowPane) pane.getChildren().get(0);
                    flowPane.getChildren().add(toggleButton);
                }
            }
            //adding mother nature
            if (motherNaturePosition == i) {
                ToggleButton toggleButton = createElementToggleButton("motherNature", 35, 35, true);
                FlowPane flowPane = (FlowPane) pane.getChildren().get(0);
                flowPane.getChildren().add(toggleButton);
            }
            //Adding towers
            for (int j = 0; j < islandData.getIslandSize() && islandData.getOwnership() != null; j++) {
                ToggleButton toggleButton = createElementToggleButton("tower", 35, 50, true);
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

    public void drawTurn(TurnData turnData, Pane turnPane){
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

    private void drawCastle(CastleData castleData, BorderPane castle, Label nameLabel, boolean disabled) {
        Pane waitingRoomPane = (Pane) castle.getBottom();
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
        drawCoin(castleData.coins(), coinButton);
    }

    private void drawWaitingRoom(List<StudentColor> waitingRoom, Pane waitingRoomPane, boolean disabled) {
        for (int i = 0; i < waitingRoom.size(); i++) {
            ToggleButton toggleButton = (ToggleButton) waitingRoomPane.getChildren().get(i);
            setStudentButtonColor(toggleButton, waitingRoom.get(i));
            toggleButton.setDisable(disabled);
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

    private void drawCoin(int nCoin, ToggleButton coinButton){
        if(nCoin > 0) {
            coinButton.getStyleClass().add("coinBackground");
            coinButton.setText(String.valueOf(nCoin));
        }
    }

    public ToggleButton createElementToggleButton(String style, int width, int height, boolean disabled){
        ToggleButton toggleButton = new ToggleButton();
        toggleButton.getStyleClass().add(style);
        toggleButton.setDisable(disabled);
        toggleButton.setPrefSize(width, height);
        return toggleButton;
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
