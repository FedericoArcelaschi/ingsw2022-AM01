package it.polimi.ingsw.model;
import java.util.*;

public class Bag {

    private final Map<Color, Integer> students;
    private long seed;
    private final Random random;

    public Bag(int studentsPerColor){
        students = new HashMap<>();
        for(Color c: Color.values()) students.put(c,studentsPerColor);
        random = new Random();
        seed = random.nextLong();
        random.setSeed(seed);
    }

    public Bag(int studentsPerColor, long seed){
        this.seed = seed;
        students = new HashMap<>();
        for(Color c: Color.values()) students.put(c,studentsPerColor);
        random = new Random(seed);
    }

    /**
     * Gets a random student
     * @return One Student (color)
     */
    public Color extract(){
        int rs = remainingStudents();
        if(rs == 0) return null;
        int r = random.nextInt(rs);
        Set<Color> keyList = students.keySet();
        for(Color c : keyList){
            if(r > students.get(c)) r-=students.get(c);
            else{
                students.replace(c,students.get(c)-1);
                return c;
            }
        }
        return null;
    }

    public List<Color> extractMultipleStudents(int n){
        List<Color> l = new ArrayList<>();
        for(int i=0; i<n; i++){
            l.add(this.extract());
        }
        return l;
    }

    /**
     * @return 2 students of each color in random order.
     */
    public List<Color> extractForIslandSetup(){
        List<Color> extractedList = new ArrayList<>();
        for(int i=0; i<2; i++){
            for(Color c : Color.values()) {
                extractedList.add(c);
                students.replace(c, students.get(c) - 1);
            }
        }
        Collections.shuffle(extractedList);
        return extractedList;
    }

    /**
     * @return The number of students in the bag. Used to end the game for
     */
    public int remainingStudents(){
        return students.values().stream().reduce(0, Integer::sum);
    }

    public long getSeed() {
        return seed;
    }

    public int getStudents(Color c) {
        return students.get(c);
    }
}