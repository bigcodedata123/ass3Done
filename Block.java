// Name: Yonatan Omer
// ID: 322624693

import biuoop.GUI;
import biuoop.DrawSurface;
import biuoop.Sleeper;
import java.awt.Color;



public class Block implements Collidable , Sprite{
    private final Rectangle collisionRectangle;
    private final Color color;

    public Block(Rectangle collisionRectangle, Color color) {
        this.collisionRectangle = collisionRectangle;
        this.color = color;
    }

    public void addToGame(Game game) {
        game.addCollidable(this);
        game.addSprite(this);
    }


    @Override
    public void drawOn(DrawSurface surface) {
        surface.setColor(this.color);
        surface.fillRectangle((int) this.collisionRectangle.getUpperLeft().getX(),
                (int) this.collisionRectangle.getUpperLeft().getY(),
                (int) this.collisionRectangle.getWidth(),
                (int) this.collisionRectangle.getHeight());

        surface.setColor(color.BLACK);
        surface.drawRectangle((int) this.collisionRectangle.getUpperLeft().getX(),
                (int) this.collisionRectangle.getUpperLeft().getY(),
                (int) this.collisionRectangle.getWidth(),
                (int) this.collisionRectangle.getHeight());


    }

    @Override
    public void timePassed() {

    }

    // Helper method to compare doubles with precision
    boolean threshold ( double a, double b, double epsilon){
        return Math.abs(a - b) < epsilon;
    }



    @Override
    public Rectangle getCollisionRectangle() {
        return this.collisionRectangle;
    }

    @Override
    public Velocity hit(Point collisionPoint, Velocity currentVelocity) {
        double x = collisionPoint.getX();
        double y = collisionPoint.getY();
        double rectX = collisionRectangle.getUpperLeft().getX();
        double rectY = collisionRectangle.getUpperLeft().getY();
        double rectWidth = collisionRectangle.getWidth();
        double rectHeight = collisionRectangle.getHeight();
        double epsilon = Line.EPSILON;


        // Check for collision with the upper-left corner
        if (threshold(x, rectX, epsilon) && threshold(y, rectY, epsilon)) {
            return new Velocity(-Math.abs(currentVelocity.getDx()), -Math.abs(currentVelocity.getDy()));
        }

        // Check for collision with the upper-right corner
        if (threshold(x, rectX + rectWidth, epsilon) && threshold(y, rectY, epsilon)) {
            return new Velocity(Math.abs(currentVelocity.getDx()), -Math.abs(currentVelocity.getDy()));
        }

        // Check for collision with the lower-left corner
        if (threshold(x, rectX, epsilon) && threshold(y, rectY + rectHeight, epsilon)) {
            return new Velocity(-Math.abs(currentVelocity.getDx()), Math.abs(currentVelocity.getDy()));
        }

        // Check for collision with the lower-right corner
        if (threshold(x, rectX + rectWidth, epsilon) && threshold(y, rectY + rectHeight, epsilon)) {
            return new Velocity(Math.abs(currentVelocity.getDx()), Math.abs(currentVelocity.getDy()));
        }

        // Check for collision with the upper edge
        if (threshold(y, rectY, epsilon)) {
            return new Velocity(currentVelocity.getDx(), -Math.abs(currentVelocity.getDy()));
        }

        // Check for collision with the lower edge
        if (threshold(y, rectY + rectHeight, epsilon)) {
            return new Velocity(currentVelocity.getDx(), Math.abs(currentVelocity.getDy()));
        }

        // Check for collision with the left edge
        if (threshold(x, rectX, epsilon)) {
            return new Velocity(-Math.abs(currentVelocity.getDx()), currentVelocity.getDy());
        }

        // Check for collision with the right edge
        if (threshold(x, rectX + rectWidth, epsilon)) {
            return new Velocity(Math.abs(currentVelocity.getDx()), currentVelocity.getDy());
        }

        return currentVelocity;
    }


}
