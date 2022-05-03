package it.polimi.ingsw.communication.modelData;

import it.polimi.ingsw.model.Cloud;
import it.polimi.ingsw.model.Color;

import java.util.List;
import java.util.Objects;

public final class CloudData {
    private final List<Color> studentList;

    public CloudData(
            List<Color> studentList
    ) {
        this.studentList = studentList;
    }

    public CloudData(Cloud cloud) {
        this(cloud.getStudentList());
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
        return "CloudData[" +
                "studentList=" + studentList + ']';
    }

}
