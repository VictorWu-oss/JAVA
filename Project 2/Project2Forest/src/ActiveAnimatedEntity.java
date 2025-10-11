import processing.core.PImage;
import java.util.List;

public abstract class ActiveAnimatedEntity extends Entity implements AnimatedEntity, ActivityPerformer{
    private final double actionPeriod;
    private final double animationPeriod;

    public ActiveAnimatedEntity(String id, Point position, List<PImage> images, double actionPeriod, double animationPeriod) {
        super(id, position, images);
        this.actionPeriod = actionPeriod;
        this.animationPeriod = animationPeriod;
    }

    public double getAnimationPeriod() {
        return this.animationPeriod;
    }

    public double getActionPeriod() {
        return this.actionPeriod;
    }

    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
        scheduler.scheduleEvent(this,
                new ActivityAction(this, world, imageStore), this.getActionPeriod());
        scheduler.scheduleEvent(this,
                new AnimationAction(this, 0), this.getAnimationPeriod());
    }

    public abstract void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler);
    public List<Point> path;
    protected PathingStrategy strategy = new AStarPathingStrategy();
}
