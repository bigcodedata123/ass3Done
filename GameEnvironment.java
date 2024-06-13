import java.util.ArrayList;
import java.util.List;

public class GameEnvironment {
    private List<Collidable> collidables;
    private final int screenWidth;
    private final int screenHeight;

    public GameEnvironment(int screenWidth, int screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.collidables = new ArrayList<>();
    }

    public List<Collidable> getCollidables() {
        return this.collidables;
    }

    public int getScreenWidth() {
        return this.screenWidth;
    }
    public int getScreenHeight() {
        return this.screenHeight;
    }

    // add the given collidable to the environment.
    public void addCollidable(Collidable c) {
        collidables.add(c);
    }

    public CollisionInfo getClosestCollision(Line trajectory) {
        CollisionInfo closestCollision = null;
        double closestDistance = Double.MAX_VALUE;
        for (Collidable c : collidables) {
            Point intersect = trajectory.closestIntersectionToStartOfLine(c.getCollisionRectangle());
            if (intersect != null) {
                double distance = trajectory.start().distance(intersect);
                if (distance < closestDistance) {
                    closestDistance = distance;
                    closestCollision = new CollisionInfo(intersect, c);
                }

            }

        }
        return closestCollision;
    }
}