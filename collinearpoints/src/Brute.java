/*
 * Aug 31, 2012
 * 1:30:44 PM
 */

/**
 *
 * @author oleg.chumakov
 */
public class Brute {

  private static Comparable[] aux;
  private static Point[][] alreadyPrinted;
  private static int totalPrinted = 0;

//  private static void merge(Comparable[] a, int lo, int mid, int hi) {
//    int i = lo, j = mid + 1;
//    for (int k = lo; k <= hi; k++) {
//      if (i > mid) {
//        aux[k] = a[j++];
//      } else if (j > hi) {
//        aux[k] = a[i++];
//      } else if (less(a[j], a[i])) {
//        aux[k] = a[j++];
//      } else {
//        aux[k] = a[i++];
//      }
//    }
//  }

  private static boolean less(Comparable v, Comparable w) {
    return v.compareTo(w) < 0;
  }

  public static void sort(Comparable[] a) {
    int N = a.length;
    for (int i = 0; i < N; i++) {
      for (int j = i; j > 0; j--) {
        if (less(a[j], a[j - 1])) {
          exch(a, j, j - 1);
        } else {
          break;
        }
      }
    }
  }

  private static void exch(Comparable[] a, int i, int j) {
    Comparable swap = a[i];
    a[i] = a[j];
    a[j] = swap;
  }
//  public static void sort(Comparable[] a) {
//    int N = a.length;
//    aux = new Comparable[N];
//    for (int sz = 1; sz < N; sz = sz + sz) {
//      for (int lo = 0; lo < N - sz; lo += sz + sz) {
//        merge(a, lo, lo + sz - 1, Math.min(lo + sz + sz - 1, N - 1));
//      }
//    }
//  }

  public static String pointsToString(Point... points) {
    int i = 0;
    String result = "";
    for (Point p : points) {
      if (i == 0) {
        result = p.toString();
      } else {
        result += " -> " + p.toString();
      }
      i++;
    }
    return result;
  }

//   public static String pointsToString(Point[] points) {
//    int i = 0;
//    String result = "";
//    for (Point p : points) {
//      if (i == 0) {
//        result = p.toString();
//      } else {
//        result += " -> " + p.toString();
//      }
//      i++;
//    }
//    return result;
//  }
  private static boolean isAlreadyPrinted(Point[] arr) {

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

  public static void main(String[] args) {
    In in = new In(args[0]);      // input file
    int N = in.readInt();         // N-by-N percolation system
    System.out.println(N);
    Point[] points = new Point[N];
    alreadyPrinted = new Point[N * N][];

    int i = 0;
    while (!in.isEmpty()) {
      int x = in.readInt();
      int y = in.readInt();
      points[i] = new Point(x, y);
      if (i != 0) {
        points[i].drawTo(points[i - 1]);
      }
      i++;
    }
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
                alreadyPrinted[totalPrinted++] = arr;
                System.out.println(pointsToString(arr));
              }
            }
          }

        }
      }
    }
  }
}
