package it.polimi.ingsw.model;
import java.util.ArrayList;
import java.util.List;

public class Cloud {
    Bag bag;
    List<Color> studentList;
    int size;

    public Cloud(Bag bag, int size){
        this.bag=bag;
        this.size = size;
        this.studentList = new ArrayList<>(size);
    }

    public boolean refill(){
        for(int i=0; i<size; i++){
            studentList.set(0, bag.extract());
        }
        return true;
    }

    public List<Color> choose() {
        List<Color> students = new ArrayList<>(studentList);
        studentList.clear();
        return students;
    }
}
