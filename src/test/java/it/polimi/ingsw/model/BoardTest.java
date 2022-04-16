package it.polimi.ingsw.model;

import it.polimi.ingsw.model.exceptions.NoSuchStudentException;
import it.polimi.ingsw.model.exceptions.NotYourTurnException;
import org.junit.jupiter.api.Test;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTest{
    @Test
    public void testBoardIslandNumber(){
        String player1 = "1";
        String player2 = "2";
        Turn t = new Turn(Arrays.asList(player1,player2));
        Board b=new Board(player1,player2,t);
        assertEquals(12, b.getIslandList().size());
    }

    @Test
    public void testGetAvailableCard() throws NotYourTurnException {
        String player1 = "1";
        String player2 = "2";
        Turn t = new Turn(Arrays.asList(player1,player2));
        Board b=new Board(player1,player2,t);
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
        String player1 = "1";
        String player2 = "2";
        Turn t = new Turn(Arrays.asList(player1,player2));
        Board b=new Board(player1,player2,t);

        assertThrows(NotYourTurnException.class, () -> b.chooseCloud(player2,0), "");
    }

    @Test
    public void testBoardIslandStartingColor(){
        String player1 = "1";
        String player2 = "2";
        Turn t = new Turn(Arrays.asList(player1,player2));
        Board b=new Board(player1,player2,t);

        for(int i=0; i<12; i++){
            int nStudents = b.getIslandList().get(i).getStudents().values().stream().mapToInt(n -> n).sum();
            if(i%6 == 0)    assertEquals(0, nStudents);
            else assertEquals(1,nStudents);
        }
    }
    @Test
    public void testBoardCloudNumber(){
        String player1 = "1";
        String player2 = "2";
        String player3 = "3";
        String player4 = "4";
        Turn t1 = new Turn(Arrays.asList(player1,player2));
        Turn t2 = new Turn(Arrays.asList(player1,player2,player3));
        Turn t3 = new Turn(Arrays.asList(player1,player2,player3,player4));
        Board b2=new Board(player1,player2,t1);
        Board b3=new Board(player1,player2,player3,t2);
        Board b4=new Board(player1,player2,player3,player4,t3);
        List<Cloud> cl2 = b2.getCloudList();
        List<Cloud> cl3 = b3.getCloudList();
        List<Cloud> cl4 = b4.getCloudList();
        //test numbers of clouds
        assertEquals(2, cl2.size());
        assertEquals(3, cl3.size());
        assertEquals(4, cl4.size());

        //test dimension of clouds
        assertEquals(4, cl2.get(0).getSize());
        assertEquals(3, cl3.get(0).getSize());
        assertEquals(4, cl4.get(0).getSize());
    }
    @Test
    public void testResetClouds() {
        String player1 = "1";
        String player2 = "2";

        Turn t = new Turn(Arrays.asList(player1,player2));
        Board b=new Board(player1,player2,t);

        assertTrue(b.refillClouds());
    }
    @Test
    public void testChooseCloud() throws NoSuchStudentException, NotYourTurnException {
        String player1 = "1";
        String player2 = "2";

        Turn t = new Turn(Arrays.asList(player1,player2));
        Board b=new Board(player1,player2,t);

        List<Color> cl = new ArrayList<>();
        //move 4 element to DR to free space for new students coming from cloud
        for(int i=0; i<4;i++){
            cl.add(b.getCastleMap().get(player1).getWaitingRoom().get(i));
        }
        assertTrue(b.moveStudentToDR(player1, cl));
        //move the students from cloud to WR
        assertTrue(b.chooseCloud(player1, 0));
    }
    @Test
    public void testMoveStudentToIsland() throws NoSuchStudentException, NotYourTurnException {
        String player1 = "1";
        String player2 = "2";
        Turn t = new Turn(Arrays.asList(player1,player2));
        Board b=new Board(player1,player2,t);
        List<Color> cl =  new ArrayList<>();

        cl.add(b.getCastleMap().get(player1).getWaitingRoom().get(0));
        cl.add(b.getCastleMap().get(player1).getWaitingRoom().get(1));
        Map<Color, Integer> students = new HashMap<>();
        for(Color c : Color.values()){
            students.put(c,0);
        }
        for(Color c : cl){
            students.replace(c, students.get(c)+1);
        }
        //test if the method returns correctly
        assertTrue(b.moveStudentToIsland(player1, 0, cl));
        //test if the student get removed from castle waiting room
        assertEquals(9-cl.size(),b.getCastleMap().get(player1).getWaitingRoom().size());
        //test if the student get added to the island
        Map<Color, Integer> studentsOnIsland = b.getIslandList().get(0).getStudents();
        assertEquals(students, studentsOnIsland);
    }
    @Test
    public void testPlayCard() throws NotYourTurnException {
        String player1 = "1";
        String player2 = "2";
        Turn t = new Turn(Arrays.asList(player1,player2));
        Board b=new Board(player1,player2,t);
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
    public void testUpdateProfessor() throws NoSuchStudentException, NotYourTurnException {
        String player1 = "1";
        String player2 = "2";
        Turn t = new Turn(Arrays.asList(player1,player2));
        Board b=new Board(player1,player2,t);
        List<Color> students = Arrays.asList(b.getCastleMap().get(player1).getWaitingRoom().get(0),b.getCastleMap().get(player1).getWaitingRoom().get(1));
        b.moveStudentToDR(player1, students);

        //test professor get assigned
        Map<Color,Team> pm1 = b.getProfessorMap();
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
        String player1 = "1";
        String player2 = "2";
        Turn t = new Turn(Arrays.asList(player1,player2));
        Board b=new Board(player1,player2,t);
        assertNull(b.isWonByResources());
    }
    @Test
    public void testMoveMotherNature() {
        String player1 = "1";
        String player2 = "2";
        Turn t = new Turn(Arrays.asList(player1,player2));
        Board b=new Board(player1,player2,t);
        assertTrue(b.moveMotherNature(3));
    }

    @Test
    public void testJoinIsland() {

    }
}