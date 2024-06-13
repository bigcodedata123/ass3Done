import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Rectangle {
    private final Point upperLeft;
    private final double width;
    private final double height;
    double epsilon = Line.EPSILON;

    // Create a new rectangle with location and width/height.
    public Rectangle(Point upperLeft, double width, double height) {
        this.upperLeft = upperLeft;
        this.width = width;
        this.height = height;
    }

    // Return a (possibly empty) List of intersection points with the specified line.
    public List<Point> intersectionPoints(Line line) {
        List<Point> intersectionPoints = new ArrayList<>();
        List<Line> edges = getEdges();

        for (Line edge : edges) {
            Point intersection = edge.intersectionWith(line);
            if (intersection != null) {
                intersectionPoints.add(intersection);
            }
        }

        return intersectionPoints;
    }

    // Helper method to get the edges of the rectangle as lines.
    private List<Line> getEdges() {
        double x1 = upperLeft.getX();
        double y1 = upperLeft.getY();
        double x2 = upperLeft.getX() + width;
        double y2 = upperLeft.getY() + height;

        List<Line> edges = new ArrayList<>();
        edges.add(new Line(x1, y1, x2, y1)); // Top edge
        edges.add(new Line(x1, y2, x2, y2)); // Bottom edge
        edges.add(new Line(x1, y1, x1, y2)); // Left edge
        edges.add(new Line(x2, y1, x2, y2)); // Right edge

        return edges;
    }


    private void addIntersectionPoint(Line line, Line edge, List<Point> intersectionPoints) {
        Point intersection = line.intersectionWith(edge);
        if (intersection != null && !intersectionPoints.contains(intersection)) {
            intersectionPoints.add(intersection);
        }
    }

    // Return the width and height of the rectangle
    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    // Returns the upper-left point of the rectangle.
    public Point getUpperLeft() {
        return upperLeft;
    }

    boolean threshold ( double a, double b, double epsilon){
        return Math.abs(a - b) < epsilon;
    }

    public boolean contains(Point point) {
        double px = point.getX();
        double py = point.getY();
        double rx = this.getUpperLeft().getX();
        double ry = this.getUpperLeft().getY();

        boolean insideXBounds = (px > rx) && (px < rx + this.width);
        boolean insideYBounds = (py > ry) && (py < ry + this.height);

        boolean onEdgeXBounds = threshold(px, rx, epsilon) || threshold(px, rx + this.width, epsilon);
        boolean onEdgeYBounds = threshold(py, ry, epsilon) || threshold(py, ry + this.height, epsilon);

        return (insideXBounds && insideYBounds) || (onEdgeXBounds && onEdgeYBounds);
    }



}