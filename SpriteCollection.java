import biuoop.DrawSurface;
import java.util.ArrayList;
import java.util.List;

public class SpriteCollection {
    private List<Sprite> sprites;

    public SpriteCollection() {
        this.sprites = new ArrayList<>();
    }

    public void addSprite(Sprite s) {
        this.sprites.add(s);

        }



    // call timePassed() on all sprites.
    public void notifyAllTimePassed(){
        for (Sprite s: this.sprites) {
            s.timePassed();
        }

    }

    // call drawOn(d) on all sprites.
    public void drawAllOn(DrawSurface d){
        for (Sprite s: this.sprites) {
            s.drawOn(d);
        }
}
}
