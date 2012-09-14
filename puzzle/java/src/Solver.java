
/*
 * Sep 11, 2012
 * 9:15:41 AM
 */
/**
 *
 * @author oleg.chumakov
 */
import java.util.Comparator;

public class Solver {

//  private class BoardHammingComparator implements Comparator<Board> {
//
//    @Override
//    public int compare(Board p1, Board p2) {
//      if (p1 == p2 && p1 != null && p2 != null) {
//        return 0;
//      }
//      return p1.hamming() - p2.hamming();
//    }
//  }
  private class BoardManhattanComparator implements Comparator<Board> {

    @Override
    public int compare(Board p1, Board p2) {
      if (p1 == p2 && p1 != null && p2 != null) {
        return 0;
      }
      return p1.manhattan() - p2.manhattan();
    }
  }
  private Queue<Board> solution = new Queue<Board>();
//  private BoardHammingComparator hammingComparator = new BoardHammingComparator();
  private BoardManhattanComparator manhattanComparator = new BoardManhattanComparator();
  private boolean isSolvable = true;

  /**
   * find a solution to the initial board (using the A* algorithm)
   *
   * @param initial
   */
  public Solver(Board initial) {

    Board infeasibleClone = initial.twin();

    MinPQ<Board> stepsInfeasibleClone = new MinPQ<Board>(manhattanComparator);
    MinPQ<Board> stepsOriginal = new MinPQ<Board>(manhattanComparator);

    stepsInfeasibleClone.insert(infeasibleClone);
    stepsOriginal.insert(initial);

    Board prevOriginal = null;
    Board prevInfeasibleClone = null;
    boolean originalSolved = false;
    while (!originalSolved) {
      Board originalStep = tryToSolve(stepsOriginal, prevOriginal);
      prevOriginal = originalStep;
      solution.enqueue(originalStep);
      if (originalStep.isGoal()) {
        originalSolved = true;
      }

      Board infeasibleCloneStep = tryToSolve(stepsInfeasibleClone,
              prevInfeasibleClone);
      prevInfeasibleClone = infeasibleCloneStep;
      if (infeasibleCloneStep.isGoal() && !originalSolved) {
        isSolvable = false;
        break;
      }

//      System.out.println("Original " + prevOriginal.toString());
//      System.out.println("Infeasible " + prevInfeasibleClone.toString());
    }
  }

  private Board tryToSolve(MinPQ<Board> boards, Board prev) {
    Board dequeued = boards.delMin();
    if (!dequeued.isGoal()) {
      for (Board neighbor : dequeued.neighbors()) {
        if (!neighbor.equals(prev)) {
          boards.insert(neighbor);
        }
      }
    }
    return dequeued;
  }

  /**
   * is the initial board solvable?
   *
   * @return
   */
  public boolean isSolvable() {
    return isSolvable;
  }

  /**
   * min number of moves to solve initial board; -1 if no solution
   *
   * @return
   */
  public int moves() {
    return solution.size() - 1;
  }

  /**
   * sequence of boards in a shortest solution; null if no solution
   *
   * @return
   */
  public Iterable<Board> solution() {
    return solution;
  }

  /**
   * solve a slider puzzle (given below)
   *
   * @param args
   */
  public static void main(String[] args) {
    // create initial board from file
    In in = new In(args[0]);
    int N = in.readInt();
    int[][] blocks = new int[N][N];
    for (int i = 0; i < N; i++) {
      for (int j = 0; j < N; j++) {
        blocks[i][j] = in.readInt();
      }
    }
    Board initial = new Board(blocks);

    // solve the puzzle
    Solver solver = new Solver(initial);

    // print solution to standard output
    if (!solver.isSolvable()) {
      StdOut.println("No solution possible");
    } else {
      StdOut.println("Minimum number of moves = " + solver.moves());
      for (Board board : solver.solution()) {
        StdOut.println(board);
      }
    }
  }
}
