package it.polimi.ingsw.client.userInterface.gui.controller;

import it.polimi.ingsw.client.userInterface.gui.graphicObjects.GraphicCastle;
import it.polimi.ingsw.client.userInterface.gui.graphicObjects.GraphicCharacter;
import it.polimi.ingsw.client.userInterface.gui.graphicObjects.MultipleToggleGroup;
import it.polimi.ingsw.communication.command.Command;
import it.polimi.ingsw.communication.command.CommandType;
import it.polimi.ingsw.communication.modelData.BoardData;
import it.polimi.ingsw.server.model.baseLogic.StudentColor;
import it.polimi.ingsw.server.model.baseLogic.TurnPhase;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class GamePaneController {

    @FXML public FlowPane castleFlowPane, cardsFlowPane, charFlowPane;
    @FXML public Pane turnPane;
    @FXML public StackPane bottomStackPane;
    @FXML private Pane islandLeftPane, islandRightPane, myCastlePane;
    @FXML private FlowPane islandsTopRow, islandsBotRow, cloudFlowPane;
    @FXML private ToggleButton expertMode;
    private MultipleToggleGroup waitingRoomToggleGroup;
    private Consumer<Command> send;
    private BoardData boardData;
    private final List<String> parameters = new ArrayList<>();

    GuiDrawer guiDrawer;

    public void initialize(Consumer<Command> send) {
        this.send = send;
    }

    public void clean() {
        guiDrawer.cleanCards(cardsFlowPane);
        guiDrawer.cleanCharacters(charFlowPane);
        guiDrawer.cleanIslands(islandLeftPane, islandRightPane, islandsTopRow, islandsBotRow);
        guiDrawer.cleanClouds(cloudFlowPane);
        guiDrawer.cleanTurn(turnPane);
        guiDrawer.cleanCastles(myCastlePane, castleFlowPane);
    }

    public void printMessage(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public boolean newGameMessage(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to play again?");
        alert.getButtonTypes().clear();
        alert.getButtonTypes().addAll(ButtonType.NO, ButtonType.YES);
        alert.setHeaderText(null);
        return alert.showAndWait().orElse(ButtonType.NO) == ButtonType.YES;
    }

    public void moveStudentToDiningRoom(MouseEvent mouseEvent) {
        System.out.println("----");
        List<ToggleButton> selected = waitingRoomToggleGroup.getSelectedToggles();
        if (selected.size() == 0)
            return;
        List<String> parameters = new ArrayList<>();
        for (ToggleButton toggleButton: selected) {
            StudentColor studentColor = null;
            try {
                studentColor = StudentColor.parseColor(toggleButton.getAccessibleText());
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            parameters.add(studentColor.name());
        }
        Command command;
        try {
            command = new Command(CommandType.MOVE_STUDENT_TO_CASTLE, parameters);
            send.accept(command);
            System.out.println(command);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
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
                Command command;
                try {
                    command = new Command(CommandType.MOVE_STUDENT_TO_ISLAND, parameters);
                    send.accept(command);
                } catch (ParseException ignored) {}
            } else if (boardData.turn().currentPhase() == TurnPhase.MOTHERNATURE) {
                List<String> parameters = new ArrayList<>();
                int moveDistance = Integer.parseInt(island.getAccessibleText()) - (boardData.motherNaturePosition() + 1);
                parameters.add(String.valueOf(moveDistance > 0 ? moveDistance : moveDistance + boardData.islandList().size()));
                Command command;
                try {
                    command = new Command(CommandType.MOVE_MOTHER_NATURE, parameters);
                    send.accept(command);
                } catch (ParseException ignored) {}
            }
        }
        else {
            parameters.add(island.getAccessibleText());
            System.out.println(parameters);
            Command command;
            try {
                command = new Command(CommandType.PAY_CHARACTER, new ArrayList<>(parameters));
                send.accept(command);
            } catch (IllegalArgumentException | ParseException ignored) {}
        }
    }

    public void chooseCloud(MouseEvent mouseEvent) {
        int cloudId;
        //take cloud id from accessibleText
        String accessibleText = ((Pane) mouseEvent.getTarget()).getAccessibleText();
        cloudId = Integer.parseInt(accessibleText.substring(accessibleText.length()-1));
        List<String> parameters = new ArrayList<>();
        parameters.add(String.valueOf(cloudId));
        Command command;
        try {
            command = new Command(CommandType.CHOOSE_CLOUD, parameters);
            send.accept(command);
        } catch (ParseException ignored) {
        }
    }

    public void playCard(MouseEvent mouseEvent) {   //TODO: fix jester command, taxman student drawing
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
            command = new Command(CommandType.PLAY_CARD, parameters);
        } catch (ParseException ignored) {}
        send.accept(command);
    }

    public void payCharacter(MouseEvent mouseEvent) {
        parameters.clear();
        GraphicCharacter character;
        try {
            character = (GraphicCharacter) mouseEvent.getTarget();
        } catch (ClassCastException e) {
            FlowPane flowPane = (FlowPane) mouseEvent.getTarget();
            character = (GraphicCharacter) flowPane.getParent();
        }
        parameters.add(character.getAccessibleText());
        parameters.addAll(character.getMultipleToggleGroup().getSelectedToggles().stream().map(ToggleButton::getAccessibleText).toList());
        Command command;
        try {
            command = new Command(CommandType.PAY_CHARACTER, new ArrayList<>(parameters));
            send.accept(command);
        } catch (IllegalArgumentException | ParseException ignored) {}
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
