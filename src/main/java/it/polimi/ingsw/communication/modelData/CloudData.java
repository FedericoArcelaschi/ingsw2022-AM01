package it.polimi.ingsw.communication.modelData;

import it.polimi.ingsw.model.baseLogic.StudentColor;

import java.util.List;
import java.util.Objects;

public class CloudData {
    private final List<StudentColor> studentList;
    private final boolean available;

    public CloudData(List<StudentColor> studentList, boolean available) {
        this.studentList = studentList;
        this.available = available;
    }

    public List<StudentColor> getStudentList() {
        return studentList;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (CloudData) obj;
        return Objects.equals(this.studentList, that.studentList);
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (StudentColor student: studentList) {
            s.append(student).append(", ");
        }

        s.append(available ? "available" : "already taken");
        return s.toString();
    }

}
