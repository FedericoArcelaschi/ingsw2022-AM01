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
     * extract a random student from the pool of students represented with the Map students
     * @return the color of the extracted student
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

    /**
     * extract random students from the pool of students represented with the Map students multiple times
     * @param n number of student to extract
     * @return a list of colors of the students extracted
     */
    public List<Color> multipleExtract(int n){
        List<Color> l = new ArrayList<>();
        for(int i=0; i<n; i++){
            l.add(extract());
        }
        return l;
    }

    /**
     * extract 2 student in a random order to put them in islands at the beginning of the game
     * @return a list of 10 students (2 per color) in a random order
     */
    public List<Color> extractForIslandSetup(){
        List<Color> extractedList = new ArrayList<>();
        for(int i=0; i<2; i++){
            for(Color c : Color.values()) {
                extractedList.add(c);
                students.replace(c,students.get(c)-1);
            }
        }
        Collections.shuffle(extractedList);
        return extractedList;
    }

    /**
     * sum the number of remaining students per color
     * @return the sum
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