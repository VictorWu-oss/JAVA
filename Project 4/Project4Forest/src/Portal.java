import processing.core.PImage;
import java.util.List;

public class Portal extends Entity implements AnimatedEntity{
    public static final String PORTAL_KEY = "portal";
    public static final int PORTAL_ANIMATION_PERIOD_IDX = 0;
    public static final int PORTAL_NUM_PROPERTIES = 1;
    private final double animationPeriod;

    public Portal(String id, Point position, List<PImage> images, double animationPeriod) {
        super(id, position, images);
        this.animationPeriod = animationPeriod;
    }

    public double getAnimationPeriod() {
        return this.animationPeriod;
    }

    public static Portal createPortal(String id, Point position, double animationPeriod, List<PImage> images) {
        return new Portal(id, position, images, animationPeriod);
    }

    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore){
        scheduler.scheduleEvent(this, AnimationAction.createAnimationAction(this, 0), getAnimationPeriod());
    }

    @Override
    public boolean isPassable(){
        return true;
    }
}

