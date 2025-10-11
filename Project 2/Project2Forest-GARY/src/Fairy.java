import processing.core.PImage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Fairy extends Active implements Interactive{
    public Fairy(String id, Point position, double actionPeriod, double animationPeriod, List<PImage> images){
        this.id = id;
        this.images = images;
        this.position = position;
        this.actionPeriod = actionPeriod;
        this.animationPeriod = animationPeriod;
    }

    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        Optional<Entity> fairyTarget = world.findNearest(this.position, new ArrayList<>(List.of(Stump.class)));

        if (fairyTarget.isPresent()) {
            Point tgtPos = fairyTarget.get().position;

            if (this.moveTo(world, fairyTarget.get(), scheduler)) {

                Sapling sapling = new Sapling("sapling_" + fairyTarget.get().id, tgtPos, imageStore.getImageList("sapling"));

                world.addEntity(sapling);
                sapling.scheduleActions(scheduler, world, imageStore);
            }
        }

        scheduler.scheduleEvent(this, new Activity(this, world, imageStore), this.actionPeriod);
    }

    public boolean moveTo(WorldModel world, Entity target, EventScheduler scheduler) {
        if (this.position.adjacent(target.position)) {
            world.removeEntity(scheduler, target);
            return true;
        } else {
            Point nextPos = nextPosition(world, target.position, false);

            if (!this.position.equals(nextPos)) {
                if (world.getOccupant(nextPos) != null) {
                    nextPos = nextPosition(world, target.position, true);
                }
                world.moveEntity(scheduler, this, nextPos);
            }
            return false;
        }
    }

    public Point nextPosition(WorldModel world, Point destPos, boolean force) {
        if (path == null || path.isEmpty() || force) {
            path = strategy.computePath(this.position, destPos,
                    p -> world.withinBounds(p) && !world.isOccupied(p));
        }

        if (!(path.isEmpty())) {
            return path.remove(0);
        }
        return this.position;
    }

}
