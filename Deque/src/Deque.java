import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
    private Node first;
    private Node last;
    private int num;

    private class Node {
        Item item;
        Node next;
        Node previous;
    }

    public Deque() {
        this.first = null;
        this.last = null;
        this.num = 0;
    }

    public boolean isEmpty() {
        return num == 0;
    }

    public int size() {
        return this.num;
    }

    public void addFirst(Item item) {
        if (item == null)
            throw new IllegalArgumentException("added item can not be null!");
        Node oldFirst = first;
        first = new Node();
        first.item = item;
        first.next = oldFirst;
        first.previous = null;
        if (isEmpty()) {
            last = first;
        } else {
            oldFirst.previous = first;
        }
        num++;
    }

    public void addLast(Item item) {
        if (item == null)
            throw new IllegalArgumentException("added item can not be null!");
        Node oldLast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        last.previous = oldLast;
        if (isEmpty()) {
            first = last;
        } else {
            oldLast.next = last;
        }
        num++;
    }


    public Item removeLast() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException("deque is already empty!");
        }
        Item item = last.item;
        last = last.previous;
        num--;
        if (isEmpty()) {
            first = null;
        } else {
            last.next = null;
        }
        return item;

    }

    public Item removeFirst() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException("deque is already empty!");
        }
        Item item = first.item;
        first = first.next;
        num--;
        if (isEmpty()) {
            last = null;
        } else {
            first.previous = null;
        }
        return item;
    }

    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException("method not supported any more");
        }

        public Item next() {
            if (!hasNext())
                throw new java.util.NoSuchElementException("no more item can be returned");
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    public static void main(String[] args) {
        Deque<String> myDeque = new Deque<String>();
        myDeque.addFirst("first item 1");
        myDeque.addFirst("first item 2");
        StdOut.println(myDeque.size());
        StdOut.println(myDeque.isEmpty());
        myDeque.addLast("last item 1");
        myDeque.addLast("last item 2");
        StdOut.println(myDeque.size());
        String removed = myDeque.removeFirst();
        StdOut.println("removed item: " + removed);
        removed = myDeque.removeFirst();
        StdOut.println("removed item: " + removed);
        Iterator<String> i = myDeque.iterator();
        while (i.hasNext()) {
            String s = i.next();
            StdOut.println(s);
        }
        myDeque.removeLast();
        myDeque.removeLast();
        StdOut.println(myDeque.isEmpty());
    }
}
