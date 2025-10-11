import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import processing.core.PImage;

public class Fairy extends ActiveAnimatedEntity implements MovementEntity {
    public static final String FAIRY_KEY = "fairy";
    public static final int FAIRY_ANIMATION_PERIOD_IDX = 0;
    public static final int FAIRY_ACTION_PERIOD_IDX = 1;
    public static final int FAIRY_NUM_PROPERTIES = 2;

    public Fairy(String id, Point position, List<PImage> images, double actionPeriod, double animationPeriod){
        super(id, position, images, actionPeriod, animationPeriod);
    }
    public static Fairy createFairy(String id, Point position, double actionPeriod, double animationPeriod, List<PImage> images) {
        return new Fairy(id, position, images, actionPeriod, animationPeriod);
    }
    @Override
    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        Optional<Entity> fairyTarget = world.findNearest(this.getPosition(), Stump.class);

        if (fairyTarget.isPresent()) {
            Point tgtPos = fairyTarget.get().getPosition();

            if (this.moveTo(world, fairyTarget.get(), scheduler, imageStore)) {

                // Creates and adds a sampling in place of the Stump
                Sapling sapling = Sapling.createSapling(Sapling.SAPLING_KEY + "_" + fairyTarget.get().getId(), tgtPos, imageStore.getImageList(Sapling.SAPLING_KEY), 0);

                // Add the Sapling
                world.addEntity(sapling);
                sapling.scheduleActions(scheduler, world, imageStore);
            }
        }

        scheduler.scheduleEvent(this, ActivityAction.createActivityAction(this, world, imageStore), this.getActionPeriod());
    }

    @Override
    public boolean moveTo(WorldModel world, Entity target, EventScheduler scheduler, ImageStore imageStore) {
        if (this.getPosition().adjacent(target.getPosition())) {
            world.removeEntity(scheduler, target);
            return true;
        } else {
            Point nextPos = this.nextPosition(world, target.getPosition(), false);

            if (!this.getPosition().equals(nextPos)) {
                if (world.getOccupant(nextPos) !=null){
                    nextPos = nextPosition(world, target.getPosition(), true);
                }
                world.moveEntity(scheduler, this, nextPos);
            }
            return false;
        }
    }

    @Override
    public Point nextPosition(WorldModel world, Point destPos, boolean force) {
        if (path == null || path.isEmpty() || force) {
            path = strategy.computePath(this.getPosition(), destPos,
                    nextPositionPassThrough(world), Point::adjacent, PathingStrategy.CARDINAL_NEIGHBORS);
        }

        if (!(path.isEmpty())) {
            return path.remove(0);
        }
        System.out.println(path);
        return this.getPosition();
    }

    public Predicate<Point> nextPositionPassThrough(WorldModel world){
        return p -> (world.withinBounds(p) && (world.getOccupancyCell(p) ==null || world.getOccupancyCell(p) instanceof Portal));
    }

//    @Override
//    public Point nextPosition(WorldModel world, Point destPos) {
//        int horiz = Integer.signum(destPos.x - this.getPosition().x);
//        Point newPos = new Point(this.getPosition().x + horiz, this.getPosition().y);
//
//        if (horiz == 0 || world.isOccupied(newPos)) {
//            int vert = Integer.signum(destPos.y - this.getPosition().y);
//            newPos = new Point(this.getPosition().x, this.getPosition().y + vert);
//
//            if (vert == 0 || world.isOccupied(newPos)) {
//                newPos = this.getPosition();
//            }
//        }
//
//        return newPos;
//    }
}
