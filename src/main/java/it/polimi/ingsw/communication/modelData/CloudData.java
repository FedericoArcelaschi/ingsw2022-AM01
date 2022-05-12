package it.polimi.ingsw.communication.modelData;

import it.polimi.ingsw.model.Color;

import java.util.List;
import java.util.Objects;

public class CloudData {
    private final List<Color> studentList;
    private final boolean available;

    public CloudData(
            List<Color> studentList,
            boolean available
    ) {
        this.studentList = studentList;
        this.available = available;
    }

    public List<Color> studentList() {
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
    public int hashCode() {
        return Objects.hash(studentList);
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (Color student: studentList) {
            s.append(student.str).append(", ");
        }

        s.append(available? "available": "already taken");
        return s.toString();
    }

}
