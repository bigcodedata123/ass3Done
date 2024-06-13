// Name: Yonatan Omer
// ID: 322624693
import java.awt.*;
import java.util.ArrayList;
import java.util.List;



public class Line {
    // Constant for comparing doubles with precision
    public static final double EPSILON = 0.001;

    /**
     * Helper method to compare two doubles for equality within a small margin.
     *
     * @param a       The first double to compare
     * @param b       The second double to compare
     * @param EPSILON The precision threshold
     * @return True if the doubles are considered equal within the threshold, false otherwise
     */
    public static boolean threshold(double a, double b, double EPSILON) {
        return Math.abs(a - b) < EPSILON;
    }

    // Variables to hold the coordinates of the line endpoints
    private final double x1;
    private final double y1;
    private final double x2;
    private final double y2;


    /**
     * Constructor that initializes a line using four coordinates.
     *
     * @param x1 The x-coordinate of the starting point
     * @param y1 The y-coordinate of the starting point
     * @param x2 The x-coordinate of the ending point
     * @param y2 The y-coordinate of the ending point
     */
    public Line(double x1, double y1, double x2, double y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    /**
     * Method to calculate the length of the line.
     *
     * @return The length of the line
     */
    public double length() {
        return Math.sqrt(Math.pow(this.x2 - this.x1, 2) + Math.pow(this.y2 - this.y1, 2));
    }

    /**
     * Method to find the midpoint of the line.
     *
     * @return The midpoint of the line
     */
    public Point middle() {
        return new Point((this.x1 + this.x2) / 2, (this.y1 + this.y2) / 2);
    }

    /**
     * Method to get the start point of the line.
     *
     * @return The start point of the line
     */
    public Point start() {
        return new Point(this.x1, this.y1);
    }

    /**
     * Method to get the end point of the line.
     *
     * @return The end point of the line
     */
    public Point end() {
        return new Point(this.x2, this.y2);
    }

    /**
     * Method to check if a point lies on the line segment.
     *
     * @param p The point to check
     * @return True if the point lies on the line segment, false otherwise
     */
    private boolean isPointOnLine(Point p) {
        double minX = Math.min(this.x1, this.x2) - EPSILON;
        double maxX = Math.max(this.x1, this.x2) + EPSILON;
        double minY = Math.min(this.y1, this.y2) - EPSILON;
        double maxY = Math.max(this.y1, this.y2) + EPSILON;

        // Check if the point is within the bounding box
        if (p.getX() < minX || p.getX() > maxX || p.getY() < minY || p.getY() > maxY) {
            return false;
        }

        // Calculate the slope (m) and y-intercept (b) of the line y = mx + b
        double dx = this.x2 - this.x1;
        double dy = this.y2 - this.y1;

        if (threshold(dx, 0, EPSILON)) { // Vertical line
            return threshold(p.getX(), this.x1, EPSILON);
        } else if (threshold(dy, 0, EPSILON)) { // Horizontal line
            return threshold(p.getY(), this.y1, EPSILON);
        } else {
            // For non-vertical and non-horizontal lines
            double slope = dy / dx;
            double yIntercept = this.y1 - slope * this.x1;
            return threshold(p.getY(), slope * p.getX() + yIntercept, EPSILON);
        }
    }


    /**
     * Method to check if this line intersects with another line.
     *
     * @param other The other line to check intersection with
     * @return True if the lines intersect, false otherwise
     */
    public boolean isIntersecting(Line other) {
        return intersectionWith(other) != null;
    }

    /**
     * Method to find the intersection point with another line, if it exists.
     *
     * @param other The other line to find intersection with
     * @return The intersection point if it exists, null otherwise
     */
    public Point intersectionWith(Line other) {
        double x1 = this.x1, y1 = this.y1, x2 = this.x2, y2 = this.y2;
        double x3 = other.x1, y3 = other.y1, x4 = other.x2, y4 = other.y2;

        double denominator = (x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4);
        if (threshold(denominator, 0, EPSILON)) {
            return null; // Lines are parallel or coincident
        }

        double intersectX = ((x1 * y2 - y1 * x2) * (x3 - x4) - (x1 - x2) * (x3 * y4 - y3 * x4)) / denominator;
        double intersectY = ((x1 * y2 - y1 * x2) * (y3 - y4) - (y1 - y2) * (x3 * y4 - y3 * x4)) / denominator;

        Point intersection = new Point(intersectX, intersectY);
        if (isPointOnLine(intersection) && other.isPointOnLine(intersection)) {
            return intersection;
        }

        return null;
    }

    /**
     * Method to check if this line is equal to another line.
     *
     * @param other The other line to compare with
     * @return True if the lines are equal, false otherwise
     */
    public boolean equals(Line other) {
        return (threshold(this.x1, other.x1, EPSILON) && threshold(this.y1, other.y1, EPSILON) &&
                threshold(this.x2, other.x2, EPSILON) && threshold(this.y2, other.y2, EPSILON)) ||
                (threshold(this.x1, other.x2, EPSILON) && threshold(this.y1, other.y2, EPSILON) &&
                        threshold(this.x2, other.x1, EPSILON) && threshold(this.y2, other.y1, EPSILON));
    }

    public Point closestIntersectionToStartOfLine(Rectangle rect){
        List<Point> intersections = rect.intersectionPoints(this);
        if (intersections.isEmpty()) {
            return null;
        }
        Point closestPoint = null;
        double closestDistance = Double.MAX_VALUE;
        Point start = this.start();

        for (Point p : intersections) {
            double distance = start.distance(p);
            if (distance < closestDistance) {
                closestDistance = distance;
                closestPoint = p;
            }
        }

        return closestPoint;
    }


}
