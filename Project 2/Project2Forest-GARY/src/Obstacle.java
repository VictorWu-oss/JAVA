import processing.core.PImage;
import java.util.List;

public class Obstacle extends Animated{
    public Obstacle(String id, Point position, double animationPeriod, List<PImage> images) {
        this.animationPeriod = animationPeriod;
        this.position = position;
        this.id = id;
        this.images = images;
    }

}
