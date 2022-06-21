package it.polimi.ingsw.server.model.baseLogic;
import org.jetbrains.annotations.Contract;

import java.util.ArrayList;
import java.util.List;

public class Cloud {

    private final Bag bag;
    private final List<StudentColor> studentList;
    private final int STUDENTS_ON_CLOUD;

    public Cloud(Bag bag, int size) {
        this.bag = bag;
        this.STUDENTS_ON_CLOUD = size;
        this.studentList = new ArrayList<>();
        refill();
    }

    public List<StudentColor> choose() {
        if(studentList.isEmpty())
            throw new IllegalArgumentException("This cloud is no longer available.");;
        List<StudentColor> students = new ArrayList<>(studentList);
        studentList.clear();
        return students;
    }

    public void refill() {
        studentList.addAll(bag.multipleExtract(STUDENTS_ON_CLOUD));
    }

    @Contract(pure = true)
    public boolean isAvailable() {
        return !studentList.isEmpty();
    }

    @Contract(pure = true)
    public List<StudentColor> getStudentList() {
        return new ArrayList<>(studentList);
    }

    @Override
    public String toString() {
        return "Cloud{" +
                "studentList=" + studentList +
                '}';
    }
}
