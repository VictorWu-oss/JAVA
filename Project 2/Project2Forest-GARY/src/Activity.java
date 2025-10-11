public class Activity extends Action{
    public Activity(Animated entity, WorldModel world, ImageStore imageStore) {
        this.entity = entity;
        this.world = world;
        this.imageStore = imageStore;
        this.repeatCount = 0;
    }

    public void executeAction(EventScheduler scheduler) {
        Active activeEntity = (Active)this.entity;
        activeEntity.executeActivity(this.world, this.imageStore, scheduler);
    }
}
