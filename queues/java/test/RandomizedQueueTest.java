/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Oleg.Chumakov
 */
public class RandomizedQueueTest {

  private RandomizedQueue<Integer> queue;
  private int size = 4;

  @Before
  public void setUp() {
    queue = new RandomizedQueue<Integer>();
    for (int i = 0; i < size; i++) {
      queue.enqueue(i);
    }

  }

  @Test
  public void Queue_must_contains_right_size_accessor() {
    assertEquals(queue.size(), size);
  }

  @Test
  public void Dqueue_must_decrease_size_of_queue() {
    queue.dequeue();
    assertEquals(queue.size(), size - 1);
  }

  @Test
  public void Iterator_must_iterate_all_items() {
    for (Integer i : queue) {
      System.out.println(i);
      assertEquals(i <= size && i >= 0, true);
    }

  }
}
