/*
 * Sep 11, 2012
 * 9:15:29 AM
 */

/**
 *
 * @author oleg.chumakov
 */
public class Board {

  private int N;
  private int[][] tiles;

  /**
   * construct a board from an N-by-N array of blocks (where blocks[i][j] =
   * block in row i, column j)
   *
   * @param blocks
   */
  public Board(int[][] blocks) {
    N = blocks.length;
    tiles = blocks;
  }

  /**
   * board dimension N
   *
   * @return
   */
  public int dimension() {
    return N;
  }

  /**
   * number of blocks out of place
   *
   * @return
   */
  public int hamming() {
    return 0;
  }

  /**
   * sum of Manhattan distances between blocks and goal
   *
   * @return
   */
  public int manhattan() {
    return 0;
  }

  /**
   * is this board the goal board?
   *
   * @return
   */
  public boolean isGoal() {
    return false;
  }

  /**
   * a board obtained by exchanging two adjacent blocks in the same row
   *
   * @return
   */
  public Board twin() {
    return null;
  }

  /**
   * does this board equal y?
   *
   * @param y
   * @return
   */
  @Override
  public boolean equals(Object y) {
    return false;
  }

  /**
   * all neighboring boards
   *
   * @return
   */
  public Iterable<Board> neighbors() {
    return null;
  }

  /**
   * string representation of the board (in the output format specified below)
   *
   * @return
   */
  @Override
  public String toString() {
    StringBuilder s = new StringBuilder();
    s.append(N + "\n");
    for (int i = 0; i < N; i++) {
      for (int j = 0; j < N; j++) {
        s.append(String.format("%2d ", tiles[i][j]));
      }
      s.append("\n");
    }
    return s.toString();
  }
}
