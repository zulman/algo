/*
 * Sep 11, 2012
 * 9:15:29 AM
 */

/**
 *
 * @author oleg.chumakov
 */
public class Board {

  private enum NeighborDirection {

    RIGHT, LEFT, UP, DOWN
  }

  private class IntVec2 {

    public int x;
    public int y;

    public IntVec2(int x, int y) {
      this.x = x;
      this.y = y;
    }

    public IntVec2(IntVec2 other) {
      this.x = other.x;
      this.y = other.y;
    }

    public IntVec2(int bothValues) {
      this.x = bothValues;
      this.y = bothValues;
    }

    public void set(int bothValues) {
      this.x = bothValues;
      this.y = bothValues;
    }
  }
  private final int INVALID_VALUE = -1;
  private final int EMPTY_SECTION = 0;
  private int N;
  private int[][] tiles;
  private IntVec2 zero = new IntVec2(INVALID_VALUE);
  private int hammingCached = INVALID_VALUE;

  /**
   * construct a board from an N-by-N array of blocks (where blocks[i][j] =
   * block in row i, column j)
   *
   * @param blocks
   */
  public Board(int[][] blocks) {
    N = blocks.length;
    tiles = copyArray(blocks);

    for (int i = 0; i < N; i++) {
      for (int j = 0; j < N; j++) {
        if (tiles[i][j] == 0) {
          zero.x = i;
          zero.y = j;
        }
      }
    }
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
    if (hammingCached != INVALID_VALUE) {
      return hammingCached;
    }
    int result = 0;
    for (int i = 0; i < N; i++) {
      for (int j = 0; j < N; j++) {
        if (tiles[i][j] == EMPTY_SECTION) {
          continue;
        }
        if (tiles[i][j] != i * N + j + 1) {
          result++;
        }
      }
    }
    hammingCached = result;
    return result;
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
    return (hamming() == 0);
  }

  /**
   * a board obtained by exchanging two adjacent blocks in the same row
   *
   * @return
   */
  public Board twin() {
    return new Board(tiles);
  }

  /**
   * does this board equal y?
   *
   * @param y
   * @return
   */
  @Override
  public boolean equals(Object y) {
    if (y == null) {
      return false;
    }
    if (y.getClass() != Board.class) {
      return false;
    }
    Board that = (Board) y;
    if (that.N != N) {
      return false;
    }

    if (that.zero.x != zero.x || that.zero.y != zero.y) {
      return false;
    }

    for (int i = 0; i < tiles.length; i++) {
      for (int j = 0; j < tiles[i].length; j++) {

        if (that.tiles[i][j] != tiles[i][j]) {
          return false;
        }
      }
    }

    return true;
  }

  /**
   * all neighboring boards
   *
   * @return
   */
  public Iterable<Board> neighbors() {
    Stack<Board> result = new Stack<Board>();

    IntVec2 right = getNeighborIndex(N, zero, NeighborDirection.RIGHT);
    IntVec2 left = getNeighborIndex(N, zero, NeighborDirection.LEFT);
    IntVec2 up = getNeighborIndex(N, zero, NeighborDirection.UP);
    IntVec2 down = getNeighborIndex(N, zero, NeighborDirection.DOWN);

    if (right.x != INVALID_VALUE) {
      result.push(createTwinWithPermutation(right));
    }
    if (left.x != INVALID_VALUE) {
      result.push(createTwinWithPermutation(left));
    }
    if (up.x != INVALID_VALUE) {
      result.push(createTwinWithPermutation(up));
    }
    if (down.x != INVALID_VALUE) {

      result.push(createTwinWithPermutation(down));
    }

    return result;
  }

  private Board createTwinWithPermutation(IntVec2 target) {
    Board result = twin();
    swap(result.tiles, result.zero, target);
    return result;
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

  private int[][] copyArray(int[][] base) {
    int[][] result = new int[N][];
    for (int i = 0; i < base.length; i++) {
      result[i] = new int[base[i].length];
      System.arraycopy(base[i], 0, result[i], 0, base[i].length);
    }
    return result;
  }

  private void swap(int[][] arr, IntVec2 first, IntVec2 second) {
    int temp = arr[first.x][first.y];
    arr[first.x][first.y] = arr[second.x][second.y];
    arr[second.x][second.y] = temp;
  }

  private IntVec2 getNeighborIndex(int N, IntVec2 index, NeighborDirection direction) {

    IntVec2 result = new IntVec2(index);
    if (direction == NeighborDirection.RIGHT && index.y != (N - 1)) {
      result.y++;
      return result;
    } else if (direction == NeighborDirection.LEFT && index.y != 0) {
      result.y--;
      return result;
    } else if (direction == NeighborDirection.UP && index.x != 0) {
      result.x--;
      return result;
    } else if (direction == NeighborDirection.DOWN && index.x != (N - 1)) {
      result.x++;
      return result;
    }
    result.set(INVALID_VALUE);
    return result;
  }
}
