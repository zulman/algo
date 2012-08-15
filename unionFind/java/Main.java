public class Main {
   public static void main(String[] args) {
      System.out.println("On Air. 1- Basic Union, 2 - Weighted Union:");
      int mode = StdIn.readInt();
      System.out.println("Size:");
      int N = StdIn.readInt();
      IUnion uf = null;
      if (mode == 1) uf = new Union(N);
      if (mode == 2) uf = new WeightedUnion(N);
      System.out.println("Using " + uf.getClass().getName());

      while (!StdIn.isEmpty()) {
         int p = StdIn.readInt();
         int q = StdIn.readInt();

         if (!uf.connected(p, q)) {
            uf.union(p, q);
            System.out.print(arrayToString(uf.getElements()));
         } else System.out.print("Elements already connected");
      }
   }

   public static String arrayToString(int[] array) {
      String result = "==Array==\n";
      String keys = "";
      String values = "";
      for (int i = 0; i < array.length; i++) {
         keys += i + "   ";
         values += array[i] + "   ";
      }
      result += keys + "\n";
      result += values + "\n";
      return result;
   }
}