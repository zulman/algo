/* 7:28:18 PM Aug 15, 2012 */

/**
 *
 * @author oleg.chumakov
 */
public class Percolation {

  private boolean[] _sites;
  private WeightedQuickUnionUF _union;
  private int _size;
  private int _unionSize;

//  private final int _topSite = 0;
//  private final int _bottomSite;
  /**
   * Creates N-by-N grid, with all sites blocked
   * @param N
   */
  public Percolation(int N) {
    /**
     * +2 for top/bottom virtual sites
     */
//    _unionSize = N * N + 2;
    _unionSize = N * N;
    _union = new WeightedQuickUnionUF(_unionSize);
    _sites = new boolean[N * N];
    _size = N;
//    _bottomSite = _unionSize - 1;
//    connectVirtualSites();
  }

  /**
   * Checks that site is open
   * @param i row
   * @param j column
   * @return  
   */
  public final boolean isOpen(int i, int j) {
    if (!checkIndexesAndAlert(i - 1, j - 1)) {
      return false;
    }
    return _sites[getFlatIndex(i - 1, j - 1)];
  }

  /**
   * Using for connect top and virtual sites with top and bottom row elements 
   * respectively
   * @param i 
   * @param j
   * @return  
   */
//  private void connectVirtualSites() {
//    for (int i = 0; i < _size; i++) {
//      _union.union(_topSite, i + 1);
//      _union.union(_bottomSite, _unionSize - 2 - i);
//    }
//  }
  /**
   * Checks that site is full
   * @param i row
   * @param j column
   * @return  
   */
  public boolean isFull(int i, int j) {
    if (!checkIndexesAndAlert(i - 1, j - 1)) {
      return false;
    }
    int flatIndex = getFlatIndex(i - 1, j - 1);

    if (getSiteStatus(flatIndex)) {
      for (int k = 0; k < _size; k++) {
        if (_union.connected(k, flatIndex)) {
          return true;
        }
      }
    }

    return false;
  }

  /**
   * Checks that system is percolates
   * @return
   */
  public boolean percolates() {

    for (int i = 0; i < _size; i++) {
      for (int j = 0; j < _size; j++) {
        if (_union.connected(i, _unionSize - 1 - j)) {
          return true;
        }
      }
    }
    return false;
//    return _union.connected(_topSite, _bottomSite);
  }

  /**
   * open site if it is not already
   * @param i row
   * @param j column
   */
  public void open(int i, int j) {
    if (!checkIndexesAndAlert(i - 1, j - 1)) {
      return;
    }
    int flatIndex = getFlatIndex(i - 1, j - 1);

    if (!_sites[flatIndex]) {
      _sites[flatIndex] = true;

      int fmod = flatIndex % _size;

      //left neighbor
      if (fmod != 0 && getSiteStatus(flatIndex - 1)) {
        tryToUnion(flatIndex, flatIndex - 1);
      }
      //right neighbor
      if (fmod != _size - 1 && getSiteStatus(flatIndex + 1)) {
        tryToUnion(flatIndex, flatIndex + 1);
      }
      //top neighbor
      if (getSiteStatus(flatIndex - _size)) {
        tryToUnion(flatIndex, flatIndex - _size);
      }
      //bottom neighbor
      if (getSiteStatus(flatIndex + _size)) {
        tryToUnion(flatIndex, flatIndex + _size);
      }
    }
  }

  /**
   * Get site status or false if index out of array bounds
   */
  private boolean getSiteStatus(int index) {
    if (index >= 0 && index < _sites.length) {
      return _sites[index];
    }
    return false;
  }

  /**
   * Performs union of elements if their indexes are in bounds
   */
  private boolean tryToUnion(int p, int q) {
    if (p >= _unionSize || q >= _unionSize || p < 0 || q < 0) {
      return false;
    }

    _union.union(p, q);
    return true;
  }

  /*
   * Checks that indexes represent item inside array _sizex_size
   */
  private boolean checkIndexesAndAlert(int i, int j) {
    if (i >= _size || j >= _size) {
      return false;
    }
    return true;
  }

  /*
   * Get index of flat 2D array representation. 
   * @param i row
   * @param j column
   */
  private int getFlatIndex(int i, int j) {
    return i * _size + j;
  }
}
