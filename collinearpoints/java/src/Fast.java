/*
 * Aug 31, 2012
 * 1:30:44 PM
 */

/**
 *
 * @author oleg.chumakov
 */
import java.util.Arrays;

public class Fast {

  private final int MIN_LINE_LENGTH = 3;
  private double sum = 0;

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

  private static void printArray(double[] a) {
    for (int i = 0; i < a.length; i++) {
      System.out.print(a[i] + " ");
    }
    System.out.println();
  }

  private void detect(Point[] points) {
    int N = points.length;
    if (N < MIN_LINE_LENGTH) {
      return;
    }
    StdDraw.setXscale(0, 32768);
    StdDraw.setYscale(0, 32768);

    for (int pIndex = 0; pIndex < points.length - 1; pIndex++) {
      Point p = points[pIndex];

      Arrays.sort(points, pIndex + 1, points.length, p.SLOPE_ORDER);

      int indexStarted = pIndex + 1;
      int indexEnded = pIndex + 1;
      boolean seqBreaked;
      double slope = p.slopeTo(points[indexStarted]);
      for (int i2 = pIndex + 2; i2 < points.length; i2++) {
        seqBreaked = false;
        double cSlope = p.slopeTo(points[i2]);
        if (cSlope == slope) {
          indexEnded++;
        } else {
          seqBreaked = true;
        }
        if (i2 == points.length - 1) {
          seqBreaked = true;
        }

        int length = indexEnded - indexStarted + 1;
        if (seqBreaked && length >= MIN_LINE_LENGTH) {

          boolean alreadyPrinted = false;
          for (int j = 0; j < pIndex; j++) {
            if (points[indexStarted].slopeTo(points[j])
                    == points[indexStarted + 1].slopeTo(points[j])) {
              alreadyPrinted = true;
              break;
            }
          }

          if (!alreadyPrinted) {
            Point[] line = new Point[length + 1];
            System.arraycopy(points, indexStarted, line, 0, length);
            line[line.length - 1] = p;
            Arrays.sort(line);
            System.out.println(pointsToString(line));
            line[0].drawTo(line[line.length - 1]);
          }
        }
        if (seqBreaked) {
          indexStarted = i2;
          indexEnded = indexStarted;
          slope = cSlope;

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
