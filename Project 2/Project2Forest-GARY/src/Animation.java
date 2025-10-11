public class Animation extends Action{
    public Animation(Animated entity, int repeatCount) {
        this.entity = entity;
        this.world = null;
        this.imageStore = null;
        this.repeatCount = repeatCount;
    }

    public void executeAction(EventScheduler scheduler) {
        this.entity.nextImage();

        if (this.repeatCount != 1) {
            scheduler.scheduleEvent(this.entity, new Animation(this.entity, Math.max(this.repeatCount - 1, 0)), this.entity.getAnimationPeriod());
        }
    }
}
