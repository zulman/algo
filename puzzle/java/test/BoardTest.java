/*
 * Sep 12, 2012
 * 6:59:16 PM
 */

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author oleg.chumakov
 */
public class BoardTest {

  public BoardTest() {
  }

  @Before
  public void setUp() {
//    Integer a = 1;
//    Integer b = 1;
//    testFunction(a, b);
//    System.out.println(a + " " + b);
  }
//
//  private void testFunction(Integer a, Integer b) {
//    a.i = -1;
//    b = -1;
//  }

  /**
   * Test of dimension method, of class Board.
   */
  @Test
  public void testDimension() {
    System.out.println("dimension");
    Board instance = null;
    int expResult = 0;
    int result = instance.dimension();
    assertEquals(expResult, result);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of hamming method, of class Board.
   */
  @Test
  public void testHamming() {
    System.out.println("hamming");
    Board instance = null;
    int expResult = 0;
    int result = instance.hamming();
    assertEquals(expResult, result);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of manhattan method, of class Board.
   */
  @Test
  public void testManhattan() {
    System.out.println("manhattan");
    Board instance = null;
    int expResult = 0;
    int result = instance.manhattan();
    assertEquals(expResult, result);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of isGoal method, of class Board.
   */
  @Test
  public void testIsGoal() {
    System.out.println("isGoal");
    Board instance = null;
    boolean expResult = false;
    boolean result = instance.isGoal();
    assertEquals(expResult, result);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of twin method, of class Board.
   */
  @Test
  public void testTwin() {
    System.out.println("twin");
    Board instance = null;
    Board expResult = null;
    Board result = instance.twin();
    assertEquals(expResult, result);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of equals method, of class Board.
   */
  @Test
  public void testEquals() {
    System.out.println("equals");
    Object y = null;
    Board instance = null;
    boolean expResult = false;
    boolean result = instance.equals(y);
    assertEquals(expResult, result);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of neighbors method, of class Board.
   */
  @Test
  public void testNeighbors() {
    System.out.println("neighbors");
    Board instance = null;
    Iterable expResult = null;
    Iterable result = instance.neighbors();
    assertEquals(expResult, result);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of toString method, of class Board.
   */
  @Test
  public void testToString() {
    System.out.println("toString");
    Board instance = null;
    String expResult = "";
    String result = instance.toString();
    assertEquals(expResult, result);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }
}
