import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class Percolation {

    private boolean[][] grid;
    private int numOpen;
    private final WeightedQuickUnionUF uf;
    private final WeightedQuickUnionUF ufTop;
    private final int n;
    private final int topNodeIndex, bottomNodeIndex;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException("n is<=0");
        this.n = n;
        this.numOpen = 0;
        this.topNodeIndex = 0;
        this.bottomNodeIndex = n * n + 1;
        this.uf = new WeightedQuickUnionUF(n * n + 2);
        this.ufTop = new WeightedQuickUnionUF(n * n + 1);
        this.grid = new boolean[n + 2][n + 2];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                this.grid[i][j] = false;
            }
        }
    }


    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        checkBoundary(row, col);
        int idx = convert2Dto1D(row, col);
        if (!isOpen(row, col)) {
            grid[row][col] = true;
            numOpen++;
        }
        else return;
        if (row == 1) {
            uf.union(topNodeIndex, idx);
            ufTop.union(topNodeIndex, idx);
        }
        else if (row == n) {
            uf.union(bottomNodeIndex, idx);
        }
        if (grid[row - 1][col]) {
            uf.union(idx, idx - n);
            ufTop.union(idx, idx - n);
        }
        if (grid[row + 1][col]) {
            uf.union(idx, idx + n);
            ufTop.union(idx, idx + n);
        }
        if (grid[row][col - 1]) {
            uf.union(idx, idx - 1);
            ufTop.union(idx, idx - 1);
        }
        if (grid[row][col + 1]) {
            uf.union(idx, idx + 1);
            ufTop.union(idx, idx + 1);
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        checkBoundary(row, col);
        return grid[row][col];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        checkBoundary(row, col);
        if (!isOpen(row, col)) {
            return false;
        }
        int idx = convert2Dto1D(row, col);
        return ufTop.find(topNodeIndex) == ufTop.find(idx);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return numOpen;
    }

    // does the system percolate?
    public boolean percolates() {
        return uf.find(topNodeIndex) == uf.find(bottomNodeIndex);
    }

    private void checkBoundary(int row, int col) {
        if (row < 1 || row > n || col < 1 || col > n)
            throw new IndexOutOfBoundsException("index out of range");
    }

    private int convert2Dto1D(int row, int col) {
        return (row - 1) * n + col - 1;
    }

    // test client (optional)
    public static void main(String[] args) {
        int n = StdIn.readInt();
        Percolation testPerco = new Percolation(n);
        testPerco.open(3, 2);
        testPerco.open(4, 2);
        testPerco.open(5, 2);
        testPerco.open(1, 2);
        testPerco.open(2, 2);
        System.out.print(testPerco.percolates());
    }
}
