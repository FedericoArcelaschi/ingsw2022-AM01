package it.polimi.ingsw.model;
<<<<<<< Updated upstream
<<<<<<< Updated upstream
import java.util.Arrays;
import java.util.Random;

public class Bag {
    private final int[] students;
    private long seed;
    private final Random random;

    public long getSeed() {
        return seed;
    }

    public Bag(){
        students = new int[]{24, 24, 24, 24, 24};
        random = new Random();
    }

    public Bag(long seed){
        this.seed = seed;
        students = new int[]{24, 24, 24, 24, 24};
        random = new Random(seed);
    }

    public Color extract(){
        int r;
        int color = 0;
        int rs = remainingStudents();
        if(rs <=0) return null;
        r = random.nextInt(rs);
        for(int studentsForColor : students){
            if(r>studentsForColor){
                r-=studentsForColor;
                color++;
            }
        }
        students[color]--;
        switch(color){
            case 0: return Color.YELLOW;
            case 1: return Color.GREEN;
            case 2: return Color.PINK;
            case 3: return Color.BLUE;
            case 4: return Color.RED;
        }
        return null;
    }

    private int remainingStudents(){
        return Arrays.stream(students).sum();
    }
}
=======
=======
>>>>>>> Stashed changes

public class Bag {
    public
}
<<<<<<< Updated upstream
>>>>>>> Stashed changes
=======
>>>>>>> Stashed changes
