
import java.util.Iterator;

/**
 *
 * @author oleg.chumakov
 */
public class RandomizedQueue<Item> implements Iterable<Item> {

  public RandomizedQueue() // construct an empty randomized queue
  {
  }

  public boolean isEmpty() // is the queue empty?
  {
    return true;
  }

  public int size() // return the number of items on the queue
  {
    return 0;
  }

  public void enqueue(Item item) // add the item
  {
  }

  public Item dequeue() // delete and return a random item
  {
    return null;
  }

  public Item sample() // return (but do not delete) a random item
  {
    return null;
  }

  // return an independent iterator over items in random order
  @Override
  public Iterator<Item> iterator() {
    return null;
  }
}