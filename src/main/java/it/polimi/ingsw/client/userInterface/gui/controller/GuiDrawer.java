package it.polimi.ingsw.client.userInterface.gui.controller;

import it.polimi.ingsw.client.userInterface.gui.graphicObjects.*;
import it.polimi.ingsw.communication.modelData.CastleData;
import it.polimi.ingsw.communication.modelData.CloudData;
import it.polimi.ingsw.communication.modelData.IslandData;
import it.polimi.ingsw.communication.modelData.TurnData;
import it.polimi.ingsw.communication.modelData.expertMode.CharacterData;
import it.polimi.ingsw.server.model.baseLogic.StudentColor;
import it.polimi.ingsw.server.model.expertLogic.character.costants.CharacterUtility;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

public class GuiDrawer {

    private final boolean expert;
    private final int nPlayer;

    public GuiDrawer(boolean expert, int player) {
        this.expert = expert;
        this.nPlayer = player;
    }

    public GraphicCastle drawCastles(CastleData myCastle, List<CastleData> otherCastle, Pane myCastlePane , FlowPane castleFlowPane, EventHandler<MouseEvent> moveStudentToCasle) {
        int i;
        GraphicCastle myGraphicCastle = new GraphicCastle(myCastle,false,nPlayer == 3);
        myGraphicCastle.getDiningRoom().setOnMouseClicked(moveStudentToCasle);
        myCastlePane.getChildren().add(myGraphicCastle);

        castleFlowPane.getChildren().add(new OtherGraphicCastle(myCastle,nPlayer == 3, expert));

        for (i = 1; i < otherCastle.size()+1; i++) {
            CastleData castleData = otherCastle.get(i-1);
            castleFlowPane.getChildren().add(new OtherGraphicCastle(castleData,nPlayer == 3, expert));
        }
        return myGraphicCastle;
    }

    public void cleanCastles(Pane myCastlePane, FlowPane castleFlowPane) {
        myCastlePane.getChildren().clear();
        castleFlowPane.getChildren().clear();
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

    public void drawCharacters(List<CharacterData> characters, CharacterUtility activatedCharacter, FlowPane charFlowPane, EventHandler<MouseEvent> payCharacter, EnumMap<StudentColor, Integer> diningRoom){
        for (CharacterData character : characters) {
            boolean active = activatedCharacter != null && character.getName().equalsIgnoreCase(activatedCharacter.name());
            GraphicCharacter graphicCharacter = GraphicCharacter.newGraphicCharacter(character.getName(), character.getStudents(), character.getDescription(), active, diningRoom);
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
        GraphicIsland island = new GraphicIsland(islandData.getStudents(), islandData.getIslandSize(), islandData.getOwnership(), index, motherNaturePosition == index, islandData.isBlocked());
        island.setOnMouseClicked(onClick);
        return island;
    }
}
