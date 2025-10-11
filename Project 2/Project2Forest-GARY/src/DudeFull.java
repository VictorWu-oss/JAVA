import processing.core.PImage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DudeFull extends Dude{

    public DudeFull(String id, Point position, double actionPeriod, double animationPeriod, int resourceLimit, List<PImage> images) {
        this.id = id;
        this.position = position;
        this.actionPeriod = actionPeriod;
        this.animationPeriod = animationPeriod;
        this.resourceLimit = resourceLimit;
        this.images = images;
    }

    public DudeFull(String id, Point position, double actionPeriod, double animationPeriod, int resourceLimit, List<PImage> images, List<Point> path) {
        this(id,position,actionPeriod,animationPeriod,resourceLimit,images);
        this.path = path;
    }

    public void transform(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        DudeNotFull dude = new DudeNotFull(this.id, this.position, this.actionPeriod, this.animationPeriod, this.resourceLimit, this.images, this.path);

        world.removeEntity(scheduler, this);
        world.addEntity(dude);

        dude.scheduleActions(scheduler, world, imageStore);
    }

    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        Optional<Entity> fullTarget = world.findNearest(this.position, new ArrayList<>(List.of(House.class)));

        if (fullTarget.isPresent() && this.moveTo(world, fullTarget.get(), scheduler)) {
            this.transform(world, scheduler, imageStore);
        } else {
            scheduler.scheduleEvent(this, new Activity(this, world, imageStore), this.actionPeriod);
        }
    }

    public boolean moveTo(WorldModel world, Entity target, EventScheduler scheduler) {
        if (this.position.adjacent(target.position)) {
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
