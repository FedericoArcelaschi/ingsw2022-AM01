package it.polimi.ingsw.model.baseLogic;
import java.util.*;
import java.util.random.RandomGenerator;

public class Bag {

    private final Map<StudentColor, Integer> students;
    private final long seed;
    private final Random random;

    @Deprecated
    public Bag(int studentsPerColor) {
        this.seed = RandomGenerator.getDefault().nextLong();
        students = new HashMap<>();
        for (StudentColor c : StudentColor.values()) students.put(c, studentsPerColor);
        random = new Random(seed);
    }

    public Bag(int studentsPerColor, long seed) {
        this.seed = seed;
        students = new HashMap<>();
        for(StudentColor c: StudentColor.values()) students.put(c,studentsPerColor);
        random = new Random(seed);
    }

    public Bag(Bag bag) {
        this.random = bag.random;
        this.students = bag.students;
        this.seed = bag.getSeed();
    }

    /**
     * extract a random student from the pool of students represented with the Map students
     * @return the color of the extracted student
     */
    public StudentColor extract(){
        int rs = remainingStudents();
        if(rs == 0) return null;
        int r = random.nextInt(rs);
        Set<StudentColor> keyList = students.keySet();
        for(StudentColor c : keyList){
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
     *
     * @param n number of student to extract
     * @return a list of colors of the students extracted
     */
    public List<StudentColor> multipleExtract(int n) {
        List<StudentColor> l = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            l.add(this.extract());
        }
        return l;
    }

    /**
     * extract 2 student in a random order to put them in islands at the beginning of the game
     * @return a list of 10 students (2 per color) in a random order
     */
    public List<StudentColor> extractForIslandSetup(){
        List<StudentColor> extractedList = new ArrayList<>();
        for(int i=0; i<2; i++){
            for(StudentColor c : StudentColor.values()) {
                extractedList.add(c);
                students.replace(c, students.get(c) - 1);
            }
        }
        Collections.shuffle(extractedList, random);
        return extractedList;
    }

    public List<StudentColor> extractForCastleSetup(int nPlayers){
        final int waitingRoomSize2Players = 7;
        final int waitingRoomSize3Players = 9;
        return switch (nPlayers) {
            case 2, 4 -> multipleExtract(waitingRoomSize2Players);
            case 3 -> multipleExtract(waitingRoomSize3Players);
            default -> throw new IllegalArgumentException("Not a valid number of Players");
        };
    }

    /**
     * sum the number of remaining students per color
     * @return the sum
     */
    public int remainingStudents(){
        return students.values().stream().reduce(0, Integer::sum);
    }

    /**
     * Checks if two bags contain the same students.
     * @param b bag to confront
     * @return true if they are equal, false if they aren't
     */
    public boolean equals(Bag b){
        return this.students.equals(b.students) && this.getSeed()==b.getSeed();

    }

    public long getSeed() {
        return seed;
    }

    public int getStudents(StudentColor c) {
        return students.get(c);
    }

    /**
     * static method for extraction. Extracts up to 500 random students.
     */
    public static List<StudentColor> extractMany(int n){
        Bag bag = new Bag(100);
        return bag.multipleExtract(n);
    }
}