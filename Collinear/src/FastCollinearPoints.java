import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    private final ArrayList<LineSegment> lineSet = new ArrayList<LineSegment>();

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        assertPoints(points);
        final Point[] copyPoint;
        int n = points.length;
        copyPoint = new Point[n];
        for (int i = 0; i < n; i++) {
            copyPoint[i] = points[i];
        }

        int count;
        for (int i = 0; i < n; i++) {
            Arrays.sort(copyPoint, points[i].slopeOrder());
            count = 0;
            double currenSlope = copyPoint[1].slopeTo(copyPoint[0]);
            int max = 1, min = 1;
            for (int j = 1; j < n; j++) {
                if (Double.compare(copyPoint[j].slopeTo(copyPoint[0]), currenSlope) == 0) {
                    count++;
                    if (copyPoint[max].compareTo(copyPoint[j]) < 0)
                        max = j;
                    if (copyPoint[min].compareTo(copyPoint[j]) > 0)
                        min = j;
                } else {
                    if (count >= 3) {
                        if (copyPoint[min].compareTo(copyPoint[0]) > 0) {
                            min = 0;
                            lineSet.add(new LineSegment(copyPoint[min], copyPoint[max])); // In finding longest segments, each segment will be counted repeatedly four times in four different slope orders. To avoid adding redundant segment, only segment with its slope order matching nature order will be added into the list.
                        }
                    }
                    max = j;
                    min = j;
                    count = 1;
                    currenSlope = copyPoint[j].slopeTo(copyPoint[0]);
                }
            }
            if (count >= 3) {
                if (copyPoint[min].compareTo(copyPoint[0]) > 0) {
                    min = 0;
                    lineSet.add(new LineSegment(copyPoint[min], copyPoint[max]));
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
