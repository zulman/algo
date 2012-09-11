/*
 * Aug 31, 2012
 * 9:20:15 AM
 */

/**
 *
 * @author oleg.chumakov
 */
import java.util.Comparator;

public class Point implements Comparable<Point> {

  private class PointSlopeComparator implements Comparator<Point> {

    private Point point;

    public PointSlopeComparator(Point p) {
      point = p;
    }

    @Override
    public int compare(Point p1, Point p2) {
      if (p1 == p2 && p1 != null && p2 != null) {
        return 0;
      }
      double sl1 = point.slopeTo(p1);
      double sl2 = point.slopeTo(p2);
      if (sl1 > sl2) {
        return 1;
      } else if (sl1 < sl2) {
        return -1;
      }

      return 0;
    }
  }
  /**
   * compare points by slope
   */
  public final Comparator<Point> SLOPE_ORDER =
          new PointSlopeComparator(this);
  /**
   * x coordinate
   */
  private final int x;
  /**
   * y coordinate
   */
  private final int y;

  /**
   * create the point
   *
   * @param x
   * @param y
   */
  public Point(int x, int y) {
    /* DO NOT MODIFY */
    this.x = x;
    this.y = y;
  }

  /**
   * plot this point to standard drawing
   */
  public void draw() {
    /* DO NOT MODIFY */
    StdDraw.point(x, y);
  }

  /**
   * draw line between this point and that point to standard drawing
   *
   * @param that
   */
  public void drawTo(Point that) {
    /* DO NOT MODIFY */
    StdDraw.line(this.x, this.y, that.x, that.y);
  }

  /**
   * slope between this point and that point
   *
   * @param that
   * @return
   */
  public double slopeTo(Point that) {
    if (compareTo(that) == 0) {
      return Double.NEGATIVE_INFINITY;
    }

    if (that.y == y) {
      return 0.0;
    }

    if (that.x == x) {
      return Double.POSITIVE_INFINITY;
    }
    return (double) (that.y - y) / (that.x - x);
  }

  /**
   * is this point lexicographically smaller than that one? comparing
   * y-coordinates and breaking ties by x-coordinates
   *
   * @param that
   * @return
   */
  @Override
  public int compareTo(Point that) {
    if (this == that) {
      return 0;
    }
    int yd = y - that.y;
    if (yd != 0) {
      return yd;
    }
    return x - that.x;
  }

  /**
   * return string representation of this point
   *
   * @return
   */
  @Override
  public String toString() {
    /* DO NOT MODIFY */
    return "(" + x + ", " + y + ")";
  }
}
