/*
 * Aug 31, 2012
 * 1:30:44 PM
 */

/**
 *
 * @author oleg.chumakov
 */
import java.util.Comparator;
import java.util.Arrays;

public class Fast {

  private final int MIN_LINE_LENGTH = 3;
  private Point[][] alreadyPrinted;
  private Point[] alreadyPrinted2;
  private int totalPrinted = 0;
  private Comparator currentComparer = null;
  private Comparable currentSearchingItem = null;
  private int foundedIndex = -1;
  private Comparable[] aux;

//  private void merge(Comparable[] a, int lo, int mid, int hi) {
//    System.arraycopy(a, 0, aux, 0, a.length);
//    int i = lo, j = mid + 1;
//    for (int k = lo; k <= hi; k++) {
//      if (i > mid) {
//        a[k] = aux[j++];
//      } else if (j > hi) {
//        a[k] = aux[i++];
//      } else if (less(aux[j], aux[i])) {
//        a[k] = aux[j++];
//      } else {
//        a[k] = aux[i++];
//      }
//      
//      if (a[k] == currentSearchingItem) {
//        foundedIndex = k;
//      }
//    }
//  }
//  
//  private void msort(Comparable[] a) {
//    int N = a.length;
//    aux = new Comparable[N];
//    for (int sz = 1; sz < N; sz = sz + sz) {
//      for (int lo = 0; lo < N - sz; lo += sz + sz) {
//        merge(a, lo, lo + sz - 1, Math.min(lo + sz + sz - 1, N - 1));
//      }
//    }
//  }
//  private int partition(Comparable[] a, int lo, int hi) {
//    int i = lo, j = hi + 1;
//    while (true) {
//      while (less(a[++i], a[lo])) {
//        if (i == hi) {
//          break;
//        }
//      }
//      while (less(a[lo], a[--j])) {
//        if (j == lo) {
//          break;
//        }
//      }
//
//      if (i >= j) {
//        break;
//      }
//      exch(a, i, j);
//    }
//    exch(a, lo, j);
//    return j;
//  }
//
//  private void sort(Comparable[] a) {
//    StdRandom.shuffle(a);
//    sort(a, 0, a.length - 1);
//  }
//
//  private void sort(Comparable[] a, int lo, int hi) {
//
//    if (hi <= lo) {
//      return;
//    }
//    int lt = lo, gt = hi;
//    Comparable v = a[lo];
//    int i = lo;
//    while (i <= gt) {
//      int cmp;
//      if (currentComparer != null) {
//        cmp = currentComparer.compare(a[i], v);
//      } else {
//        cmp = a[i].compareTo(v);
//      }
//      if (cmp < 0) {
//        exch(a, lt++, i++);
//      } else if (cmp > 0) {
//        exch(a, i, gt--);
//      } else {
//        i++;
//      }
//    }
//    sort(a, lo, lt - 1);
//    sort(a, gt + 1, hi);
//
////    if (hi <= lo) {
////      return;
////    }
////    int j = partition(a, lo, hi);
////    sort(a, lo, j - 1);
////    sort(a, j + 1, hi);
//  }
//
//  private boolean less(Comparable v, Comparable w) {
//    if (currentComparer != null) {
//      return currentComparer.compare(v, w) < 0;
//    }
//    return v.compareTo(w) < 0;
//  }
  private void exch(Comparable[] a, int i, int j) {
    Comparable swap = a[i];
    a[i] = a[j];
    a[j] = swap;
  }

  private String pointsToString(Point[] points) {
    String result = "";
    for (int i = 0; i < points.length; i++) {
      if (i == 0) {
        result = points[i].toString();
      } else {
        result += " -> " + points[i].toString();
      }
    }
    return result;
  }

  private String pointsToString(Point[] points, int start, int end) {
    String result = "";
    for (int i = start; i <= end; i++) {
      if (i == start) {
        result = points[i].toString();
      } else {
        result += " -> " + points[i].toString();
      }
    }
    return result;
  }

