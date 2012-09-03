/*
 * Aug 31, 2012
 * 1:30:44 PM
 */

/**
 *
 * @author oleg.chumakov
 */
import java.util.Comparator;

public class Fast {

  private final int MIN_LINE_LENGTH = 3;
  private Point[][] alreadyPrinted;
  private Point[] alreadyPrinted2;
  private int totalPrinted = 0;
  private Comparator currentComparer = null;
  private Comparable currentSearchingItem = null;
  private int foundedIndex = -1;
  Comparable[] aux;

  private void merge(Comparable[] a, int lo, int mid, int hi) {
    System.arraycopy(a, 0, aux, 0, a.length);
    int i = lo, j = mid + 1;
    for (int k = lo; k <= hi; k++) {
      if (i > mid) {
        a[k] = aux[j++];
      } else if (j > hi) {
        a[k] = aux[i++];
      } else if (less(aux[j], aux[i], currentComparer)) {
        a[k] = aux[j++];
      } else {
        a[k] = aux[i++];
      }

      if (a[k] == currentSearchingItem) {
        foundedIndex = k;
      }
    }
  }

  private void sort(Comparable[] a) {
    int N = a.length;
    aux = new Comparable[N];
    for (int sz = 1; sz < N; sz = sz + sz) {
      for (int lo = 0; lo < N - sz; lo += sz + sz) {
        merge(a, lo, lo + sz - 1, Math.min(lo + sz + sz - 1, N - 1));
      }
    }
  }

  private boolean less(Comparable v, Comparable w, Comparator c) {
    if (c != null) {
      return c.compare(v, w) < 0;
    }
    return v.compareTo(w) < 0;
  }

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

  private boolean isAlreadyPrinted(Point[] arr) {
    for (int i = 0; i < totalPrinted; i++) {

      int sameElements = 0;
      for (int j = 0; j < 2; j++) {
        if (arr[j] == alreadyPrinted[i][j]) {
          sameElements++;
        }
      }
      if (sameElements == 2) {
        return true;
      }
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

  private void savePrinted(Point[] arr) {
    if (alreadyPrinted.length <= totalPrinted) {
      Point[][] temp = new Point[alreadyPrinted.length * 2][];
      for (int i = 0; i < alreadyPrinted.length; i++) {
        temp[i] = alreadyPrinted[i];
      }
      alreadyPrinted = temp;
    }

    alreadyPrinted[totalPrinted++] = arr;
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
    currentComparer = null;
    sort(tempPoints);
    for (int i1 = 0; i1 < points.length; i1++) {
      Point p = points[i1];

      currentComparer = p.SLOPE_ORDER;
      currentSearchingItem = p;
      sort(tempPoints);
      System.out.println(pointSlopesToString(tempPoints, p));

//      int newIndex = i1;
//      if (foundedIndex != -1) {
//        newIndex = foundedIndex;
//      }
//      exch(tempPoints, 0, newIndex);

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

          Point[] line = null;
          //Inside line indexes
          if (pIndex >= indexStarted && pIndex <= indexEnded) {
            System.out.println("inside " + indexStarted + " " + indexEnded + " | " + pIndex);
            line = new Point[length];
            System.arraycopy(tempPoints, indexStarted, line, 0, length);
          } else if (pIndex > indexEnded) {
            System.out.println("end " + indexStarted + " " + indexEnded + " | " + pIndex);
            line = new Point[length + 1];
            System.arraycopy(tempPoints, indexStarted, line, 0, length);
            line[line.length - 1] = p;
          } else if (pIndex < indexStarted) {
            System.out.println("start " + indexStarted + " " + indexEnded + " | " + pIndex);
            line = new Point[length + 1];
            System.arraycopy(tempPoints, indexStarted, line, 1, length);
            line[0] = p;
          }
//          if (newIndex > indexEnded) {
//            System.arraycopy(tempPoints, indexStarted, line, 0, length);
//
//            line[line.length - 1] = p;
//          } else if (newIndex < indexStarted) {
//            System.arraycopy(tempPoints, indexStarted, line, 1, length);
//
//            line[0] = p;
//          }
//          currentComparer = null;
//          sort(line);
//line[0]
//        line[]
          if (!isAlreadyPrinted(line)) {
            savePrinted(line);
            System.out.println(pointsToString(line));
            line[0].drawTo(line[line.length - 1]);
          }
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
