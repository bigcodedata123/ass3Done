// Name: Yonatan Omer
// ID: 322624693

import biuoop.GUI;
import biuoop.DrawSurface;
import biuoop.Sleeper;
import java.awt.Color;

/**
 * Represents a ball in the animation.
 */
public class Ball implements Sprite {
    private static final double MOVE_SLIGHTLY = 0.0001;
    private Point center; // The center point of the ball
    private final int radius; // The radius of the ball
    private final Color color; // The color of the ball
    private Velocity velocity; // The velocity of the ball
    private final int minX; // The minimum x-coordinate value
    private final int minY; // The minimum y-coordinate value
    private final int boardWidth; // The width of the animation board
    private final int boardHeight; // The height of the animation board
    private final GameEnvironment gameEnvironment;

    /**
     * Constructor to initialize the ball.
     *
     * @param center       The center point of the ball
     * @param r            The radius of the ball
     * @param color        The color of the ball
     * @param minX         The minimum x-coordinate value
     * @param minY         The minimum y-coordinate value
     * @param boardWidth   The width of the animation board
     * @param boardHeight  The height of the animation board
     */
    public Ball(Point center, int r, Color color, int minX, int minY, int boardWidth, int boardHeight, GameEnvironment ge) {
        this.center = center;
        this.radius = r;
        this.color = color;
        this.velocity = new Velocity(0, 0);
        this.minX = minX;
        this.minY = minY;
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        this.gameEnvironment = ge;
    }

    /**
     * Get the x-coordinate of the center of the ball.
     *
     * @return The x-coordinate of the center
     */
    public double getX() {
        return this.center.getX();
    }

    /**
     * Get the y-coordinate of the center of the ball.
     *
     * @return The y-coordinate of the center
     */
    public double getY() {
        return this.center.getY();
    }

    public Point getCenter() {
        return this.center;
    }

    /**
     * Get the radius of the ball.
     *
     * @return The radius of the ball
     */
    public int getSize() {
        return this.radius;
    }

    public GameEnvironment getGameEnvironment() {
        return gameEnvironment;
    }

    @Override
    public void timePassed() {
        moveOneStep();
    }

    /**
     * Draw the ball on the given DrawSurface.
     *
     * @param surface The DrawSurface on which to draw the ball
     */
    @Override
    public void drawOn(DrawSurface surface) {
        surface.setColor(this.color);
        surface.fillCircle((int)getX(), (int)getY(), getSize());
    }

    /**
     * Set the velocity of the ball.
     *
     * @param v The velocity to set
     */
    public void setVelocity(Velocity v) {
        this.velocity = v;
    }
    public Velocity getVelocity() {
        return this.velocity;
    }

    /**
     * Set the velocity of the ball.
     *
     * @param dx The change in x-coordinate per step
     * @param dy The change in y-coordinate per step
     */
    public void setVelocity(double dx, double dy) {
        this.velocity = new Velocity(dx, dy);
    }



    /**
     * Move the ball one-step considering the boundaries.
     */
    public void moveOneStep() {
        Point nextCenter = this.velocity.applyToPoint(this.center); // Calculate the potential new position
        Line trajectory = new Line(this.center.getX(),this.center.getY(), nextCenter.getX(), nextCenter.getY());
        CollisionInfo closestCollision = this.gameEnvironment.getClosestCollision(trajectory);


        if (closestCollision != null) {
            Point collisionPoint = closestCollision.collisionPoint();
            // Move the ball slightly before the collision point
            double dx = collisionPoint.getX() - this.center.getX();
            double dy = collisionPoint.getY() - this.center.getY();
            this.center = new Point(this.center.getX() + (dx * MOVE_SLIGHTLY), this.center.getY() + (dy * MOVE_SLIGHTLY));
            // Notify the hit object and update the velocity
            Collidable hitObject = closestCollision.collisionObject();
            this.velocity = hitObject.hit(collisionPoint, this.velocity);

        } else {
            this.center =nextCenter ; // Move the ball to the next position
        }
    }




    public void addToGame(Game game) {
        game.addSprite(this);
    }

}