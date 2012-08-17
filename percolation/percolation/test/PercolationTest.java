/* 12:17:55 PM Aug 16, 2012 */

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author oleg.chumakov
 */
public class PercolationTest {

  private Percolation _percolation;
  private final int N = 10;

  public PercolationTest() {
  }

  @BeforeClass
  public static void setUpClass() throws Exception {
  }

  @AfterClass
  public static void tearDownClass() throws Exception {
  }

  @Before
  public void setUp() {
    _percolation = new Percolation(N);
  }

  @After
  public void tearDown() {
  }

  @Test
  public void Union_must_be_percolated_if_all_elements_are_opened() {
    for (int i = 1; i <= N; i++) {
      for (int j = 1; j <= N; j++) {
        _percolation.open(i, j);
      }
    }
    assertEquals(_percolation.percolates(), true);

  }

  @Test
  public void Union_must_be_not_percolated_if_all_elements_are_default_closed() {
    assertEquals(_percolation.percolates(), false);
  }

  @Test
  public void Open_operation_must_open_right_index_site() {
    _percolation.open(2, 3);

    assertEquals(_percolation.isOpen(2, 3), true);
  }

  @Test
  public void Union_must_be_percolated_if_column_of_elements_are_opened() {
    for (int i = 1; i <= N; i++) {
      _percolation.open(i, 1);
      assertEquals(_percolation.isFull(i, 1), true);
    }
    assertEquals(_percolation.percolates(), true);
  }

  @Test
  public void All_elements_in_open_segment_with_one_full_element_must_be_full() {
    _percolation.open(1, 2);
    _percolation.open(2, 2);
    _percolation.open(3, 2);
    assertEquals(checkAllOpenIsFull(), true);

    _percolation.open(4, 1);
    _percolation.open(5, 2);
    _percolation.open(4, 3);
    assertEquals(checkAllOpenIsFull(), false);

    _percolation.open(4, 2);
    assertEquals(checkAllOpenIsFull(), true);
  }

  private boolean checkAllOpenIsFull() {
    for (int i = 1; i <= N; i++) {
      for (int j = 1; j <= N; j++) {
        if (_percolation.isOpen(i, j) && !_percolation.isFull(i, j)) {
          return false;
        }
      }
    }
    return true;
  }

  @Test
  public void Sites_must_ognore_connections_throw_rows() {
    for (int i = 1; i <= N; i++) {
      _percolation.open(i, N);
    }

    _percolation.open(N, 1);
    assertEquals(_percolation.isFull(N, 1), false);
  }
  
  @Test
  public void First_row_must_not_be_full_by_default() {
    for (int i = 1; i <= N; i++) {
      assertEquals(!_percolation.isFull(0, i), true);
    }
  }

  @Test
  public void Union_must_not_be_percolated_if_one_element_in_column_is_closed() {
    for (int i = 1; i < N; i++) {
      _percolation.open(i, 1);
      assertEquals(_percolation.isFull(i, 1), true);
    }
    assertEquals(_percolation.percolates(), false);
  }
}
