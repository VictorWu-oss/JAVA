import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import processing.core.PImage;

public class DudeNotFull extends Dude {
    public DudeNotFull(String id, Point position, List<PImage> images, double actionPeriod, double animationPeriod, int resourceLimit, int resourceCount) {
        super(id, position, images, actionPeriod, animationPeriod, resourceLimit, resourceCount);
    }

    public static DudeNotFull createDudeNotFull(String id, Point position, double actionPeriod, double animationPeriod, int resourceLimit, List<PImage> images) {
        return new DudeNotFull(id, position, images, actionPeriod, animationPeriod, resourceLimit, 0);
    }

    @Override
    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        List<Class<? extends Entity>> targets = List.of(Tree.class, Sapling.class);
        Optional<Entity> target = world.findNearest(this.getPosition(), targets);

        if (target.isEmpty() || !this.moveTo(world, target.get(), scheduler, imageStore)
                || !this.transformDude(world, scheduler, imageStore)) {

            scheduler.scheduleEvent(this,
                    ActivityAction.createActivityAction(this, world, imageStore),
                    this.getActionPeriod());
        }
    }

    @Override
    public boolean transformDude(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        if (this.resourceCount >= this.resourceLimit) {
            DudeFull dude = DudeFull.createDudeFull(this.getId(), this.getPosition(), this.getActionPeriod(), this.getAnimationPeriod(), this.resourceLimit, imageStore.getImageList(DUDE_KEY));

            world.removeEntity(scheduler, this);
            scheduler.unscheduleAllEvents(this);

            world.addEntity(dude);
            dude.scheduleActions(scheduler, world, imageStore);

            return true;
        }
        return false;
    }

    @Override
    public boolean moveTo(WorldModel world, Entity target, EventScheduler scheduler, ImageStore imageStore) {
        if (this.getPosition().adjacent(target.getPosition())) {
            this.resourceCount += 1;
            if (target instanceof PlantEntity plant) {
                plant.setHealth(plant.getHealth() - 1);
            }
            return true;
        } else {
            Point nextPos = this.nextPosition(world, target.getPosition(), false);

            if (!this.getPosition().equals(nextPos)) {
                Optional<Entity> occupant = world.getOccupant(nextPos);

                if (occupant.isPresent()){
                    return false;
                }
                world.moveEntity(scheduler, this, nextPos);
            }
            return false;
        }
    }
}