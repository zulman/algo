
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

  private class SNComparator implements Comparator<SearchNode> {

    @Override
    public int compare(SearchNode p1, SearchNode p2) {
      if (p1 == p2 && p1 != null && p2 != null) {
        return 0;
      }

      return p1.priority() - p2.priority();
    }
  }

  private class SearchNode {

    private Board board;
    private int moves;
    private SearchNode prevSN;

    private SearchNode(Board board, int moves, SearchNode prevSN) {
      this.board = board;
      this.moves = moves;
      this.prevSN = prevSN;
    }

    private int priority() {
      return this.board.manhattan() + moves;
    }
  }
  private Stack<Board> solution = null;
  private boolean isSolvable = true;

  /**
   * find a solution to the initial board (using the A* algorithm)
   *
   * @param initial
   */
  public Solver(Board oinitial) {
    //Instead of clone object because Board class API restrictions
    Board initial = oinitial.twin().twin();

    SearchNode snInitial = new SearchNode(initial, 0, null);
    SearchNode snInitialTW = new SearchNode(initial.twin(), 0, null);

    MinPQ<SearchNode> stepsOriginal = new MinPQ<SearchNode>(new SNComparator());
    MinPQ<SearchNode> stepsTW = new MinPQ<SearchNode>(new SNComparator());

    stepsOriginal.insert(snInitial);
    stepsTW.insert(snInitialTW);

    boolean originalSolved = false;
    while (!originalSolved) {

      SearchNode originalStep = tryToSolve(stepsOriginal);
      if (originalStep.board.isGoal()) {
        originalSolved = true;
        solution = new Stack<Board>();
        while (originalStep.prevSN != null) {
          solution.push(originalStep.board);
          originalStep = originalStep.prevSN;
        }
        solution.push(originalStep.board);
      }

      SearchNode twStep = tryToSolve(stepsTW);
      if (twStep.board.isGoal() && !originalSolved) {
        originalSolved = true;
        isSolvable = false;
      }
    }
  }

  private SearchNode tryToSolve(MinPQ<SearchNode> boards) {
    SearchNode dequeued = boards.delMin();
    if (!dequeued.board.isGoal()) {
      for (Board neighbor : dequeued.board.neighbors()) {
        if (dequeued.prevSN != null && neighbor.equals(dequeued.prevSN.board)) {
          continue;
        }
        SearchNode nsn = new SearchNode(neighbor, dequeued.moves + 1, dequeued);
        boards.insert(nsn);
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
    int res = -1;
    if (solution != null) {
      res = solution.size() - 1;
    }
    return res;
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
