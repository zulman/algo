/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Oleg.Chumakov
 */
public class PointSET {

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
    return false;
  }

  /**
   * number of points in the set
   *
   * @return
   */
  public int size() {
    return 0;
  }

  /**
   * add the point p to the set (if it is not already in the set)
   *
   * @param p
   */
  public void insert(Point2D p) {
  }

  /**
   * does the set contain the point p?
   *
   * @param p
   * @return
   */
  public boolean contains(Point2D p) {
    return false;
  }

  /**
   * draw all of the points to standard draw
   */
  public void draw() {
  }

  /**
   * all points in the set that are inside the rectangle
   *
   * @param rect
   * @return
   */
  public Iterable<Point2D> range(RectHV rect) {
    return new Stack<Point2D>();
  }

  /**
   * a nearest neighbor in the set to p; null if set is empty
   *
   * @param p
   * @return
   */
  public Point2D nearest(Point2D p) {
    return new Point2D(0, 0);
  }
}
