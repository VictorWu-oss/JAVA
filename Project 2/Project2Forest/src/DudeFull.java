import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import processing.core.PImage;

public class DudeFull extends Dude{
    public DudeFull(String id, Point position, List<PImage> images, double actionPeriod, double animationPeriod, int resourceLimit, int resourceCount) {
        super(id, position, images, actionPeriod, animationPeriod, resourceLimit, resourceCount);
    }

    public static DudeFull createDudeFull(String id, Point position, double actionPeriod, double animationPeriod, int resourceLimit, List<PImage> images) {
        return new DudeFull(id, position, images, actionPeriod, animationPeriod, resourceLimit, 0);
    }

    @Override
    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        Optional<Entity> fullTarget = world.findNearest(this.getPosition(), House.class);

        if (fullTarget.isPresent() && this.moveTo(world, (Entity)fullTarget.get(), scheduler)) {
            this.transformDude(world, scheduler, imageStore);
        } else {
            scheduler.scheduleEvent(this, ActivityAction.createActivityAction(this, world, imageStore), this.getActionPeriod());
        }
    }

    @Override
    public boolean transformDude(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        DudeNotFull dude = DudeNotFull.createDudeNotFull(this.getId(), this.getPosition(), this.getActionPeriod(), this.getAnimationPeriod(), this.resourceLimit, imageStore.getImageList(DUDE_KEY));

        world.removeEntity(scheduler, this);

        world.addEntity(dude);
        dude.scheduleActions(scheduler, world, imageStore);
        return true;
    }

    @Override
    public boolean moveTo(WorldModel world, Entity target, EventScheduler scheduler) {
        if (this.getPosition().adjacent(target.getPosition())) {
            return true;
        } else {
            Point nextPos = this.nextPosition(world, target.getPosition(), false);

            if (!this.getPosition().equals(nextPos)) {
                if (world.getOccupant(nextPos) != null) {
                    nextPos = nextPosition(world, target.getPosition(), true);
                }
                world.moveEntity(scheduler, this, nextPos);
            }
            return false;
        }
    }
}
