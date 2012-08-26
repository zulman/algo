
import java.util.Iterator;

/**
 *
 * @author oleg.chumakov
 */
public class RandomizedQueue<Item> implements Iterable<Item> {

  private Item[] items = (Item[]) new Object[1];
  private int size = 0;

  /**
   * construct an empty randomized queue
   */
  public RandomizedQueue() {
  }

  /**
   * is the queue empty?
   *
   * @return
   */
  public boolean isEmpty() {
    return size == 0;
  }

  /**
   * return the number of items on the queue
   *
   * @return
   */
  public int size() {
    return size;
  }

  /**
   * add the item
   *
   * @param item
   */
  public void enqueue(Item item) {
    checkForNull(item);
    if (size == items.length) {
      resize(2 * items.length);
    }
    items[size++] = item;
  }

  /**
   * delete and return a random item
   *
   * @return
   */
  public Item dequeue() {
    checkForEmptyQueue();
    int idx = StdRandom.uniform(size);
    Item item = items[idx];
    items[idx] = null;
    size--;
    swap(items, idx, size);
    if (size > 0 && size <= items.length / 4) {
      resize(items.length / 2);
    }
    return item;
  }

  /**
   * return (but do not delete) a random item
   *
   * @return
   */
  public Item sample() {
    checkForEmptyQueue();
    return items[StdRandom.uniform(size)];
  }

  /**
   * return an independent iterator over items in random order
   *
   * @return
   */
  @Override
  public Iterator<Item> iterator() {
    return new RandomizedQueueIterator(items, size);
  }

  private static <T> void swap(T[] arr, int i, int change) {
    T helper = arr[i];
    arr[i] = arr[change];
    arr[change] = helper;
  }

  private <T> void shuffleArray(T[] arr) {
    int n = arr.length;
    for (int i = 0; i < n; i++) {
      swap(arr, i, StdRandom.uniform(n));
    }
  }

  private void resize(int capacity) {
    Item[] copy = (Item[]) new Object[capacity];
    for (int i = 0; i < capacity && i < items.length; i++) {
      copy[i] = items[i];
    }
    items = copy;
  }

  /**
   * check that item not null throws exception if validation failed
   *
   * @param item
   */
  private void checkForNull(Item item) {
    if (item == null) {
      throw new java.lang.NullPointerException();
    }
  }

  /**
   * throwing exception if queue is empty
   */
  private void checkForEmptyQueue() {
    if (isEmpty()) {
      throw new java.util.NoSuchElementException();
    }
  }

  private class RandomizedQueueIterator implements Iterator<Item> {

    private Item[] items = null;
    private int[] indexes = null;
    private int current = 0;

    public RandomizedQueueIterator(Item[] items, int size) {
      this.items = items;
      indexes = new int[size];
      for (int i = 0; i < indexes.length; i++) {
        indexes[i] = i;
      }
      for (int i = 0; i < indexes.length; i++) {
        int idx = StdRandom.uniform(indexes.length);
        int helper = indexes[i];
        indexes[i] = indexes[idx];
        indexes[idx] = helper;
      }
    }

    @Override
    public boolean hasNext() {
      return current < indexes.length;
    }

    private void checkNextExisting() {
      if (!hasNext()) {
        throw new java.util.NoSuchElementException();
      }
    }

    @Override
    public Item next() {
      checkNextExisting();
      return items[indexes[current++]];
    }

    @Override
    public void remove() {
      checkNextExisting();
      throw new java.lang.UnsupportedOperationException();
    }
  }
}
