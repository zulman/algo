/**
 *
 * @author oleg.chumakov
 */
public class Subset {

  public static void main(final String[] args) {
    int k = Integer.parseInt(args[0].trim());
    String[] items = StdIn.readStrings();
    int size = items.length;

    if (k > size) {
      throw new java.lang.ArrayIndexOutOfBoundsException();
    }

    for (int i = 0; i < k; i++) {
      int idx = StdRandom.uniform(size);
      String tmp = items[idx];
      items[idx] = items[i];
      items[i] = tmp;
    }

    for (int i = 0; i < k; i++) {
      System.out.println(items[i]);
    }
  }
}