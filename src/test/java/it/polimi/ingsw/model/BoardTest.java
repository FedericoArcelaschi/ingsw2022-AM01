package it.polimi.ingsw.model;

import it.polimi.ingsw.model.exceptions.NoSuchStudentException;
import it.polimi.ingsw.model.exceptions.NotYourTurnException;
import it.polimi.ingsw.model.exceptions.TooManyStudentsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Isolated;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {

    private final String player1 = "a", player2= "2";
    private Board b;
    private Turn t;
    private int seed = 1;

    @BeforeEach
    void setUp() {
        List<String> listOfPlayer = Arrays.asList(player1, player2);
        t = new Turn(listOfPlayer);
        b =  BoardFactory.getBoard(listOfPlayer, t, seed);
    }

    @Test
    public void testBoardIslandNumber() {

        assertEquals(12, b.getIslandList().size());
    }

    @Test
    public void testGetAvailableCard() throws NotYourTurnException {
        assertEquals(10, Arrays.stream(b.getCastleMap().get(player1).getCards()).filter(card -> card != null && !card).count());
        b.playCard(player1, 1);
        b.playCard(player1, 2);
        b.playCard(player1, 3);
        b.playCard(player1, 4);
        b.playCard(player1, 5);
        b.playCard(player1, 6);
        b.playCard(player1, 7);
        b.playCard(player1, 8);
        b.playCard(player1, 9);
        b.playCard(player1, 10);
        assertEquals(0, Arrays.stream(b.getCastleMap().get(player1).getCards()).filter(card -> card != null && !card).count());
    }

    @Test
    public void testNotYourTurnException(){
        assertThrows(NotYourTurnException.class, () -> b.chooseCloud(player2,0), "");
    }

    @Test
    public void testBoardIslandStartingColor(){
        for(int i=0; i<12; i++){
            int nStudents = b.getIslandList().get(i).getStudents().values().stream().mapToInt(n -> n).sum();
            if(i%6 == 0)    assertEquals(0, nStudents);
            else assertEquals(1,nStudents);
        }
    }

    @Test
    public void testBoardCloudNumber(){
        String player3 = "3";
        String player4 = "4";
        Turn t1 = new Turn(Arrays.asList(player1,player2));
        Turn t2 = new Turn(Arrays.asList(player1,player2,player3));
        Turn t3 = new Turn(Arrays.asList(player1,player2,player3,player4));
        Board b2=new Board(player1,player2,t1, seed);
        Board b3=new Board(player1,player2,player3,t2, seed);
        Board b4=new Board(player1,player2,player3,player4,t3, seed);
        List<Cloud> cl2 = b2.getCloudList();
        List<Cloud> cl3 = b3.getCloudList();
        List<Cloud> cl4 = b4.getCloudList();

        //test numbers of clouds
        assertEquals(2, cl2.size());
        assertEquals(3, cl3.size());
        assertEquals(4, cl4.size());

        //test dimension of clouds
        assertEquals(3, cl2.get(0).getSize());
        assertEquals(4, cl3.get(0).getSize());
        assertEquals(3, cl4.get(0).getSize());
    }
    @Test
    public void testResetClouds() {
        assertTrue(b.refillClouds());
    }

    @Test
    public void testChooseCloud() throws NoSuchStudentException, NotYourTurnException, TooManyStudentsException {
        List<Color> cl = new ArrayList<>();
        //move 4 element to DR to free space for new students coming from cloud
        for(int i=0; i<4;i++){
            cl.add(b.getCastleMap().get(player1).getWaitingRoom().get(i));
        }
        b.moveStudentToDiningRoom(player1, cl);
        //move the students from cloud to WR
        assertTrue(b.chooseCloud(player1, 0));
    }
    @Test
    public void testMoveStudentToIsland() throws NoSuchStudentException, NotYourTurnException, TooManyStudentsException {
        List<Color> colorList =  new ArrayList<>();

        colorList.add(b.getCastleMap().get(player1).getWaitingRoom().get(0));
        colorList.add(b.getCastleMap().get(player1).getWaitingRoom().get(1));
        Map<Color, Integer> students = new HashMap<>();
        for(Color c : Color.values()){
            students.put(c,0);
        }
        for(Color c : colorList){
            students.replace(c, students.get(c) + 1);
        }
        //test if the method returns correctly
        assertTrue(b.moveStudentToIsland(player1, 0, colorList));
        //test if the student get removed from castle waiting room
        assertEquals(7 - colorList.size(),
                b.getCastle(player1).getWaitingRoom().size(),
                colorList.size() + " students should have been removed from the waiting room, but " +
                        (7 - b.getCastle(player1).getWaitingRoom().size()) + "students were removed");
        //test if the student get added to the island
        Map<Color, Integer> studentsOnIsland = b.getIslandList().get(0).getStudents();
        assertEquals(students, studentsOnIsland);
    }
    @Test
    public void testPlayCard() throws NotYourTurnException {
        //check if the card is not used at the beginning
        assertFalse(b.getCastleMap().get(player1).getCards()[0]);
        //check if the card is played correctly
        assertTrue(b.playCard(player1,1));
        //check if the card is set as used
        assertTrue(b.getCastleMap().get(player1).getCards()[0]);
        //check if last card played is the one we played
        assertEquals(1, b.getCastleMap().get(player1).getLastCardPlayed());
        assertEquals(1, (b.getCastleMap().get(player1).getLastCardPlayed()+1)/2);
        //check if the card can't be reused
        assertFalse(b.playCard(player1,1));
    }
    @Test
    public void testUpdateProfessor() throws NoSuchStudentException, NotYourTurnException, TooManyStudentsException {
        List<Color> students = Arrays.asList(b.getCastleMap().get(player1).getWaitingRoom().get(0),b.getCastleMap().get(player1).getWaitingRoom().get(1));
        b.moveStudentToDiningRoom(player1, students);

        //test professor get assigned
        Map<Color,Team> pm1 = b.getProfessorsMap();
        for(Color c : Color.values()){
            if(students.contains(c)){
                assertNotNull(pm1.get(c));
            }
            else{
                assertNull(pm1.get(c));
            }
        }
    }

    @Test
    public void testIsNotWonByResources() {
        assertNull(b.isWonByResources());
    }

    @Test
    public void testMoveMotherNature() throws NotYourTurnException {
        b.playCard(player1, 3);
        b.moveMotherNature(1);
    }

    /**
     * Test from blank board.
     * Joins 3 islands with three students and no owner.
     */
    @Test
    public void testJoinIsland(){
        List<Island> oldList = new ArrayList<>(b.islandList);
        Island islandA = b.islandList.get(1);
        Island islandB = b.islandList.get(2);
        Island islandC = b.islandList.get(3);
        b.joinIslands(Arrays.asList(islandA, islandB, islandC));
        assertEquals(oldList.get(0), b.islandList.get(0),
                "The first island should stay untouched");
        assertEquals(10, b.islandList.size(),
                "the island list shoud decrease by 2");
        assertEquals(3, b.islandList.get(1).getIslandNumber(),
                "the second island is the union of three");
        Map<Color, Integer> EmptyStudentsMap = b.islandList.get(0).getStudents(); //first island is empty
        Map<Color, Integer> expectedStudentsMap
                = new HashMap<>(EmptyStudentsMap);

        for (int i = 1; i < 4; i++) {
            for (Color student: Color.values()) {
                if(oldList.get(i).getStudents().get(student) > 0){
                    if(expectedStudentsMap.get(student) != null){
                        int previousStudents = expectedStudentsMap.get(student);
                        expectedStudentsMap
                                .replace(student,
                                        previousStudents +
                                        oldList.get(i).getStudents().get(student));
                        break;
                    }else{
                        expectedStudentsMap
                                .put(student,
                                        oldList.get(i).getStudents().get(student));
                        break;
                    }
                }
            }
        }
        assertEquals(expectedStudentsMap, b.islandList.get(1).getStudents(),
                "The second island should have all the students as the islands before");
        assertEquals(oldList.get(4), b.islandList.get(2),
                "also the next island is untouched");
        assertEquals(oldList.get(1).getOwnership(), b.islandList.get(2).getOwnership(),
                "no owner should be present");
    }
}