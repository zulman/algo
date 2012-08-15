interface IUnion {
  int[] getElements();
  boolean connected(int p, int q);
  void union(int p, int q);
}