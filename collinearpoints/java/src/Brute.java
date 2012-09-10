/*
 * Aug 31, 2012
 * 1:30:44 PM
 */

/**
 *
 * @author oleg.chumakov
 */
import java.util.Comparator;

public class Brute {

  private Point[][] alreadyPrinted;
  private int totalPrinted = 0;
  private Comparator currentComparer = null;

  private void merge(Comparable[] a, Comparable[] aux, int lo, int mid, int hi) {
    for (int k = lo; k <= hi; k++) {
      aux[k] = a[k];
    }
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
    }
  }

  private void sort(Comparable[] a) {
    int N = a.length;
    Comparable[] aux = new Comparable[N];
    for (int sz = 1; sz < N; sz = sz + sz) {
      for (int lo = 0; lo < N - sz; lo += sz + sz) {
        merge(a, aux, lo, lo + sz - 1, Math.min(lo + sz + sz - 1, N - 1));
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
      for (int j = 0; j < arr.length; j++) {
        if (arr[j] == alreadyPrinted[i][j]) {
          sameElements++;
        }
      }
      if (sameElements == arr.length) {
        return true;
      }
    }
    return false;
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
    alreadyPrinted = new Point[N * N][];

    sort(points);
    StdDraw.setXscale(0, 32768);
    StdDraw.setYscale(0, 32768);
    for (int i1 = 0; i1 < points.length; i1++) {
      for (int i2 = 0; i2 < points.length; i2++) {
        if (i1 == i2) {
          continue;
        }
        double firstSlope = points[i1].slopeTo(points[i2]);

        for (int i3 = 0; i3 < points.length; i3++) {
          if (i1 == i3 || i2 == i3) {
            continue;
          }
          if (firstSlope == points[i1].slopeTo(points[i3])) {

            for (int i4 = 0; i4 < points.length; i4++) {
              if (i1 == i4 || i2 == i4 || i3 == i4) {
                continue;
              }
              if (firstSlope == points[i1].slopeTo(points[i4])) {
                //Founded
                Point[] arr = new Point[4];
                arr[0] = points[i1];
                arr[1] = points[i2];
                arr[2] = points[i3];
                arr[3] = points[i4];
                sort(arr);

                if (isAlreadyPrinted(arr)) {
                  continue;
                }
                points[i1].drawTo(points[i4]);
                savePrinted(arr);
                System.out.println(pointsToString(arr));
              }
            }
          }

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
    Brute b = new Brute();
    b.detect(points);
  }
}
