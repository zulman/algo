/*
 * Sep 11, 2012
 * 9:15:41 AM
 */

/**
 *
 * @author oleg.chumakov
 */
public class Solver {

  private Stack<Board> solution = new Stack<Board>();

  /**
   * find a solution to the initial board (using the A* algorithm)
   *
   * @param initial
   */
  public Solver(Board initial) {

    MinPQ<Board> steps = new MinPQ<Board>();
    steps.insert(initial);

  }

  /**
   * is the initial board solvable?
   *
   * @return
   */
  public boolean isSolvable() {
    return false;
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
