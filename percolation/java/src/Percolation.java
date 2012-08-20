/* 7:28:18 PM Aug 15, 2012 */

/**
 *
 * @author oleg.chumakov
 */
public class Percolation {

  private boolean[] sites;
  private WeightedQuickUnionUF fullness;
  private WeightedQuickUnionUF percolation;
  private final int ROW_SIZE;
  private final int GRAPH_SIZE;
  private final int TOP_SITE_INDEX;
  private final int BOTTOM_SITE_INDEX;

  /**
   * Creates N-by-N grid, with all sites blocked
   * @param N
   */
  public Percolation(int N) {
    /**
     * +2 for top/bottom virtual sites
     */
    GRAPH_SIZE = N * N + 2;
//    unionSize = N * N;
    percolation = new WeightedQuickUnionUF(GRAPH_SIZE);
    sites = new boolean[N * N];
    fullness = new WeightedQuickUnionUF(GRAPH_SIZE);
    ROW_SIZE = N;
    TOP_SITE_INDEX = GRAPH_SIZE - 2;
    BOTTOM_SITE_INDEX = GRAPH_SIZE - 1;
  }

  /**
   * Checks that site is open
   * @param i row
   * @param j column
   * @return  
   */
  public boolean isOpen(int i, int j) {
    checkIndexes(i - 1, j - 1);
    return sites[getFlatIndex(i - 1, j - 1)];
  }

  /**
   * Checks that site is full
   * @param i row
   * @param j column
   * @return  
   */
  public boolean isFull(int i, int j) {
    checkIndexes(i - 1, j - 1);
    int flatIndex = getFlatIndex(i - 1, j - 1);

    if (isSiteOpen(flatIndex)) {
      return fullness.connected(flatIndex, TOP_SITE_INDEX);
    }

    return false;
  }

  /**
   * Checks that system is percolates
   * @return
   */
  public boolean percolates() {
    return percolation.connected(TOP_SITE_INDEX, BOTTOM_SITE_INDEX);
  }

  /**
   * open site if it is not already
   * @param i row
   * @param j column
   */
  public void open(int i, int j) {
    checkIndexes(i - 1, j - 1);
//      return;
//    }
    int flatIndex = getFlatIndex(i - 1, j - 1);

    if (!sites[flatIndex]) {
      sites[flatIndex] = true;

      if (flatIndex >= 0 && flatIndex < ROW_SIZE) {
        tryToUnionInBothGraphs(flatIndex, TOP_SITE_INDEX);
      }

      if (flatIndex >= sites.length - ROW_SIZE && flatIndex < sites.length) {
        percolation.union(BOTTOM_SITE_INDEX, flatIndex);
      }

      int fmod = flatIndex % ROW_SIZE;

      //left neighbor
      if (fmod != 0 && isSiteOpen(flatIndex - 1)) {
        tryToUnionInBothGraphs(flatIndex, flatIndex - 1);
      }
      //right neighbor
      if (fmod != ROW_SIZE - 1 && isSiteOpen(flatIndex + 1)) {
        tryToUnionInBothGraphs(flatIndex, flatIndex + 1);
      }
      //top neighbor
      if (isSiteOpen(flatIndex - ROW_SIZE)) {
        tryToUnionInBothGraphs(flatIndex, flatIndex - ROW_SIZE);
      }
      //bottom neighbor
      if (isSiteOpen(flatIndex + ROW_SIZE)) {
        tryToUnionInBothGraphs(flatIndex, flatIndex + ROW_SIZE);
      }
    }
  }

  /**
   * Get site status or false if index out of array bounds
   */
  private boolean isSiteOpen(int index) {
    if (index >= 0 && index < sites.length) {
      return sites[index];
    }
    return false;
  }

  /**
   * Performs union of elements if their indexes are in bounds
   */
  private boolean tryToUnionInBothGraphs(int p, int q) {
    if (p >= GRAPH_SIZE || q >= GRAPH_SIZE || p < 0 || q < 0) {
      return false;
    }
    fullness.union(p, q);
    percolation.union(p, q);
    return true;
  }

  /*
   * Checks that indexes represent item inside array SizexSize
   */
  private void checkIndexes(int i, int j) {
    if (i >= ROW_SIZE || j >= ROW_SIZE || i < 0 || j < 0) {
      throw new IndexOutOfBoundsException(String.format("Maximum index must be [%s;%s] but trying to get [%s;%s]", ROW_SIZE, ROW_SIZE, i, j));
    }
  }

  /*
   * Get index of flat 2D array representation. 
   * @param i row
   * @param j column
   */
  private int getFlatIndex(int i, int j) {
    return i * ROW_SIZE + j;
  }
}
