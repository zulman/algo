
/**
 *
 * @author oleg.chumakov
 */
public class StandartSortMethods {

  public static void selectionSort(Comparable[] a) {
    int N = a.length;
    for (int i = 0; i < N; i++) {
      int min = i;
      for (int j = i + 1; j < N; j++) {
        if (less(a[j], a[min])) {
          min = j;
        }
      }
      exch(a, i, min);
      if (i != min) {
        printArray(a);
      }
    }
  }

  public static void insertionSort(Comparable[] a) {
    int N = a.length;
    for (int i = 0; i < N; i++) {
      for (int j = i; j > 0; j--) {
        if (less(a[j], a[j - 1])) {
          exch(a, j, j - 1);
          printArray(a);
        } else {
          break;
        }
      }
    }
  }

  public static void shellSort(Comparable[] a) {
    int N = a.length;
    int h = 1;
    while (h < N / 3) {
      h = 3 * h + 1; // 1, 4, 13, 40, 121, 364, ...
    }
    while (h >= 1) {  // h-sort the array.
      for (int i = h; i < N; i++) {
        for (int j = i; j >= h && less(a[j], a[j - h]); j -= h) {
          exch(a, j, j - h);
          printArray(a);
        }
      }

      h = h / 3;
    }
  }

  private static void printArray(Comparable[] a) {
    for (int i = 0; i < a.length; i++) {
      System.out.print(a[i] + " ");
    }
    System.out.println();
  }

  private static boolean less(Comparable v, Comparable w) {
    return v.compareTo(w) < 0;
  }

  private static void exch(Comparable[] a, int i, int j) {
    Comparable swap = a[i];
    a[i] = a[j];
    a[j] = swap;
  }
//  public static void main(final String[] args) {
//    Character[] e = {'S', 'C', 'Q', 'A', 'D', 'I', 'J', 'M', 'B', 'H'};
//    Character[] e = {'K', 'N', 'V', 'W', 'Y', 'X', 'L', 'S', 'T', 'A'};
//    Character[] e = {'I', 'S', 'D', 'T', 'N', 'M', 'O', 'J', 'B', 'F'};
//  }
}
