// Name: Yonatan Omer
// ID: 322624693
/**
 * The Velocity class represents the velocity of an object in 2D space.
 */
public class Velocity {
    // Variables to hold the change in position along the x and y axes
    private double dx;
    private double dy;

    /**
     * Constructs a Velocity with the given changes in x and y.
     * @param dx The change in x-coordinate.
     * @param dy The change in y-coordinate.
     */
    public Velocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }


    /**
     * Gets the change in x-coordinate.
     * @return The change in x-coordinate.
     */
    public double getDx() {
        return dx;
    }

    /**
     * Gets the change in y-coordinate.
     * @return The change in y-coordinate.
     */
    public double getDy() {
        return dy;
    }

    /**
     * Applies the velocity to a given point, returning a new point.
     * @param p The point to which the velocity is applied.
     * @return The new point after applying the velocity.
     */
    public Point applyToPoint(Point p) {
        double newX = p.getX() + dx;
        double newY = p.getY() + dy;
        return new Point(newX, newY);
    }

    /**
     * Creates a Velocity instance from an angle and speed.
     * @param angle The angle of the velocity vector in degrees.
     * @param speed The magnitude of the velocity.
     * @return A new Velocity instance.
     */
    public static Velocity fromAngleAndSpeed(double angle, double speed) {
        double dx = Math.sin(Math.toRadians(angle)) * speed;
        double dy = -Math.cos(Math.toRadians(angle)) * speed;
        return new Velocity(dx, dy);
    }



}
