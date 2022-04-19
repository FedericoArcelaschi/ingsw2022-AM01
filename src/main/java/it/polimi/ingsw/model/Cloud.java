package it.polimi.ingsw.model;
import java.util.ArrayList;
import java.util.List;

public class Cloud {
    private Bag bag;
    private List<Color> studentList;
    private int size;

    public Cloud(Bag bag, int size){
        this.bag = bag;
        this.size = size;
        this.studentList = new ArrayList<>();
        refill();
    }

    public boolean refill() {
        studentList.clear();
        for(int i = 0; i < size; i++) {
            studentList.add(bag.extract());
        }
        return true;
    }

    public List<Color> choose() {
        if(studentList.size() != size) return null;
        List<Color> students = new ArrayList<>(studentList);
        studentList.clear();
        return students;
    }

    public int getSize() {
        return size;
    }

}
