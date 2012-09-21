/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Oleg.Chumakov
 */
import java.util.TreeSet;

public class PointSET {

  private TreeSet<Point2D> points = new TreeSet<Point2D>();

  /**
   * construct an empty set of points
   */
  public PointSET() {
  }

  /**
   * is the set empty?
   *
   * @return
   */
  public boolean isEmpty() {
    return size() == 0;
  }

  /**
   * number of points in the set
   *
   * @return
   */
  public int size() {
    return points.size();
  }

  /**
   * add the point p to the set (if it is not already in the set)
   *
   * @param p
   */
  public void insert(Point2D p) {
    points.add(p);
  }

  /**
   * does the set contain the point p?
   *
   * @param p
   * @return
   */
  public boolean contains(Point2D p) {
    return points.contains(p);
  }

  /**
   * draw all of the points to standard draw
   */
  public void draw() {
    for (Point2D p : points) {
//      StdDraw.point(p.x(), p.y());
      p.draw();
    }
  }

  /**
   * all points in the set that are inside the rectangle
   *
   * @param rect
   * @return
   */
  public Iterable<Point2D> range(RectHV rect) {
    Stack<Point2D> result = new Stack<Point2D>();
    for (Point2D op : points) {
      if (rect.contains(op)) {
        result.push(op);
      }
    }
    return result;
  }

  /**
   * a nearest neighbor in the set to p; null if set is empty
   *
   * @param p
   * @return
   */
  public Point2D nearest(Point2D p) {
    double min = Double.MAX_VALUE;
    double tempDist;
    Point2D result = null;
    for (Point2D op : points) {
      tempDist = op.distanceSquaredTo(p);
      if (tempDist < min) {
        min = tempDist;
        result = op;
      }
    }
    return result;
  }
}
