
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

  private class BoardHammingComparator implements Comparator<Board> {

    @Override
    public int compare(Board p1, Board p2) {
      if (p1 == p2 && p1 != null && p2 != null) {
        return 0;
      }
      return p1.hamming() - p2.hamming();
    }
  }
  private Queue<Board> solution = new Queue<Board>();
  private Board previousSearchNode = null;
  private BoardHammingComparator hammingComparator = new BoardHammingComparator();

  /**
   * find a solution to the initial board (using the A* algorithm)
   *
   * @param initial
   */
  public Solver(Board initial) {
    MinPQ<Board> steps = new MinPQ<Board>(hammingComparator);
    steps.insert(initial);
    while (true) {
      Board dequeued = steps.delMin();
      solution.enqueue(dequeued);
      if (dequeued.isGoal()) {
        break;
      }
      for (Board neighbor : dequeued.neighbors()) {
        if (!neighbor.equals(previousSearchNode)) {
          steps.insert(neighbor);
        }
      }
      previousSearchNode = dequeued;

    }
  }

  /**
   * is the initial board solvable?
   *
   * @return
   */
  public boolean isSolvable() {
    return true;
  }

  /**
   * min number of moves to solve initial board; -1 if no solution
   *
   * @return
   */
  public int moves() {
    return solution.size();
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
