public class AnimationAction extends Action{
    private final int repeatCount;

    public AnimationAction(Entity entity, int repeatCount) {
        super(entity);
        this.repeatCount = repeatCount;
    }

    public static Action createAnimationAction(Entity entity, int repeatCount) {
        return new AnimationAction(entity, repeatCount);
    }

    public void executeAction(EventScheduler scheduler) {
        this.entity.nextImage();

        if (repeatCount != 1 && entity instanceof AnimatedEntity animated) {
            scheduler.scheduleEvent(
                    entity,
                    new AnimationAction(entity, Math.max(repeatCount - 1, 0)),
                    animated.getAnimationPeriod()
            );
        }
    }
}
