package it.polimi.ingsw.server.model.baseLogic;
import java.util.ArrayList;
import java.util.List;

public class Cloud {
    private final Bag bag;
    private final List<StudentColor> studentList;
    private final int size;
    private boolean available;

    public Cloud(Bag bag, int size) {
        this.bag = bag;
        this.size = size;
        this.studentList = new ArrayList<>();
        this.available = true;
        refill();
    }

    public boolean refill() {
        studentList.clear();
        for(int i = 0; i < size; i++) {
            studentList.add(bag.extract());
        }
        return true;
    }

    public List<StudentColor> choose() {
        if(!available) return null;
        List<StudentColor> students = new ArrayList<>(studentList);
        available = false;
        return students;
    }

    public int getSize() {
        return size;
    }

    public boolean isAvailable() {
        return available;
    }

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
