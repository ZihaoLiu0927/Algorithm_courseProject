import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;

public class Permutation {
    public static void main(String args[]) {
        RandomizedQueue<String> queue = new RandomizedQueue<String>();
        int n = Integer.parseInt(args[0]);
        while (!StdIn.isEmpty()) {
            queue.enqueue(StdIn.readString());
        }
        Iterator<String> i = queue.iterator();
        while (i.hasNext() && n > 0) {
            StdOut.println(i.next());
            n--;
        }
    }
}
