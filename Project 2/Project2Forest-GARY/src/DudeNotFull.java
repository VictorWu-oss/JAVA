import processing.core.PImage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class DudeNotFull extends Dude {

    public DudeNotFull(String id, Point position, double actionPeriod, double animationPeriod, int resourceLimit, List<PImage> images) {
        this.id = id;
        this.position = position;
        this.actionPeriod = actionPeriod;
        this.animationPeriod = animationPeriod;
        this.resourceLimit = resourceLimit;
        this.images = images;
    }

    public DudeNotFull(String id, Point position, double actionPeriod, double animationPeriod, int resourceLimit, List<PImage> images, List<Point> path) {
        this(id,position,actionPeriod,animationPeriod,resourceLimit,images);
    }

        public boolean transform(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        if (this.resourceCount >= this.resourceLimit) {
            DudeFull dude = new DudeFull(this.id, this.position, this.actionPeriod, this.animationPeriod, this.resourceLimit, this.images, this.path);

            world.removeEntity(scheduler, this);
            scheduler.unscheduleAllEvents(this);

            world.addEntity(dude);

            dude.scheduleActions(scheduler, world, imageStore);

            return true;
        }

        return false;
    }

    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        Optional<Entity> target = world.findNearest(this.position, new ArrayList<>(Arrays.asList(Tree.class, Sapling.class)));

        if (target.isEmpty() || !this.moveTo(world, target.get(), scheduler) || !this.transform(world, scheduler, imageStore)) {
            scheduler.scheduleEvent(this, new Activity(this, world, imageStore), this.actionPeriod);
        }
    }

    public boolean moveTo(WorldModel world, Entity target, EventScheduler scheduler) {
        Plant plant = (Plant)target;
        if (this.position.adjacent(target.position)) {
            this.resourceCount += 1;
            plant.health--;
            return true;
        } else {
            Point nextPos = this.nextPosition(world, target.position, false);

            if (!this.position.equals(nextPos)) {
                if (world.getOccupant(nextPos) != null) {
                    nextPos = nextPosition(world, target.position, true);
                }
                world.moveEntity(scheduler, this, nextPos);
            }
            return false;
        }
    }
}