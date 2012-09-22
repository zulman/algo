/*
 * Sep 21, 2012
 * 6:36:18 PM
 */

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author oleg.chumakov
 */
public class KdTreeTest {

  private KdTree tree;
  private Point2D p = new Point2D(0.5, 0.5);

  public KdTreeTest() {
  }

  @Before
  public void setUp() {
    tree = new KdTree();
    tree.insert(p);
  }

  /**
   * Test of isEmpty method, of class KdTree.
   */
  @Test
  public void testIsEmpty() {
    tree = new KdTree();
    assertEquals(true, tree.isEmpty());

  }

  /**
   * Test of size method, of class KdTree.
   */
  @Test
  public void testSize() {
    assertEquals(1, tree.size());
  }

  /**
   * Test of insert method, of class KdTree.
   */
  @Test
  public void testInsert() {
    tree.insert(new Point2D(0.25, 0.25));
    assertEquals(2, tree.size());
  }

  /**
   * Test of contains method, of class KdTree.
   */
  @Test
  public void testContains() {
    Point2D p1 = new Point2D(0.25, 0.25);
    Point2D p2 = new Point2D(0.3, 0.25);
    tree.insert(p1);
    assertEquals(true, tree.contains(p1));
    assertEquals(true, tree.contains(p));
    assertEquals(false, tree.contains(p2));
  }
//  @Test
//  public void testCompare() {
//    Point2D a = new Point2D(1, 1);
//    Point2D b = new Point2D(2, 2);
//    int res = tree.compare(a, b, KdTree.NodeType.HORIZONTAL);
//    assertEquals(1, res);
//    res = tree.compare(b, a, KdTree.NodeType.HORIZONTAL);
//    assertEquals(-1, res);
//
//    res = tree.compare(a, b, KdTree.NodeType.VERTICAL);
//    assertEquals(1, res);
//
//    res = tree.compare(b, a, KdTree.NodeType.VERTICAL);
//    assertEquals(-1, res);
//  }
//  @Test
//  public void testSplitRect() {
//
//    RectHV rVP = new RectHV(3, 0, 10, 10);
//    RectHV rVN = new RectHV(0, 0, 3, 10);
//    RectHV rHP = new RectHV(0, 3, 10, 10);
//    RectHV rHN = new RectHV(0, 0, 10, 3);
//
//    RectHV r = new RectHV(0, 0, 10, 10);
//    Point2D p = new Point2D(3, 3);
//
//    RectHV res = tree.splitRect(r, p,
//            KdTree.NodeType.VERTICAL,
//            KdTree.RectSplitType.POSITIVE);
//    assertEquals(true, res.equals(rVP));
//
//    res = tree.splitRect(r, p,
//            KdTree.NodeType.VERTICAL,
//            KdTree.RectSplitType.NEGATIVE);
//    assertEquals(true, res.equals(rVN));
//
//    res = tree.splitRect(r, p,
//            KdTree.NodeType.HORIZONTAL,
//            KdTree.RectSplitType.POSITIVE);
//    assertEquals(true, res.equals(rHP));
//
//    res = tree.splitRect(r, p,
//            KdTree.NodeType.HORIZONTAL,
//            KdTree.RectSplitType.NEGATIVE);
//    assertEquals(true, res.equals(rHN));
//  }
}
