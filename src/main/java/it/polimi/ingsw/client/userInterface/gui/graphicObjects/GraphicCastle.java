package it.polimi.ingsw.client.userInterface.gui.graphicObjects;

import it.polimi.ingsw.communication.modelData.CastleData;
import it.polimi.ingsw.server.model.baseLogic.StudentColor;
import it.polimi.ingsw.server.model.baseLogic.Team;
import javafx.event.EventHandler;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class GraphicCastle extends BorderPane {
    WaitingRoom waitingRoom;
    DiningRoom diningRoom;
    public GraphicCastle(CastleData castleData, boolean disable, boolean is3Game) {
        this.setPrefSize(290, 675);
        this.getStyleClass().add("castlePane");

        VBox center = new VBox();
        center.setPrefSize(290, 347);
        center.getChildren().add(new ProfessorRoom(castleData.teachers(), castleData.towerColor()));
        center.getChildren().add(diningRoom = new DiningRoom(castleData.diningRoom()));


        this.setBottom(waitingRoom = new WaitingRoom(castleData.waitingRoom(), disable, is3Game));
        this.setCenter(center);
        this.setTop(new TowerGarden(castleData.towerColor(), castleData.nTower()));
    }

    public MultipleToggleGroup getWaitingRoomToggleGroup() {
        return waitingRoom.multipleToggleGroup;
    }

    public Pane getDiningRoom() {
        return diningRoom;
    }
}

class WaitingRoom extends Pane {
    public MultipleToggleGroup multipleToggleGroup;
    public WaitingRoom(List<StudentColor> students, boolean is3Game, boolean disable) {
        this.setPrefSize(290, 108);
        List<GraphicStudent> graphicStudents = new ArrayList<>();
        students.forEach(color -> graphicStudents.add(new GraphicStudent(color)));
        multipleToggleGroup = new MultipleToggleGroup(is3Game ? 4 : 3);
        graphicStudents.forEach(multipleToggleGroup::add);
        setStudentPosition(graphicStudents);
        graphicStudents.forEach(button -> button.setDisable(disable));
        this.getChildren().addAll(graphicStudents);
    }

    private void setStudentPosition(List<GraphicStudent> graphicStudents) {
        double x = 37;
        double y = 16;
        graphicStudents.get(0).setLayoutX(x);
        graphicStudents.get(0).setLayoutY(y);
        x = 84.5;
        for (int i = 1; i < graphicStudents.size(); i++) {
            graphicStudents.get(i).setLayoutX(x);
            graphicStudents.get(i).setLayoutY(y);
            x = (i % 2) == 1 ? x : x + 47.5;
            y = y == 16? 55 : 16;
        }
    }
}

class DiningRoom extends Pane {
    public DiningRoom(EnumMap<StudentColor, Integer> diningRoom) {
        this.setPrefSize(290, 347);
        List<Table> tables = new ArrayList<>();
        for (StudentColor color: StudentColor.values()) {
            tables.add(new Table(color, diningRoom.get(color)));
        }
        setTablesPositions(tables);
        this.getChildren().addAll(tables);
    }

    private void setTablesPositions(List<Table> tables){
        double x = 37;
        double y = 13;
        for (Table table : tables) {
            table.setLayoutX(x);
            table.setLayoutY(y);
            x += 47.5;
        }
    }

    static class Table extends Pane {
        private boolean selected;

        public Table(StudentColor studentColor, int nStudent) {
            List<GraphicStudent> students = new ArrayList<>();
            selected = false;

            for (int i = 0; i < nStudent; i++) {
                students.add(new GraphicStudent(studentColor, true));
            }
            setStudentPosition(students);
            this.getChildren().addAll(students);
            this.setOnMouseClicked(this::onClick);
        }

        private void setStudentPosition(List<GraphicStudent> students) {
            double y = 287;
            for (GraphicStudent student : students) {
                student.setLayoutY(y);
                y -= 32;
            }
        }

        private void onClick(MouseEvent mouseEvent){
            if(getStyleClass().contains("selected"))
                getStyleClass().remove("selected");
            else
                getStyleClass().add("selected");
            selected = !selected;
        }
    }
}

class ProfessorRoom extends Pane {
    public ProfessorRoom(Map<StudentColor, Team> teachers, Team team) {
        this.setPrefSize(290, 70);
        
        List<GraphicTeacher> graphicTeachers = new ArrayList<>();

        for (StudentColor color : StudentColor.values()) {
            if(teachers.get(color) == team) graphicTeachers.add(new GraphicTeacher(color));
            else graphicTeachers.add(new GraphicTeacher());
        }
        setTeacherPositions(graphicTeachers);
        this.getChildren().addAll(graphicTeachers);
    }
    
    private void setTeacherPositions(List<GraphicTeacher> teachers) {
        double x = 32.5;
        double y = 17;
        for (GraphicTeacher teacher : teachers) {
            teacher.setLayoutX(x);
            teacher.setLayoutY(y);
            x += 47.5;
        }
    }
}

class TowerGarden extends Pane {
    public TowerGarden(Team team, int nTower) {
        this.setPrefSize(290, 150);

        List<GraphicTower> towers = new ArrayList<>();
        for (int i = 0; i < (8 - nTower); i++) {
            towers.add(new GraphicTower(team, 50, 50));
        }
        setTowerPositions(towers);
        this.getChildren().addAll(towers);
    }

    private void setTowerPositions(List<GraphicTower> towers) {
        double x = 50;
        double y = 20;
        for (int i = 0; i < towers.size(); i++) {
            towers.get(i).setLayoutX(x);
            towers.get(i).setLayoutY(y);
            x = (i % 2) == 0 ? x : x + 50;
            y = y == 20? 90 : 20;
        }
    }
}
