/*
 * Aug 31, 2012
 * 9:28:19 AM
 */

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author oleg.chumakov
 */
public class PointTests {

  private Point testPoint1;
  private Point testPoint2;

  @Before
  public void setUp() {

    testPoint1 = new Point(3, 2);
    testPoint2 = new Point(2, 2);
  }

  @Test
  public void Slope_must_be_positive_zero_for_horizontal_line() {
    assertEquals(0.0, testPoint1.slopeTo(testPoint2), 0);
  }

  @Test
  public void Comare_operations_must_return_zero_for_same_values() {

    assertEquals(true, testPoint1.compareTo(testPoint2) > 0);
  }
}
