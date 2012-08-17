/* 7:47:43 PM Aug 15, 2012 */

/**
 *
 * @author oleg.chumakov
 */
public class PercolationStats {

  private double _mean;
  private double _stddev;
  private int _t;
  private double _confidenceIntervalComponent;

  /**
   * Constructor
   * Performs T independent computational experiments on an N-by-N grid
   * @param N
   * @param T
   */
  public PercolationStats(int N, int T) {
    if (N <= 0) {
      throw new java.lang.IllegalArgumentException(
              "N(grid size) value must be greater 0, but received " + N);
    }
    if (T <= 0) {
      throw new java.lang.IllegalArgumentException(
              "T(iterations count) value must be greater 0, but received " + T);
    }

    _t = T;
    double[] thresholdValues = new double[T];

    for (int i = 0; i < T; i++) {
      Percolation precolation = new Percolation(N);
      int totalOpened = 0;
      while (!precolation.percolates()) {
        int p = StdRandom.uniform(N) + 1;
        int q = StdRandom.uniform(N) + 1;

        while (precolation.isOpen(p, q)) {
          p = StdRandom.uniform(N)+1;
          q = StdRandom.uniform(N)+1;
        }

        precolation.open(p, q);
        totalOpened++;
      }
      thresholdValues[i] = ((double) totalOpened / (N * N));
    }

    _mean = StdStats.mean(thresholdValues);
    _stddev = StdStats.stddev(thresholdValues);
    _confidenceIntervalComponent = 1.96 * _stddev / java.lang.Math.sqrt(_t);
  }

  /**
   * 95% confidence interval upper value
   * @return 
   */
  public double upperConfidenceInterval() {
    return _mean + _confidenceIntervalComponent;
  }

  /**
   * 95% confidence interval lower value
   * @return 
   */
  public double lowerConfidenceInterval() {
    return _mean - _confidenceIntervalComponent;
  }

  /**
   * sample mean of percolation threshold
   * @return
   */
  public double mean() {
    return _mean;
  }

  /**
   * sample standard deviation of percolation threshold
   * @return
   */
  public double stddev() {
    return _stddev;
  }

  /**
   * test client, described below
   * @param args
   */
  public static void main(final String[] args) {

    int N = 10;
    int T = 10;
    if (args.length >= 2) {
      try {
        N = Integer.parseInt(args[0].trim());
        T = Integer.parseInt(args[1].trim());
      } catch (Throwable e) {
      }
    }

    System.out.println(String.format("N = %s, T = %s", N, T));

    PercolationStats percolationStats = new PercolationStats(N, T);
    System.out.println(String.format(
            "mean                    = %s", percolationStats.mean()));
    System.out.println(String.format(
            "stddev                  = %s", percolationStats.stddev()));
    System.out.println(String.format(
            "95%% confidence interval = %s, %s",
            percolationStats.lowerConfidenceInterval(),
            percolationStats.upperConfidenceInterval()));
  }
}
