import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {
    private SpriteCollection sprites;
    private GameEnvironment environment;
    private GUI gui;
    private Sleeper sleeper;
    private Paddle paddle;
    public static final Color VERY_LIGHT_RED = new Color(255, 102, 102, 255);
    public static List <Ball> balls = new ArrayList<>();



    public Game() {
        this.sprites = new SpriteCollection();
        this.environment = new GameEnvironment(800, 600);
        this.gui = new GUI("NEW GAME", this.environment.getScreenWidth(), this.environment.getScreenHeight());
        this.sleeper = new Sleeper();
        this.paddle=new Paddle(new Rectangle(new Point(400-60,570),90,23),VERY_LIGHT_RED, gui.getKeyboardSensor(),800);
        paddle.addToGame(this);
    }

    public void addCollidable(Collidable c) {
        this.environment.addCollidable(c);
    }

    public void addSprite(Sprite s) {
        this.sprites.addSprite(s);
    }

    public void addBallToList(Ball b) {
        balls.add(b);
    }


    // Initialize a new game: create the Blocks and Ball (and Paddle)
    // and add them to the game.
    public void initialize() {
        int blockWidth = 55;
        int blockHeight = 23;
        int heightGap = 120;
        int numOfBalls=100;
        Random random = new Random();

        List<Collidable> blocks = new ArrayList<>();
        blocks.add(new Block(new Rectangle(new Point(0, 0), 800, 10), Color.BLACK));    // גבול עליון
        blocks.add(new Block(new Rectangle(new Point(0, 590), 800, 10), Color.BLACK));  // גבול תחתון
        blocks.add(new Block(new Rectangle(new Point(0, 10), 10, 580), Color.BLACK));   // גבול שמאלי
        blocks.add(new Block(new Rectangle(new Point(790, 10), 10, 580), Color.BLACK)); // גבול ימין

        Color[] colors = {Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.BLUE, Color.MAGENTA};
        int screenWidth = this.environment.getScreenWidth();
        int screenHeight = this.environment.getScreenHeight();

        for (int row = 0; row < 6; row++) {
            int numBlocks = 12 - row;
            int y = heightGap + row * blockHeight;
            for (int col = 0; col < numBlocks; col++) {
                int x = screenWidth - 10 - (numBlocks - col) * blockWidth;
                Point upperLeft = new Point(x, y);
                Rectangle rect = new Rectangle(upperLeft, blockWidth, blockHeight);
                Block block = new Block(rect, colors[row]);
                block.addToGame(this);
            }
        }

        for (Collidable block : blocks) {
            this.environment.addCollidable(block);
        }

        for (Collidable block : blocks) {
            if (Block.class.isAssignableFrom(block.getClass())) {
                this.sprites.addSprite((Block) block);
            }
        }


        for(int i=0; i<numOfBalls;i++) {

            double startX = 30 + random.nextDouble() * (650 - 30);
            double startY = 500+ random.nextDouble();
            Ball ball = new Ball(new Point(startX, startY), 8, Color.black, 0, 0, screenWidth, screenHeight, this.environment);
            ball.setVelocity(Velocity.fromAngleAndSpeed(random.nextInt(360), 7));
            ball.addToGame(this);
            addBallToList(ball);
        }
    }

    // Run the game -- start the animation loop.
    public void run() {
        int framesPerSecond = 60;
        int millisecondsPerFrame = 1000 / framesPerSecond;

        while (true) {
            long startTime = System.currentTimeMillis(); // timing

            DrawSurface d = this.gui.getDrawSurface();
            this.sprites.drawAllOn(d);
            this.gui.show(d);

            // Notify all sprites that time has passed
            this.sprites.notifyAllTimePassed();

            // timing and sleep
            long usedTime = System.currentTimeMillis() - startTime;
            long milliSecondLeftToSleep = millisecondsPerFrame - usedTime;
            if (milliSecondLeftToSleep > 0) {
                this.sleeper.sleepFor(milliSecondLeftToSleep);
            }
        }
    }



    public static void main(String[] args) {
        Game game = new Game();
        game.initialize();
        game.run();
    }
}
