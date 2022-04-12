package it.polimi.ingsw.model;

import it.polimi.ingsw.model.exceptions.NoSuchStudentException;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.*;

public class BoardTest extends TestCase {

    public void testBoardIslandNumber(){
        String player1 = "1";
        String player2 = "2";
        Board b=new Board(player1,player2);
        System.out.println(b.getIslandList());
        assertEquals(12, b.getIslandList().size());
    }

    public void testBoardIslandStartingColor(){
        String player1 = "1";
        String player2 = "2";
        Board b=new Board(player1,player2);

        for(int i=0; i<12; i++){
            int nStudents = b.getIslandList().get(i).getStudents().values().stream().mapToInt(n -> n).sum();
            if(i%6 == 0)    assertEquals(0, nStudents);
            else assertEquals(1,nStudents);
        }
    }

    public void testBoardCloudNumber(){
        String player1 = "1";
        String player2 = "2";
        String player3 = "3";
        String player4 = "4";
        Board b2=new Board(player1,player2);
        Board b3=new Board(player1,player2,player3);
        Board b4=new Board(player1,player2,player3,player4);
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

    public void testResetClouds() {
        String player1 = "1";
        String player2 = "2";
        Board b=new Board(player1,player2);

        assertTrue(b.refillClouds());
    }

    public void testChooseCloud() throws NoSuchStudentException {
        String player1 = "1";
        String player2 = "2";
        Board b=new Board(player1,player2);
        List<Color> cl = new ArrayList<>();
        //move 4 element to DR to free space for new students coming from cloud
        for(int i=0; i<4;i++){
            cl.add(b.getCastleMap().get(player1).getWaitingRoom().get(i));
        }
        System.out.println(b.moveStudentToDR(player1, cl));
        //move the students from cloud to WR
        assertTrue(b.chooseCloud(player1, 0));
    }

    public void testMoveStudentToIsland() throws NoSuchStudentException {
        String player1 = "1";
        String player2 = "2";
        Board b=new Board(player1,player2);
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

    public void testPlayCard() {
        String player1 = "1";
        String player2 = "2";
        Board b=new Board(player1,player2);
        //check if the card is not used at the beginning
        assertFalse(b.getCastleMap().get(player1).getCards().get(0).isPlayed());
        //check if the card is played correctly
        assertTrue(b.playCard(player1,1));
        //check if the card is set as used
        assertTrue(b.getCastleMap().get(player1).getCards().get(0).isPlayed());
        //check if last card played is the one we played
        assertEquals(1, b.getCastleMap().get(player1).getLastCardPlayed().getPriority());
        assertEquals(1, b.getCastleMap().get(player1).getLastCardPlayed().getDistance());
        //check if the card can't be reused
        assertFalse(b.playCard(player1,1));
    }

    public void testUpdateProfessor() throws NoSuchStudentException {
        String player1 = "1";
        String player2 = "2";
        Board b = new Board(player1,player2);
        List<Color> students = Arrays.asList(b.getCastleMap().get(player1).getWaitingRoom().get(0),b.getCastleMap().get(player1).getWaitingRoom().get(1));
        b.moveStudentToDR(player1, students);

        //test professor get assigned
        Map<Color,Castle> pm1 = b.getProfessorMap();
        Map<Color,Castle> pm2;
        for(Color c : Color.values()){
            if(students.contains(c)){
                assertNotNull(pm1.get(c));
            }
            else{
                assertNull(pm1.get(c));
            }
        }
    }

    public void testIsWinningPosition() {
    }

    public void testIsWonByResources() {
    }

    public void testMoveMotherNature() {
        String player1 = "1";
        String player2 = "2";
        Board b=new Board(player1,player2);
        assertTrue(b.moveMotherNature(3));
    }
}