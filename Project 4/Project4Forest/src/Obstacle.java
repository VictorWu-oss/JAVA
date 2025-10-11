import processing.core.PImage;
import java.util.List;

public class Obstacle extends Entity implements AnimatedEntity{
    public static final String OBSTACLE_KEY = "obstacle";
    public static final int OBSTACLE_ANIMATION_PERIOD_IDX = 0;
    public static final int OBSTACLE_NUM_PROPERTIES = 1;
    private final double animationPeriod;

    public Obstacle(String id, Point position, List<PImage> images, double animationPeriod) {
        super(id, position, images);
        this.animationPeriod = animationPeriod;
    }

    public double getAnimationPeriod() {
        return this.animationPeriod;
    }

    public static Obstacle createObstacle(String id, Point position, double animationPeriod, List<PImage> images) {
        return new Obstacle(id, position, images, animationPeriod);
    }

    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore){
        scheduler.scheduleEvent(this, AnimationAction.createAnimationAction(this, 0), getAnimationPeriod());
    }

}
