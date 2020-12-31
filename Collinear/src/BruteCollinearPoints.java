import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
    private final ArrayList<LineSegment> lineSet = new ArrayList<LineSegment>();

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        assertPoints(points);
        final Point[] copyPoint;
        copyPoint = new Point[points.length];
        for (int i = 0; i < points.length; i++) {
            copyPoint[i] = points[i];
        }
        Arrays.sort(copyPoint);
        double slope1, slope2, slope3;
        for (int i = 0; i < copyPoint.length - 3; i++) {
            for (int j = i + 1; j < copyPoint.length - 2; j++) {
                for (int k = j + 1; k < copyPoint.length - 1; k++) {
                    slope1 = copyPoint[i].slopeTo(copyPoint[j]);
                    slope2 = copyPoint[i].slopeTo(copyPoint[k]);
                    if (slope1 == slope2)
                        for (int u = k + 1; u < copyPoint.length; u++) {
                            slope3 = copyPoint[i].slopeTo(copyPoint[u]);
                            if (slope1 == slope3)
                                lineSet.add(new LineSegment(copyPoint[i], copyPoint[u]));
                        }
                }
            }
        }
    }

    // the number of line segments
    public int numberOfSegments() {
        return lineSet.size();
    }

    // the line segments
    public LineSegment[] segments() {
        LineSegment[] resSet = new LineSegment[lineSet.size()];
        for (int i = 0; i < lineSet.size(); i++) {
            resSet[i] = lineSet.get(i);
        }
        return resSet;
    }

    private void assertPoints(Point[] p) {
        if (p == null)
            throw new IllegalArgumentException("null passed in!");
        for (int i = 0; i < p.length; i++) {
            if (p[i] == null)
                throw new IllegalArgumentException("null point in the collection!");
        }
        for (int i = 0; i < p.length - 1; i++) {
            for (int j = i + 1; j < p.length; j++) {
                if (p[i].compareTo(p[j]) == 0)
                    throw new IllegalArgumentException("repeated point detected!");
            }
        }
    }

}
