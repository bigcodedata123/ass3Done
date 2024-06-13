import biuoop.DrawSurface;
import java.awt.Color;
import java.net.CookieHandler;
import java.util.List;

import biuoop.KeyboardSensor;

public class Paddle implements Sprite, Collidable {
    private KeyboardSensor keyboard;
    private Rectangle paddleShape;
    private final Color color;
    private int gameWidth;
    public static final double EPSILON = 0.001;

    public static boolean threshold(double a, double b, double epsilon) {
        return Math.abs(a - b) < epsilon;
    }

    public Paddle(Rectangle paddleShape, Color color, KeyboardSensor keyboard,int gameWidth) {
        this.paddleShape = paddleShape;
        this.color = color;
        this.keyboard = keyboard;
        this.gameWidth = gameWidth;
    }


    public void moveLeft() {
        double newX = this.paddleShape.getUpperLeft().getX() - 7; // Move left by 5 pixels
        double newY = this.paddleShape.getUpperLeft().getY(); // Y coordinate remains the same
        if (newX < 10) {
            newX = gameWidth - 10 - paddleShape.getWidth(); // Set newX to the rightmost position
        }
        Rectangle newPaddle = new Rectangle(new Point(newX, newY), this.paddleShape.getWidth(), this.paddleShape.getHeight());
        // Check for collisions with balls
        for (Ball ball : Game.balls) {
            if (newPaddle.contains(ball.getCenter())) {
                return;
            }
        }
        // Update paddle's position
        this.paddleShape = newPaddle;
    }

    public void moveRight() {
        double newX = this.paddleShape.getUpperLeft().getX() + 7; // Move right by 5 pixels
        double newY = this.paddleShape.getUpperLeft().getY(); // Y coordinate remains the same
        if (newX > gameWidth - 10 - paddleShape.getWidth()) {
            newX = 10; // Set newX to the leftmost position
        }
        Rectangle newPaddle = new Rectangle(new Point(newX, newY), this.paddleShape.getWidth(), this.paddleShape.getHeight());
        // Check for collisions with balls
        for (Ball ball : Game.balls) {
            if (newPaddle.contains(ball.getCenter())) {
                return;
            }
        }
        this.paddleShape = newPaddle;
    }


    // Sprite
    @Override
    public void timePassed() {
        if (keyboard.isPressed(KeyboardSensor.LEFT_KEY)) {
            moveLeft();
        } else if (keyboard.isPressed(KeyboardSensor.RIGHT_KEY)) {
            moveRight();
        }
    }

    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(this.color);
        d.fillRectangle((int) this.paddleShape.getUpperLeft().getX(),
                (int) this.paddleShape.getUpperLeft().getY(),
                (int) this.paddleShape.getWidth(),
                (int) this.paddleShape.getHeight());

        d.setColor(Color.BLACK);
        d.drawRectangle((int) this.paddleShape.getUpperLeft().getX(),
                (int) this.paddleShape.getUpperLeft().getY(),
                (int) this.paddleShape.getWidth(),
                (int) this.paddleShape.getHeight());
    }

    // Collidable
    @Override
    public Rectangle getCollisionRectangle() {
        return this.paddleShape;
    }

    @Override
    public Velocity hit(Point collisionPoint, Velocity currentVelocity) {
        double sectionWidth = this.paddleShape.getWidth() / 5;
        double paddleX = this.paddleShape.getUpperLeft().getX();
        double paddleY = this.paddleShape.getUpperLeft().getY();
        double paddleWidth = this.paddleShape.getWidth();
        double paddleHeight = this.paddleShape.getHeight();
        double hitX = collisionPoint.getX();
        double hitY = collisionPoint.getY();

        // Calculate which section of the paddle was hit
        int section;
        double currentSpeed = Math.sqrt(currentVelocity.getDx() * currentVelocity.getDx() + currentVelocity.getDy() * currentVelocity.getDy());

        if (threshold(hitX, paddleX, EPSILON)) {
            return new Velocity(-Math.abs(currentVelocity.getDx()), currentVelocity.getDy());
        }

        if (threshold(hitX, paddleX+paddleWidth, EPSILON)) {
            return new Velocity(Math.abs(currentVelocity.getDx()), currentVelocity.getDy());
        }


        section = (int) Math.round((hitX - paddleX) / sectionWidth);

        switch (section) {
            case 0:
                if (threshold(hitX, paddleX, EPSILON) && threshold(hitY, paddleY, EPSILON)) {
                    return new Velocity(-Math.abs(currentVelocity.getDx()), -Math.abs(currentVelocity.getDy()));
                }
                else{
                    return Velocity.fromAngleAndSpeed(300, currentSpeed);
                }


            case 1:
                return Velocity.fromAngleAndSpeed(330, currentSpeed);


            case 2:
                if (threshold(paddleY, hitY, EPSILON)) {
                    return new Velocity(currentVelocity.getDx(), -Math.abs(currentVelocity.getDy()));
                }


            case 3:
                return Velocity.fromAngleAndSpeed(30, currentSpeed);


            case 4, 5:

                    return Velocity.fromAngleAndSpeed(60, currentSpeed);


            default:
                return currentVelocity;

        }


    }


    // Add this paddle to the game.
    public void addToGame(Game g) {
        g.addSprite(this);
        g.addCollidable(this);
    }
}