import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Random rnd = new Random();
        int numBlocksPerRow = 7;
        int numBlocksPerColumn = 7;
        int blockWidth = 20;  // רוחב בלוקים
        int blockHeight = 20; // גובה בלוקים
        int gap = 30;          // רווח בין בלוקים

        GUI gui = new GUI("Block Collision", 800, 600);
        List<Collidable> blocks = new ArrayList<>();
        blocks.add(new Block(new Rectangle(new Point(0, 0), 800, 10), Color.BLACK));    // גבול עליון
        blocks.add(new Block(new Rectangle(new Point(0, 590), 800, 10), Color.BLACK));  // גבול תחתון
        blocks.add(new Block(new Rectangle(new Point(0, 10), 10, 580), Color.BLACK));   // גבול שמאלי
        blocks.add(new Block(new Rectangle(new Point(790, 10), 10, 580), Color.black)); // גבול ימין


        // הוספת בלוקים באמצע המסך
        for (int i = 0; i < numBlocksPerRow; i++) {
            for (int j = 0; j < numBlocksPerColumn; j++) {
                int x = 50 + i * (blockWidth + gap); // התזוזה האופקית והוספת הרווח
                int y = 50 + j * (blockHeight + gap); // התזוזה האנכית והוספת הרווח
                blocks.add(new Block(new Rectangle(new Point(x, y), blockWidth, blockHeight), Color.WHITE));

            }
        }


        GameEnvironment ge = new GameEnvironment(800, 600);

        // הוספת הבלוקים לסביבת המשחק
        for (Collidable block : blocks) {
            ge.addCollidable(block);
        }

        Ball[] balls = new Ball[numBlocksPerRow * numBlocksPerColumn];

        // יצירת כדורים
        for (int i = 0; i < numBlocksPerRow; i++) {
            for (int j = 0; j < numBlocksPerColumn; j++) {
                balls[j + i * numBlocksPerRow] = new Ball(new Point(400, 300), 5, Color.RED, 800, 600, 800, 600,ge);
                balls[j + i * numBlocksPerRow].setVelocity(Velocity.fromAngleAndSpeed(rnd.nextInt(360), 5));
            }
        }

        while (true) {
            Sleeper sleeper = new Sleeper();
            sleeper.sleepFor(20);
            DrawSurface d = gui.getDrawSurface();

            // ציור בלוקים
            for (Collidable block : blocks) {
                if (block instanceof Block) {
                    ((Block) block).drawOn(d);
                }
            }

            // ציור והזזת כדורים
            for (Ball ball : balls) {
                ball.drawOn(d);
                ball.moveOneStep();
            }

            gui.show(d);
        }
    }
}
