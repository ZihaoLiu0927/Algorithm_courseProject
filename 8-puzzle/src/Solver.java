import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {

    private boolean solvable;
    private Node finalNode;
    private int globMove;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null)
            throw new IllegalArgumentException("must input an initial Board object!");
        MinPQ<Node> myQueue = new MinPQ<Node>();
        MinPQ<Node> anotherQueue = new MinPQ<Node>();
        myQueue.insert(new Node(initial, 0, null));
        anotherQueue.insert(new Node(initial.twin(), 0, null));


        while (!myQueue.isEmpty() && !anotherQueue.isEmpty()) {
            Node lastNode = myQueue.delMin();
            Node anotherLastNode = anotherQueue.delMin();
            if (lastNode.board.isGoal()) {
                finalNode = lastNode;
                globMove = lastNode.moves;
                solvable = true;
                break;
            } else if (anotherLastNode.board.isGoal()) {
                globMove = -1;
                solvable = false;
                break;
            }
            runAlgorithm(lastNode, myQueue);
            runAlgorithm(anotherLastNode, anotherQueue);
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return solvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (!isSolvable())
            return -1;
        return globMove;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (!isSolvable()) return null;
        Stack<Board> solution = new Stack<>();
        Node iterNode = finalNode;
        while (iterNode != null) {
            solution.push(iterNode.board);
            iterNode = iterNode.previous;
        }
        return solution;
    }

    private static class Node implements Comparable<Node> {
        public Board board;
        public int moves;
        public Node previous;
        public int distManhattan;

        public int compareTo(Node that) {
            return Integer.compare(this.distManhattan + this.moves, that.distManhattan + that.moves);
        }

        public Node(Board board, int moves, Node previous) {
            this.board = board;
            this.moves = moves;
            this.previous = previous;
            this.distManhattan = this.board.manhattan();
        }
    }

    private void runAlgorithm(Node deletedNode, MinPQ<Node> queue) {
        for (Board board : deletedNode.board.neighbors()) {
            if (deletedNode.previous == null)
                queue.insert(new Node(board, deletedNode.moves + 1, deletedNode));
            else if (!board.equals(deletedNode.previous.board))
                queue.insert(new Node(board, deletedNode.moves + 1, deletedNode));
        }
    }

    // test client (see below)
    public static void main(String[] args) {

        // create initial board from file
        int n = 3;
        int s = 0;
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = s++;
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}

