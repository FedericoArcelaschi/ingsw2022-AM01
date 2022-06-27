package it.polimi.ingsw.server.model.baseLogic;

import it.polimi.ingsw.communication.modelData.ModelDataBuilder;
import it.polimi.ingsw.server.model.baseLogic.*;
import it.polimi.ingsw.server.model.exceptions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.random.RandomGenerator;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {

    private final String player1 = "a", player2= "2";
    private Board b;
    private Turn t;
    private final int seed = 1;

    @BeforeEach
    void setUp() {
        List<String> listOfPlayer = Arrays.asList(player1, player2);
        t = new Turn(listOfPlayer);
        b =  BoardFactory.getBoard(listOfPlayer, false);
    }

    void turnSetUpStudents() throws PhaseNotRightException {
        b.getTurn().addCard(player1, new Card(1));
        b.getTurn().changePhase();
        b.getTurn().addCard(player2, new Card(2));
        b.getTurn().changePhase();
        //got through planning phase for two players
    }

    void turnSetUpMN() throws PhaseNotRightException {
        turnSetUpStudents();
        //got through planning phase for two players
        b.getTurn().changePhase();
        //here turnphase is MOTHERNATURE
    }

    void turnSetUpCloud() throws PhaseNotRightException {
        turnSetUpMN();
        b.getTurn().changePhase();
        //now it's chooseclouod
    }
    @Test
    public void testBoardIslandNumber() {

        assertEquals(12, b.getIslandList().size());
    }

    @Test
    public void testGetAvailableCard() throws PhaseNotRightException {
        assertEquals(10, b.getCastle(player1).getDeck().stream().filter(card -> card.isAvailable()).count());
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
        assertEquals(0, b.getCastle(player1).getDeck().stream().filter(card -> card.isAvailable()).count());
    }

    @Test
    public void testNotYourTurnException(){
        assertDoesNotThrow(() -> b.playCard(player1,1), "player should be able to move because it's his turn");
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
        //assertEquals(3, cl2.get(0).getSize());
        //assertEquals(4, cl3.get(0).getSize());
        //assertEquals(3, cl4.get(0).getSize());
    }
    @Test
    public void testResetClouds() {
        b.cloudRefill();
    }

    @Test
    public void testChooseCloud() throws NoSuchStudentException, TooManyStudentsException, PhaseNotRightException {
        turnSetUpStudents();
        List<StudentColor> cl = new ArrayList<>();
        //move 4 element to DR to free space for new students coming from cloud
        for(int i=0; i<4;i++){
            cl.add(b.getCastleMap().get(player1).getWaitingRoom().get(i));
        }
        try {
            b.moveStudentsToDiningRoom(player1, cl);
        } catch (PhaseNotRightException e) {
            throw new RuntimeException(e);
        }
        b.getTurn().changePhase();
        //System.out.println(b.);
        b.getTurn().changePhase();
        //move the students from cloud to WR
        List<StudentColor> cloud = b.getCloudList().get(0).getStudentList();
        try {
            b.chooseCloud("a", 0);
        } catch (PhaseNotRightException e) {
            throw new RuntimeException(e);
        }
        if (!b.getCastle("a").getWaitingRoom().containsAll(cloud)) {
            fail();
        }
    }
    @Test
    public void testMoveStudentToIsland() throws NoSuchStudentException, PhaseNotRightException {
        turnSetUpStudents();
        List<StudentColor> studentColorList =  new ArrayList<>();
        studentColorList.add(b.getCastleMap().get(player1).getWaitingRoom().get(0));
        studentColorList.add(b.getCastleMap().get(player1).getWaitingRoom().get(1));
        Map<StudentColor, Integer> students = new HashMap<>();
        for(StudentColor c : StudentColor.values()) {
            students.put(c,0);
        }
        for(StudentColor c : studentColorList) {
            students.replace(c, students.get(c) + 1);
        }
        //test if the method returns correctly
        try {
            b.moveStudentToIsland(player1, 0, studentColorList);
        } catch (PhaseNotRightException e) {
            throw new RuntimeException(e);
        }
        assertEquals(students, b.getIslandList().get(0).getStudents());
        //test if the student get removed from castle waiting room
        assertEquals(7 - studentColorList.size(),
                b.getCastle(player1).getWaitingRoom().size(),
                studentColorList.size() + " students should have been removed from the waiting room, but " +
                        (7 - b.getCastle(player1).getWaitingRoom().size()) + "students were removed");
        //test if the student get added to the island
        Map<StudentColor, Integer> studentsOnIsland = b.getIslandList().get(0).getStudents();
        assertEquals(students, studentsOnIsland);
    }
    @Test
    public void testPlayCard() throws PhaseNotRightException {
        //check if the card is not used at the beginning
        assertTrue(b.getCastleMap().get(player1).getDeck().get(0).isAvailable());
        //check if the card is played correctly
        assertTrue(b.getCastle(player1).getDeck().get(0).isAvailable());
        b.playCard(player1,1);
        assertFalse(b.getCastle(player1).getDeck().get(0).isAvailable());
        //check if last card played is the one we played
        assertEquals(1, b.getCastleMap().get(player1).getLastCardPlayed().priority());
        assertEquals(1, b.getCastleMap().get(player1).getLastCardPlayed().distance());
        //check if the card can't be reused
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
                    b.playCard(player1, 1);
                }, "IllegalArgumentException was expected");

        assertEquals("Card cannot be played. Card is already played and you have another card to play in your castle. You don't have this card in the castle.",
                thrown.getMessage());
    }
    @Test
    public void testUpdateProfessor() throws NoSuchStudentException, TooManyStudentsException, PhaseNotRightException {
        turnSetUpStudents();
        List<StudentColor> students = Arrays.asList(b.getCastleMap().get(player1).getWaitingRoom().get(0),b.getCastleMap().get(player1).getWaitingRoom().get(1));
        b.moveStudentsToDiningRoom(player1, students);

        //test professor get assigned
        Map<StudentColor,Team> pm1 = b.getProfessorsMap();
        for(StudentColor c : StudentColor.values()){
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
        assertFalse(b.isWonByResources());
    }

    @Test
    public void testMoveMotherNature() throws PhaseNotRightException {
        final Turn turn = new Turn(List.of("prova", "qwerty"));
        final Board board = new Board("prova", "qwerty", turn, RandomGenerator.getDefault().nextLong());
        board.playCard("prova", 3);
        turn.changePhase();
        board.playCard("qwerty", 10);
        turn.changePhase();
        //List<StudentColor> availableStudents = board.getCastle("prova").waitingRoom.subList(0, 3);
        //System.out.println(availableStudents);
        //board.moveStudentsToDiningRoom("prova", availableStudents);
        turn.changePhase();
        board.moveMotherNature(1);
        assertEquals(1, board.getMotherNaturePosition());
        System.out.println(ModelDataBuilder.newBoardData(board, board.getCurrentPlayer()));
    }

    /**
     * Test from blank board.
     * Joins 3 islands with three students and no owner.
     */
    @Test
    public void testJoinIsland(){
        List<Island> oldList = new ArrayList<>(b.getIslandList());
        Island islandA = b.getIslandList().get(1);
        Island islandB = b.getIslandList().get(2);
        Island islandC = b.getIslandList().get(3);
        b.joinIslands(List.of(1, 2, 3));
        assertEquals(oldList.get(0), b.getIslandList().get(0),
                "The first island should stay untouched");
        assertEquals(10, b.getIslandList().size(),
                "the island list should decrease by 2");
        assertEquals(3, b.getIslandList().get(1).getIslandNumber(),
                "the second island is the union of three");
        Map<StudentColor, Integer> EmptyStudentsMap = b.getIslandList().get(0).getStudents(); //first island is empty
        Map<StudentColor, Integer> expectedStudentsMap
                = new HashMap<>(EmptyStudentsMap);

        for (int i = 1; i < 4; i++) {
            for (StudentColor student: StudentColor.values()) {
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
        assertEquals(expectedStudentsMap, b.getIslandList().get(1).getStudents(),
                "The second island should have all the students as the islands before");
        assertEquals(oldList.get(4), b.getIslandList().get(2),
                "also the next island is untouched");
        assertEquals(oldList.get(1).getOwnership(), b.getIslandList().get(2).getOwnership(),
                "no owner should be present");
    }
}