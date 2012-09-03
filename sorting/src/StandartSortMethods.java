
/**
 *
 * @author oleg.chumakov
 */
public class StandartSortMethods {

  private static void mergeSort(Comparable[] a) {
    int N = a.length;
    Comparable[] aux = new Comparable[N];
    System.arraycopy(a, 0, aux, 0, a.length);
    mergeSort(a, aux, 0, N - 1);
  }

  private static void mergeSort(Comparable[] a, Comparable[] aux, int lo, int hi) {
    if (hi <= lo) {
      return;
    }
    int mid = lo + (hi - lo) / 2;
    mergeSort(aux, a, lo, mid);
    mergeSort(aux, a, mid + 1, hi);
    if (!less(a[mid + 1], a[mid])) {
      return;
    }
    merge(aux, a, lo, mid, hi);
  }

  private static void merge(Comparable[] a, Comparable[] aux, int lo, int mid, int hi) {
    int i = lo, j = mid + 1;
    for (int k = lo; k <= hi; k++) {
      if (i > mid) {
        aux[k] = a[j++];
      } else if (j > hi) {
        aux[k] = a[i++];
      } else if (less(a[j], a[i])) {
        aux[k] = a[j++];
      } else {
        aux[k] = a[i++];
      }
    }
  }

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

  public static void main(final String[] args) {
//    Character[] e = {'S', 'C', 'Q', 'A', 'D', 'I', 'J', 'M', 'B', 'H'};
//    Character[] e = {'K', 'N', 'V', 'W', 'Y', 'X', 'L', 'S', 'T', 'A'};
//    Character[] e = {'I', 'S', 'D', 'T', 'N', 'M', 'O', 'J', 'B', 'F'};
//    Character[] e = {'Y', 'X', 'Z', 'A', 'G', 'F', 'E', 'D', 'C', 'B'};
//    Double[] ea = {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.NEGATIVE_INFINITY, 0.2432736688055837};
//    printArray(ea);
//    insertionSort(ea);
//    printArray(ea);

    Integer[] e = {1, 2, 3, 4, 5, 6, 7, 8, 0};
    int length = e.length;
    int insertIndex = 3;
    printArray(e);
    System.arraycopy(e, insertIndex - 1, e,
            insertIndex,
            length - insertIndex);
    e[insertIndex] = -1;
    printArray(e);
  }
}
