public class CollisionInfo {
    private Point collisionPoint;
    private Collidable collisionObject;

    public CollisionInfo(Point collisionPoint, Collidable collisionObject) {
        this.collisionPoint = collisionPoint;
        this.collisionObject = collisionObject;
    }

    // Method to get the collision point
    public Point collisionPoint() {
        return collisionPoint;
    }

    // Method to get the collision object
    public Collidable collisionObject() {
        return collisionObject;
    }
}