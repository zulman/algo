/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Oleg.Chumakov
 */
public class KdTree {

  private double currentMinDist = Double.MAX_VALUE;
  private Point2D currentMin = null;

  private enum RectSplitType {

    POSITIVE,
    NEGATIVE
  }

  private enum NodeType {

    HORIZONTAL,
    VERTICAL
  }

  private static class Node {

    /**
     * the point
     */
    private Point2D p;
    private NodeType type = NodeType.VERTICAL;
    /**
     * the axis-aligned rectangle corresponding to this node
     */
    private RectHV rect;
    /**
     * the left/bottom subtree
     */
    private Node lb;
    /**
     * the right/top subtree
     */
    private Node rt;

    private Node(Point2D point, RectHV rectangle, NodeType type) {
      this.p = point;
      this.rect = rectangle;
      this.type = type;
    }
  }
  private Node root = null;
  private int size = 0;

  /**
   * construct an empty set of points
   */
  public KdTree() {
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
    return size;
  }

  /**
   * add the point p to the set (if it is not already in the set)
   *
   * @param p
   */
  public void insert(Point2D p) {
    if (p == null) {
      return;
    }

    if (root == null) {
      root = new Node(p, new RectHV(0, 0, 1, 1), NodeType.VERTICAL);
    } else {
      insert(root, p);
    }
    size++;
  }

  private NodeType invert(NodeType type) {
    if (type == NodeType.HORIZONTAL) {
      return NodeType.VERTICAL;
    } else if (type == NodeType.VERTICAL) {
      return NodeType.HORIZONTAL;
    }
    return type;
  }

  private void insert(Node base, Point2D point) {
    int comparasion = compare(base.p, point, base.type);

    if (comparasion >= 0) {
      if (base.rt == null) {
        RectHV r = splitRect(base.rect, base.p, base.type, RectSplitType.POSITIVE);
        base.rt = new Node(point, r, invert(base.type));
      } else {
        insert(base.rt, point);
      }
    } else if (comparasion < 0) {
      if (base.lb == null) {
        RectHV r = splitRect(base.rect, base.p, base.type, RectSplitType.NEGATIVE);
        base.lb = new Node(point, r, invert(base.type));
      } else {
        insert(base.lb, point);
      }
    }
  }

  /**
   *
   * @param rect
   * @param splitPoint
   * @param isHorizontal
   * @param split POSITIVE - right and top, NEGATIVE - left and bottom
   * @return
   */
  private RectHV splitRect(RectHV rect, Point2D splitPoint,
          NodeType type, RectSplitType splitType) {

    double xmin = rect.xmin();
    double xmax = rect.xmax();
    double ymin = rect.ymin();
    double ymax = rect.ymax();

    if (type == NodeType.HORIZONTAL) {
      //Bottom
      if (splitType == RectSplitType.NEGATIVE) {
        ymax = splitPoint.y();
      } //Top
      else if (splitType == RectSplitType.POSITIVE) {
        ymin = splitPoint.y();
      }
    } else if (type == NodeType.VERTICAL) {
      //Right
      if (splitType == RectSplitType.NEGATIVE) {
        xmax = splitPoint.x();
      } //Left
      else if (splitType == RectSplitType.POSITIVE) {
        xmin = splitPoint.x();
      }
    }
    RectHV result = new RectHV(xmin, ymin, xmax, ymax);

    return result;
  }

  private int compare(Point2D a, Point2D b, NodeType type) {
    if (type == NodeType.HORIZONTAL) {
      if (a.y() == b.y()) {
        return 0;
      } else if (b.y() > a.y()) {
        return 1;
      } else {
        return -1;
      }
    } else if (type == NodeType.VERTICAL) {
      if (a.x() == b.x()) {
        return 0;
      } else if (b.x() > a.x()) {
        return 1;
      } else {
        return -1;
      }
    }
    return 0;
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
    draw(root);
  }

  /**
   * Recursively draws nodes and their rectangles
   *
   * @param n
   */
  private void draw(Node n) {
    if (n.type == NodeType.HORIZONTAL) {
      StdDraw.setPenColor(StdDraw.BLUE);
      StdDraw.line(n.rect.xmin(), n.p.y(), n.rect.xmax(), n.p.y());
    } else if (n.type == NodeType.VERTICAL) {
      StdDraw.setPenColor(StdDraw.RED);
      StdDraw.line(n.p.x(), n.rect.ymin(), n.p.x(), n.rect.ymax());
    }
    StdDraw.setPenColor(StdDraw.BLACK);
    StdDraw.setPenRadius(.01);
    n.p.draw();
    StdDraw.setPenRadius(0.002);

    if (n.lb != null) {
      draw(n.lb);
    }

    if (n.rt != null) {
      draw(n.rt);
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
    range(root, rect, result);
    return result;
  }

  private void range(Node base, RectHV rect, Stack<Point2D> result) {
    if (!base.rect.intersects(rect)) {
      return;
    }

    if (rect.contains(base.p)) {
      result.push(base.p);
    }

    if (base.lb != null) {
      range(base.lb, rect, result);
    }
    if (base.rt != null) {
      range(base.rt, rect, result);
    }
  }

  /**
   * a nearest neighbor in the set to p; null if set is empty
   *
   * @param p
   * @return
   */
  public Point2D nearest(Point2D p) {
    currentMinDist = Double.MAX_VALUE;
    currentMin = null;
    nearest(root, p);

    return currentMin;
  }

  private void nearest(Node base, Point2D point) {
    if (base.rect.distanceTo(point) > currentMinDist) {
      return;
    }

    double tmpDist = base.p.distanceTo(point);
    if (tmpDist < currentMinDist) {
      currentMinDist = tmpDist;
      currentMin = base.p;
    }
    //optimization required: check before start recursion
    if (base.lb != null) {
      nearest(base.lb, point);
    }
    if (base.rt != null) {
      nearest(base.rt, point);
    }
  }
}
