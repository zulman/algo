/*
 * Sep 11, 2012
 * 9:15:29 AM
 */

/**
 *
 * @author oleg.chumakov
 */
public class Board {

//  private enum NeighborDirection {
//
//    RIGHT, LEFT, UP, DOWN
//  }
//  private class IntVec2 {
//
//    private int x;
//    private int y;
//
//    public IntVec2(int x, int y) {
//      this.x = x;
//      this.y = y;
//    }
//
//    public IntVec2(IntVec2 other) {
//      this.x = other.x;
//      this.y = other.y;
//    }
//
//    public IntVec2(int bothValues) {
//      this.x = bothValues;
//      this.y = bothValues;
//    }
//
//    public void set(int bothValues) {
//      this.x = bothValues;
//      this.y = bothValues;
//    }
//
//    @Override
//    public String toString() {
//      return (this.x + ":" + this.y);
//    }
//  }
  private static final byte RIGHT = 0;
  private static final byte LEFT = 1;
  private static final byte UP = 2;
  private static final byte DOWN = 3;
  private static final byte INVALID_VALUE = -1;
  private static final byte EMPTY_SECTION = 0;
  private int N;
  private char[][] tiles;
//  private IntVec2 zero = new IntVec2(INVALID_VALUE);
  private int zeroI = INVALID_VALUE;
  private int zeroJ = INVALID_VALUE;
  private int hammingCached = INVALID_VALUE;
  private int manhattanCached = INVALID_VALUE;
  
  /**
   * Using to avoid IntVec2 instantiating
   * because of memory usage restrictions
   */
  private int resulti = 0;
  private int resultj = 0;

  /**
   * construct a board from an N-by-N array of blocks (where blocks[i][j] =
   * block in row i, column j)
   *
   * @param blocks
   */
  public Board(int[][] blocks) {
    N = blocks.length;
    tiles = copyArray(blocks);
    boolean founded = false;
    for (int i = 0; i < N && !founded; i++) {
      for (int j = 0; j < N && !founded; j++) {
        if (tiles[i][j] == 0) {
          zeroI = i;
          zeroJ = j;
          founded = true;
        }
      }
    }
  }

  private Board(char[][] blocks, int i, int j) {
    N = blocks.length;
    tiles = copyArray(blocks);

    this.zeroI = i;
    this.zeroJ = j;
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
    if (manhattanCached != INVALID_VALUE) {
      return manhattanCached;
    }
    int result = 0;
    for (int i = 0; i < N; i++) {
      for (int j = 0; j < N; j++) {
        if (tiles[i][j] == EMPTY_SECTION) {
          continue;
        }
        if (tiles[i][j] != i * N + j + 1) {
          int val = tiles[i][j];
          //Column
          int ry = (val - 1) % N;
          //Row
          int rx = (val - 1) / N;
          int delta = Math.abs(i - rx) + Math.abs(j - ry);
          result += delta;
        }
      }
    }

    manhattanCached = result;
    return result;
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
    Board result = new Board(tiles, zeroI, zeroJ);
    int i = 0;
    if (zeroI == i) {
      i++;
    }

    result.swap(result.tiles, i, 0, i, 1);
    return result;
  }

  private Board cloneBoard() {
    return new Board(tiles, zeroI, zeroJ);
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
    if (!(y instanceof Board)) {
      return false;
    }
    Board that = (Board) y;

    if (that.manhattan() != manhattan()) {
      return false;
    }

    if (that.N != N) {
      return false;
    }

    if (that.zeroI != zeroI || that.zeroJ != zeroJ) {
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

    for (int i = RIGHT; i <= DOWN; i++) {
      if (getNeighborIndex(zeroI, zeroJ, i)) {
        result.push(createCloneWithPermutation(resulti, resultj));
      }
    }

    return result;
  }

  private Board createCloneWithPermutation(int i, int j) {
    Board result = cloneBoard();
    swap(result.tiles, result.zeroI, result.zeroJ, i, j);
    result.zeroI = i;
    result.zeroJ = j;
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
        s.append(String.format("%2d ", (int) tiles[i][j]));
      }
      s.append("\n");
    }
    return s.toString();
  }

  private char[][] copyArray(int[][] base) {
    char[][] result = new char[N][];
    for (int i = 0; i < base.length; i++) {
      result[i] = new char[base[i].length];
      for (int inneridx = 0; inneridx < result[i].length; inneridx++) {
        result[i][inneridx] = (char) base[i][inneridx];
      }
    }
    return result;
  }

  private char[][] copyArray(char[][] base) {
    char[][] result = new char[N][];
    for (int i = 0; i < base.length; i++) {
      result[i] = new char[base[i].length];
      System.arraycopy(base[i], 0, result[i], 0, base[i].length);
    }
    return result;
  }

  private void swap(char[][] arr, int firsti, int firstj, int secondi, int secondj) {
    char temp = arr[firsti][firstj];
    arr[firsti][firstj] = arr[secondi][secondj];
    arr[secondi][secondj] = temp;
  }

  /**
   * Calculates permutation index for neighbor board.
   * Bery strange behaviour of result saving:
   * indexes will be saved to private vars resulti and resultj.
   * @param i
   * @param j
   * @param direction
   * @return is neighbor of this direction available?
   */
  private boolean getNeighborIndex(int i, int j,
          int direction) {

    resulti = i;
    resultj = j;
//    IntVec2 result = new IntVec2(index);
    if (direction == RIGHT && j != (N - 1)) {
      resultj++;
      return true;
    } else if (direction == LEFT && j != 0) {
      resultj--;
      return true;
    } else if (direction == UP && i != 0) {
      resulti--;
      return true;
    } else if (direction == DOWN && i != (N - 1)) {
      resulti++;
      return true;
    }
    resulti = INVALID_VALUE;
    resultj = INVALID_VALUE;
    return false;
  }
}
