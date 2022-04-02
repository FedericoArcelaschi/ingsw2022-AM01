package it.polimi.ingsw.model;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

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

    public void testChooseCloud() {
        String player1 = "1";
        String player2 = "2";
        Board b=new Board(player1,player2);
        List<Color> cl = new ArrayList<>();
        //move 4 element to DR to free space for new students coming from cloud
        for(int i=0; i<4;i++){
            cl.add(b.getCastleMap().get(player1).getWaitingRoom().get(i));
        }
        b.moveStudentToDR(player1, cl);
        //move the students from cloud to WR
        assertTrue(b.chooseCloud(player1, 0));

    }

    public void testMoveStudentToDR() {
    }

    public void testMoveStudentToIsland() {
    }

    public void testPlayCard() {
    }

    public void testIsWinningPosition() {
    }

    public void testIsWonByResources() {
    }
}