public class Union implements IUnion {
   private int[] id;
   public Union(int N) {
      id = new int[N];
      for (int i = 0; i < N; i++)
      id[i] = i;
   }
   public int[] getElements() {
      return id;
   }
   public boolean connected(int p, int q) {
      return id[p] == id[q];
   }
   public void union(int p, int q) {
      int pid = id[p];
      int qid = id[q];
      for (int i = 0; i < id.length; i++)
      if (id[i] == pid) id[i] = qid;
   }
}