public class ActivityAction extends Action {
    private final WorldModel world;
    private final ImageStore imageStore;

    public ActivityAction(Entity entity, WorldModel world, ImageStore imageStore) {
        super(entity);
        this.world = world;
        this.imageStore = imageStore;
    }

    public static ActivityAction createActivityAction(Entity entity, WorldModel world, ImageStore imageStore) {
        return new ActivityAction(entity, world, imageStore);
    }

    public void executeAction(EventScheduler scheduler) {
        if (entity instanceof ActiveAnimatedEntity active) {
            active.executeActivity(world, imageStore, scheduler);
        }

    }
}
