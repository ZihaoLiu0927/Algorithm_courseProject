import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;

public class PointSET {
    SET<Point2D> myset;

    // construct an empty set of points
    public PointSET() {
        myset = new SET<Point2D>();
    }

    // is the set empty?
    public boolean isEmpty() {
        return myset.isEmpty();
    }

    // number of points in the set
    public int size() {
        return myset.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException("point cannot be null!\n");
        myset.add(p);
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException("point cannot be null!\n");
        return myset.contains(p);
    }


    // draw all points to standard draw
    public void draw() {
        for (Point2D each : myset) {
            each.draw();
        }
    }


    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null)
            throw new IllegalArgumentException("rectangle cannot be null!\n");
        ArrayList<Point2D> pointsInside = new ArrayList<Point2D>();
        for (Point2D each : myset) {
            if (rect.contains(each)) {
                pointsInside.add(each);
            }
        }
        return pointsInside;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException("point cannot be null!\n");
        if (isEmpty())
            return null;
        Point2D minP = null;
        double min = Double.POSITIVE_INFINITY;
        double tempDist;
        for (Point2D each : myset) {
            tempDist = each.distanceTo(p);
            if (tempDist < min) {
                min = tempDist;
                minP = each;
            }
        }
        return minP;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {
        PointSET myPset = new PointSET();
        StdOut.println(myPset.isEmpty());
        StdOut.println(myPset.size());
        myPset.insert(new Point2D(4, 9));
        StdOut.println(myPset.isEmpty());
        myPset.insert(new Point2D(2, 6));
        myPset.insert(new Point2D(1, 7));
        myPset.insert(new Point2D(9, 2));
        myPset.insert(new Point2D(13, 0));
        StdOut.println(myPset.size());
        StdOut.println(myPset.contains(new Point2D(1, 7)));
        StdOut.println(myPset.contains(new Point2D(13, 7)));
        Iterable<Point2D> pointsInsideRect = myPset.range(new RectHV(0, 0, 13, 13));
        for (Point2D i : pointsInsideRect) {
            StdOut.println(i.toString());
        }
        StdOut.println("nearest point: " + myPset.nearest(new Point2D(1, 13)).toString());
    }
}