  private boolean isAlreadyPrinted(Point p1, Point p2) {
    for (int i = 0; i < totalPrinted; i++) {

      if (alreadyPrinted[i][0] == p1 && alreadyPrinted[i][1] == p2) {
        return true;
      }
//      for (int j = 0; j < 2; j++) {
//        if (arr[j] == alreadyPrinted[i][j]) {
//          sameElements++;
//        }
//      }
//      if (sameElements == 2) {
//        return true;
//      }
    }
    return false;
  }

  private String pointSlopesToString(Point[] points, Point point) {
    String result = "";
    for (int i = 0; i < points.length; i++) {
      if (point == points[i]) {
        result += "[";
      }
      result += point.slopeTo(points[i]) + "_" + points[i].toString() + " ## ";
      if (point == points[i]) {
        result += "]";
      }
    }
    return result;
  }

  private void savePrinted(Point p1, Point p2) {
    if (alreadyPrinted.length <= totalPrinted) {
      Point[][] temp = new Point[alreadyPrinted.length * 2][];
      for (int i = 0; i < alreadyPrinted.length; i++) {
        temp[i] = alreadyPrinted[i];
      }
      alreadyPrinted = temp;
    }

    alreadyPrinted[totalPrinted] = new Point[2];
    alreadyPrinted[totalPrinted][0] = p1;
    alreadyPrinted[totalPrinted][1] = p2;
    totalPrinted++;
  }

  private void detect(Point[] points) {
    int N = points.length;
    if (N < MIN_LINE_LENGTH) {
      return;
    }
    alreadyPrinted = new Point[N * N][];
    StdDraw.setXscale(0, 32768);
    StdDraw.setYscale(0, 32768);
    Point[] tempPoints = new Point[N];
    System.arraycopy(points, 0, tempPoints, 0, points.length);
    Arrays.sort(tempPoints);
    for (int i1 = 0; i1 < points.length; i1++) {

      Point p = points[i1];
      Arrays.sort(tempPoints, p.SLOPE_ORDER);

      int indexStarted = 0;
      int indexEnded = 0;
      boolean seqBreaked;
      boolean containsP = false;
      int pIndex = 0;
      double slope = p.slopeTo(tempPoints[indexStarted]);
      for (int i2 = 1; i2 < tempPoints.length; i2++) {

        seqBreaked = false;
        if (tempPoints[i2] == p || p.slopeTo(tempPoints[i2]) == slope) {
          if (tempPoints[i2] == p) {
            pIndex = i2;
          }
          indexEnded++;

        } else {
          seqBreaked = true;
        }
        if (i2 == tempPoints.length - 1) {
          seqBreaked = true;
        }
        int length = indexEnded - indexStarted + 1;
        if (seqBreaked && length >= MIN_LINE_LENGTH) {

//          int pIndexN = pIndex;
//          if (pIndex >= indexStarted && pIndex <= indexEnded) {
//          } else {
//
//            if (indexEnded + 1 < tempPoints.length) {
//              indexEnded++;
//              exch(tempPoints, indexEnded, pIndex);
//            } else {
//              indexStarted--;
//              exch(tempPoints, indexStarted, pIndex);
//            }
//          }
//          Arrays.sort(tempPoints, indexStarted, indexEnded + 1);
//          for (int idx = indexStarted; idx <= indexEnded; idx++) {
//            if (tempPoints[idx] == p) {
//              pIndexN = idx;
//            }
//          }
          if (!isAlreadyPrinted(tempPoints[indexStarted], tempPoints[indexStarted + 1])) {
            savePrinted(tempPoints[indexStarted], tempPoints[indexStarted + 1]);
            System.out.println(pointsToString(tempPoints, indexStarted, indexEnded));
            tempPoints[indexStarted].drawTo(tempPoints[indexEnded]);
          }
//          exch(tempPoints, pIndexN, pIndex);
        }
        if (seqBreaked) {
          indexStarted = i2;
          indexEnded = indexStarted;
          slope = p.slopeTo(tempPoints[indexStarted]);
        }
      }
    }
  }

  public static void main(String[] args) {
    In in = new In(args[0]);      // input file
    int N = in.readInt();         // N-by-N percolation system
    Point[] points = new Point[N];

    int i = 0;
    while (!in.isEmpty()) {
      int x = in.readInt();
      int y = in.readInt();
      points[i] = new Point(x, y);
      i++;
    }

    Fast f = new Fast();
    f.detect(points);

  }
}
