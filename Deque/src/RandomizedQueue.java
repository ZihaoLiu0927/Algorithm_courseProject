import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] a;
    private int num;

    // construct an empty randomized queue
    public RandomizedQueue() {
        this.a = (Item[]) new Object[1];
        this.num = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return num == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return num;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null)
            throw new IllegalArgumentException("must include a item to be added");
        if (num == a.length) resize(a.length * 2);
        a[num++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty())
            throw new java.util.NoSuchElementException("the randomized queue is empty");
        int randNum = StdRandom.uniform(num);
        Item item = a[randNum];
        swap(randNum, --num);
        a[--num] = null;
        if (num > 0 && num == a.length / 4) resize(a.length / 2);
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty())
            throw new java.util.NoSuchElementException("the randomized queue is empty");
        int randNum = StdRandom.uniform(num);
        return a[randNum];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new randQueueIterator();
    }

    private class randQueueIterator implements Iterator<Item> {
        private int i = num - 1;
        private final int[] order = StdRandom.permutation(num);

        public boolean hasNext() {
            return i >= 0;
        }

        public Item next() {
            if (!hasNext())
                throw new java.util.NoSuchElementException("no item can be returned any more");
            return a[order[i--]];
        }

        public void remove() {
            throw new UnsupportedOperationException("method not supported any more");
        }
    }

    private void resize(int max) {
        Item[] temp = (Item[]) new Object[max];
        for (int i = 0; i < num; i++) {
            temp[i] = a[i];
        }
        a = temp;
    }

    private void swap(int i, int j) {
        Item temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    public static void main(String[] args) {
        RandomizedQueue<Integer> myRandqueue = new RandomizedQueue<Integer>();
        StdOut.println(myRandqueue.isEmpty());
        StdOut.println(myRandqueue.size());
        myRandqueue.enqueue(1);
        myRandqueue.enqueue(2);
        myRandqueue.enqueue(3);
        myRandqueue.enqueue(4);
        myRandqueue.enqueue(5);
        myRandqueue.enqueue(6);
        myRandqueue.enqueue(7);
        myRandqueue.enqueue(8);
        StdOut.println(myRandqueue.isEmpty());
        StdOut.println(myRandqueue.size());
        int removed = myRandqueue.dequeue();
        StdOut.println("item removed: " + removed + '\n');
        Iterator<Integer> i = myRandqueue.iterator();
        while (i.hasNext()) {
            int s = i.next();
            StdOut.println(s);
        }
        int sample = myRandqueue.sample();
        StdOut.println("sample item: " + sample);
    }

}
