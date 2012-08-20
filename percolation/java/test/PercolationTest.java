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

  private Percolation percolation;
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
    percolation = new Percolation(N);
  }

  @After
  public void tearDown() {
  }

  @Test
  public void Union_must_be_percolated_if_all_elements_are_opened() {
    for (int i = 1; i <= N; i++) {
      for (int j = 1; j <= N; j++) {
        percolation.open(i, j);
      }
    }
    assertEquals(percolation.percolates(), true);

  }

  @Test
  public void Union_must_be_not_percolated_if_all_elements_are_default_closed() {
    assertEquals(percolation.percolates(), false);
  }

  @Test
  public void Open_operation_must_open_right_index_site() {
    percolation.open(2, 3);

    assertEquals(percolation.isOpen(2, 3), true);
  }

  @Test
  public void Union_must_be_percolated_if_column_of_elements_are_opened() {
    for (int i = 1; i <= N; i++) {
      percolation.open(i, 1);
      assertEquals(percolation.isFull(i, 1), true);
    }
    assertEquals(percolation.percolates(), true);
  }

  @Test
  public void All_elements_in_open_segment_with_one_full_element_must_be_full() {
    percolation.open(1, 2);
    percolation.open(2, 2);
    percolation.open(3, 2);
    assertEquals(checkAllOpenIsFull(), true);

    percolation.open(4, 1);
    percolation.open(5, 2);
    percolation.open(4, 3);
    assertEquals(checkAllOpenIsFull(), false);

    percolation.open(4, 2);
    assertEquals(checkAllOpenIsFull(), true);
  }

  private boolean checkAllOpenIsFull() {
    for (int i = 1; i <= N; i++) {
      for (int j = 1; j <= N; j++) {
        if (percolation.isOpen(i, j) && !percolation.isFull(i, j)) {
          return false;
        }
      }
    }
    return true;
  }

  @Test
  public void Sites_must_ignore_connections_throw_rows() {
    for (int i = 1; i <= N; i++) {
      percolation.open(i, N);
    }

    percolation.open(N, 1);
    assertEquals(percolation.isFull(N, 1), false);
  }

  @Test
  public void First_row_must_not_be_full_by_default() {
    for (int i = 1; i <= N; i++) {
      assertEquals(!percolation.isFull(1, i), true);
    }
  }

  @Test
  public void Idenity_percolation_must_be_full_only_after_opening() {
    Percolation p = new Percolation(1);
    assertEquals(p.percolates(), false);
    p.open(1, 1);
    assertEquals(p.percolates(), true);
  }

  @Test
  public void Union_must_not_be_percolated_if_one_element_in_column_is_closed() {
    for (int i = 1; i < N; i++) {
      percolation.open(i, 1);
      assertEquals(percolation.isFull(i, 1), true);
    }
    assertEquals(percolation.percolates(), false);
  }
}
