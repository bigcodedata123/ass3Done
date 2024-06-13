import biuoop.DrawSurface;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public interface Sprite {
    // draw the sprite to the screen
    void drawOn(DrawSurface d);
    // notify the sprite that time has passed
    void timePassed();

}
