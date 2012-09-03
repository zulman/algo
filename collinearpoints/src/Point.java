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
      double sld = point.slopeTo(p1) - point.slopeTo(p2);
      if (sld > 0) {
        return 1;
      } else if (sld
              < 0) {
        return -1;
      }


      return 0;
    }
  }
  // compare points by slope
  public final Comparator<Point> SLOPE_ORDER =
          new PointSlopeComparator(this);
  private final int x;                              // x coordinate
  private final int y;                              // y coordinate

  // create the point (x, y)
  public Point(int x, int y) {
    /* DO NOT MODIFY */
    this.x = x;
    this.y = y;
  }

  // plot this point to standard drawing
  public void draw() {
    /* DO NOT MODIFY */
    StdDraw.point(x, y);
  }

  // draw line between this point and that point to standard drawing
  public void drawTo(Point that) {
    /* DO NOT MODIFY */
    StdDraw.line(this.x, this.y, that.x, that.y);
  }

  // slope between this point and that point
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
    return (double) (that.y - y) / (double) (that.x - x);
  }

  // is this point lexicographically smaller than that one?
  // comparing y-coordinates and breaking ties by x-coordinates
  @Override
  public int compareTo(Point that) {
    int yd = y - that.y;
    if (yd != 0) {
      return yd;
    }
    return x - that.x;
  }

  // return string representation of this point
  @Override
  public String toString() {
    /* DO NOT MODIFY */
    return "(" + x + ", " + y + ")";
  }
}
