import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class Board {

    private final int[][] myBoard;
    private final int n;
    private int blkI, blkJ;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        this.n = tiles.length;
        this.myBoard = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                this.myBoard[i][j] = tiles[i][j];
                if (myBoard[i][j] == 0) {
                    blkI = i;
                    blkJ = j;
                }
            }
        }
    }

    // string representation of this board
    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                res.append(String.format("%d ", myBoard[i][j]));
            }
            res.append("\n");
        }
        return res.toString();
    }

    // board dimension n
    public int dimension() {
        return n;
    }

    // number of tiles out of place
    public int hamming() {
        int counts = 0;
        int goalNum;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (myBoard[i][j] != 0) {
                    if (i == n - 1 && j == n - 1)
                        goalNum = 0;
                    else
                        goalNum = (i * n + j + 1);
                    if (myBoard[i][j] != goalNum) {
                        counts++;
                    }
                }
            }
        }
        return counts;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int sumDistance = 0;
        int distance;
        int inferI, inferJ;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (myBoard[i][j] != 0) {
                    inferI = (myBoard[i][j] - 1) / n;
                    inferJ = myBoard[i][j] - inferI * n - 1;
                    distance = Math.abs(inferI - i) + Math.abs(inferJ - j);
                    sumDistance += distance;
                }
            }
        }
        return sumDistance;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == null) return false;
        if (y == this) return true;
        if (y.getClass() != this.getClass()) return false;

        Board that = (Board) y;
        if (this.n != that.n) return false;
        return Arrays.deepEquals(this.myBoard, that.myBoard);
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        ArrayList<Board> neighbors = new ArrayList<Board>();
        if (blkI > 0) {
            exch(blkI, blkJ, blkI - 1, blkJ);
            neighbors.add(new Board(myBoard));
            exch(blkI, blkJ, blkI - 1, blkJ);
        }
        if (blkI < n - 1) {
            exch(blkI, blkJ, blkI + 1, blkJ);
            neighbors.add(new Board(myBoard));
            exch(blkI, blkJ, blkI + 1, blkJ);
        }
        if (blkJ > 0) {
            exch(blkI, blkJ, blkI, blkJ - 1);
            neighbors.add(new Board(myBoard));
            exch(blkI, blkJ, blkI, blkJ - 1);
        }
        if (blkJ < n - 1) {
            exch(blkI, blkJ, blkI, blkJ + 1);
            neighbors.add(new Board(myBoard));
            exch(blkI, blkJ, blkI, blkJ + 1);
        }
        return neighbors;
    }


    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        if (myBoard[0][0] != 0 && myBoard[1][0] != 0)
            return new Board(swapNewBoard(0, 0, 1, 0));
        else
            return new Board(swapNewBoard(0, 1, 1, 1));
    }

    private int[][] swapNewBoard(int fi, int fj, int ti, int tj) {
        int[][] newBoard = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                newBoard[i][j] = myBoard[i][j];
            }
        }
        int temp = newBoard[fi][fj];
        newBoard[fi][fj] = newBoard[ti][tj];
        newBoard[ti][tj] = temp;
        return newBoard;
    }


    private void exch(int fi, int fj, int ti, int tj) {
        int temp = myBoard[fi][fj];
        myBoard[fi][fj] = myBoard[ti][tj];
        myBoard[ti][tj] = temp;
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        int[][] tiles = new int[3][3];
        int[][] tilesRe = new int[3][3];
        int[][] tilesTest = {{4, 1, 3}, {0, 2, 5}, {7, 8, 6}};
        int s = 8;
        int s1 = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                tiles[i][j] = s;
                tilesRe[i][j] = s1;
                s--;
                s1++;
            }
        }
        //tilesRe[2][2] = 0;

        Board b = new Board(tiles);
        Board br = new Board(tilesRe);
        Board bt = new Board(tilesTest);
        StdOut.println(bt.toString());
        StdOut.println(bt.dimension());
        StdOut.println(bt.hamming());
        StdOut.println(bt.manhattan());
        StdOut.println(bt.isGoal());
        StdOut.println(bt.equals(b));
        Iterable<Board> iter = br.neighbors();
        Iterator<Board> i = iter.iterator();
        StdOut.println(i.next());
        StdOut.println(i.next());
        StdOut.println(bt.twin());
        StdOut.println(bt.toString());
    }
}
