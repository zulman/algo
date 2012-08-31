/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author oleg.chumakov
 */
public class DequeTests {
  Deque<String> deque = null;

  @Before
  public void setUp() {
    deque = new Deque<String>();
    deque.addFirst("third");
    deque.addFirst("second");
    deque.addFirst("first");
    deque.addLast("last");
  }

  @Test
  public void Deque_size_counter_must_return_valid_values() {
    assertEquals(deque.size() == 4, true);
  }

  @Test
  public void Remove_operations_must_reduce_size_of_deque() {
    assertEquals(deque.removeLast(), "last");
    assertEquals(deque.size() == 3, true);
    assertEquals(deque.removeFirst(), "first");
    assertEquals(deque.size() == 2, true);
  }

  @Test
  public void Deque_iterator_must_pass_valid_iterations_count() {
    int i = 0;
    for (String item : deque) {
      i++;
    }
    assertEquals(i, 4);
  }
}
