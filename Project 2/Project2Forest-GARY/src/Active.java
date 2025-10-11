import java.util.List;

public abstract class Active extends Animated {
    abstract void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler);
    public List<Point> path;
    protected PathingStrategy strategy = new AStarPathingStrategy();

    @Override
    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
        scheduler.scheduleEvent(this, new Activity(this, world, imageStore), this.actionPeriod);
        scheduler.scheduleEvent(this, new Animation(this, 0), getAnimationPeriod());
    }

}
