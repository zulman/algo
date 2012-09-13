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
  private Board board;
  private int N = 3;
  private int[][] tiles;

  @Before
  public void setUp() {
    int N = 3;
    tiles = new int[N][N];
    for (int i = 0; i < tiles.length; i++) {
      for (int j = 0; j < tiles[i].length; j++) {
        tiles[i][j] = i * N + j + 1;
      }
    }

    tiles[N - 1][N - 1] = 0;
    board = new Board(tiles);
  }

  /**
   * Test of dimension method, of class Board.
   */
  @Test
  public void dimension_must_be_valid() {
    assertEquals(N, board.dimension());
  }

  /**
   * Test of hamming method, of class Board.
   */
  @Test
  public void hamming_distance_of_solved_board_must_be_zero() {
    assertEquals(0, board.hamming());
  }

  /**
   * Test of hamming method, of class Board.
   */
  @Test
  public void manhattan() {
    assertEquals(0, board.manhattan());

    tiles[0][0] = 8;
    tiles[0][1] = 1;
    tiles[0][2] = 3;

    tiles[1][0] = 4;
    tiles[1][1] = 0;
    tiles[1][2] = 2;

    tiles[2][0] = 7;
    tiles[2][1] = 6;
    tiles[2][2] = 5;

    Board tempBoard = new Board(tiles);
//    System.out.println("Manhattan Initial " + tempBoard);
    assertEquals(10, tempBoard.manhattan());


//    System.out.println((4 - 1) % 3);
//    System.out.println((5 - 1) % 3);
//    System.out.println((6 - 1) % 3);
//
//    System.out.println((1 - 1) / 3);
//    System.out.println((2 - 1) / 3);
//    System.out.println((3 - 1) / 3);
  }

  /**
   * Test of isGoal method, of class Board.
   */
  @Test
  public void solved_board_must_be_marked_as_goal() {
    assertEquals(true, board.isGoal());
  }

  /**
   * Test of twin method, of class Board.
   */
  @Test
  public void twin_must_create_deep_equal_board() {
    assertEquals(true, board.twin().equals(board));
    assertEquals(true, board.equals(board));
  }

  /**
   * Test of neighbors method, of class Board.
   */
  @Test
  public void neighbor_must_return_only_possible_boards() {
    tiles[N - 1][N - 1] = 5;
    tiles[1][1] = 0;

    Board tempBoard = new Board(tiles);
//    System.out.println("Initial " + tempBoard);
    int count = 0;
    for (Board b : tempBoard.neighbors()) {
      assertEquals(false, b.isGoal());
//      System.out.println("Neighbor: " + b);
      count++;
    }
    assertEquals(4, count);

    tiles[1][1] = 5;
    tiles[N - 1][N - 1] = 1;
    tiles[0][0] = 0;

    tempBoard = new Board(tiles);
//    System.out.println("Initial " + tempBoard);
    count = 0;
    for (Board b : tempBoard.neighbors()) {
      assertEquals(false, b.isGoal());
//      System.out.println("Neighbor: " + b);
      count++;
    }
    assertEquals(2, count);
  }
}
