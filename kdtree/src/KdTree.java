import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;

public class KdTree {

    private Node root;

    private static class Node {
        Node leftNode;
        Node rightNode;
        Point2D point;
        RectHV nodeRect;
        int n;

        public Node(Point2D point, RectHV rect, int n) {
            this.point = point;
            this.n = n;
            this.leftNode = null;
            this.rightNode = null;
            this.nodeRect = rect;
        }
    }

    // construct an empty set of points
    public KdTree() {
        this.root = null;
    }

    // is the set empty?
    public boolean isEmpty() {
        return this.root == null;
    }

    // number of points in the set
    public int size() {
        return size(root);
    }

    private int size(Node node) {
        if (node == null) return 0;
        else return node.n;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        assertPoint(p);
        root = insertByX(root, p, 0, 0, 1, 1);
    }

    private Node insertByX(Node node, Point2D p, double xmin, double ymin, double xmax, double ymax) {
        if (node == null)
            return new Node(p, new RectHV(xmin, ymin, xmax, ymax), 1);
        if (node.point.equals(p))
            return node;
        int cmp = Point2D.X_ORDER.compare(p, node.point);
        double px = node.point.x();
        if (cmp < 0)
            node.leftNode = insertByY(node.leftNode, p, xmin, ymin, px, ymax);
        else
            node.rightNode = insertByY(node.rightNode, p, px, ymin, xmax, ymax);
        node.n = size(node.leftNode) + size(node.rightNode) + 1;
        return node;
    }

    private Node insertByY(Node node, Point2D p, double xmin, double ymin, double xmax, double ymax) {
        if (node == null)
            return new Node(p, new RectHV(xmin, ymin, xmax, ymax), 1);
        if (node.point.equals(p))
            return node;
        int cmp = Point2D.Y_ORDER.compare(p, node.point);
        double py = node.point.y();
        if (cmp < 0)
            node.leftNode = insertByX(node.leftNode, p, xmin, ymin, xmax, py);
        else
            node.rightNode = insertByX(node.rightNode, p, xmin, py, xmax, ymax);
        node.n = size(node.leftNode) + size(node.rightNode) + 1;
        return node;
    }


    // does the set contain point p?
    public boolean contains(Point2D p) {
        assertPoint(p);
        return contains(root, p, true);
    }

    private boolean contains(Node node, Point2D p, boolean byx) {
        if (node == null) return false;
        if (node.point.equals(p)) return true;
        int cmp;
        if (byx) {
            cmp = Point2D.X_ORDER.compare(p, node.point);
        } else {
            cmp = Point2D.Y_ORDER.compare(p, node.point);
        }
        if (cmp < 0)
            return contains(node.leftNode, p, !byx);
        else
            return contains(node.rightNode, p, !byx);
    }

    // draw all points to standard draw
    public void draw() {
        if (isEmpty()) return;
        draw(root, true);
    }

    private void draw(Node node, boolean byx) {
        double xstart, ystart, xend, yend;
        if (byx) {
            StdDraw.setPenColor(StdDraw.RED);
            xstart = node.point.x();
            xend = node.point.x();
            ystart = node.nodeRect.ymin();
            yend = node.nodeRect.ymax();
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            xstart = node.nodeRect.xmin();
            xend = node.nodeRect.xmax();
            ystart = node.point.y();
            yend = node.point.y();
        }
        StdDraw.line(xstart, ystart, xend, yend);
        StdDraw.setPenColor(StdDraw.BLACK);
        node.point.draw();

        if (node.leftNode != null) draw(node.leftNode, !byx);
        if (node.rightNode != null) draw(node.rightNode, !byx);
    }


    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        assertRect(rect);
        ArrayList<Point2D> pointList = new ArrayList<Point2D>();
        range(root, rect, pointList);
        return pointList;
    }

    private void range(Node node, RectHV query, ArrayList<Point2D> pointList) {
        if (node == null) return;
        if (!node.nodeRect.intersects(query)) return;
        if (query.contains(node.point)) pointList.add(node.point);
        range(node.leftNode, query, pointList);
        range(node.rightNode, query, pointList);
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        assertPoint(p);
        if (isEmpty()) return null;
        Point2D closest = root.point;
        return nearest(root, p, closest, true);
    }

    private Point2D nearest(Node node, Point2D p, Point2D current, boolean byx) {
        if (node == null) return current;
        double dist = current.distanceSquaredTo(p);
        if (node.nodeRect.distanceSquaredTo(p) > dist) return current;

        if (dist > node.point.distanceSquaredTo(p)) {
            current = node.point;
        }
        int cmp;
        if (byx)
            cmp = Point2D.X_ORDER.compare(p, node.point);
        else
            cmp = Point2D.Y_ORDER.compare(p, node.point);
        if (cmp < 0) {
            current = nearest(node.leftNode, p, current, !byx);
            current = nearest(node.rightNode, p, current, !byx);
        } else {
            current = nearest(node.rightNode, p, current, !byx);
            current = nearest(node.leftNode, p, current, !byx);
        }
        return current;
    }

    private void assertPoint(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException("point cannot be null!\n");
    }

    private void assertRect(RectHV rect) {
        if (rect == null)
            throw new IllegalArgumentException("point cannot be null!\n");
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {
        KdTree mytree = new KdTree();
        StdOut.println(mytree.size());
        StdOut.println(mytree.isEmpty());
        mytree.insert(new Point2D(0.7, 0.2));
        mytree.insert(new Point2D(0.5, 0.4));
        mytree.insert(new Point2D(0.2, 0.3));
        mytree.insert(new Point2D(0.4, 0.7));
        mytree.insert(new Point2D(0.9, 0.6));
        StdOut.println(mytree.size());
        StdOut.println(mytree.isEmpty());
        StdOut.println(mytree.contains(new Point2D(0.5, 0.4)));
        StdOut.println(mytree.contains(new Point2D(0.5, 0.3)));
        StdOut.println(mytree.range(new RectHV(0, 0, 0.7, 0.4)));

        Point2D p = new Point2D(0.2, 0.8);
        StdOut.println(mytree.nearest(p));


    }
}
