import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    // perform independent trials on an n-by-n grid
    private final double[] ps;
    private final int nTimes;
    private final double itvConstant;

    public PercolationStats(int n, int trials) {
        if (n <= 0) throw new IllegalArgumentException("n is<=0");
        if (trials <= 0) throw new IllegalArgumentException("trails is<=0");
        this.nTimes = trials;
        this.ps = new double[trials];
        this.itvConstant = 1.96;
        double p;
        int siteRow, siteCol;
        int openNum;
        for (int i = 0; i < trials; i++) {
            Percolation exp = new Percolation(n);
            while (!exp.percolates()) {
                siteRow = StdRandom.uniform(n) + 1;
                siteCol = StdRandom.uniform(n) + 1;
                exp.open(siteRow, siteCol);
            }
            openNum = exp.numberOfOpenSites();
            p = openNum / (double) (n * n);
            this.ps[i] = p;
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        double meanV = StdStats.mean(this.ps);
        return meanV;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        double stdevV = StdStats.stddev(this.ps);
        return stdevV;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - itvConstant * stddev() / Math.sqrt(nTimes);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + itvConstant * stddev() / Math.sqrt(nTimes);
    }

    public static void main(String[] args) {
        int n = StdIn.readInt();
        int t = StdIn.readInt();
        PercolationStats test = new PercolationStats(n, t);
        StdOut.println("mean = " + test.mean());
        StdOut.println("stddev = " + test.stddev());
        StdOut.println(
                "95% confidence interval " + test.confidenceLo() + ", " + test.confidenceHi());
    }
}
