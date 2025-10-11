import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import processing.core.PImage;

public class Car extends ActiveAnimatedEntity implements MovementEntity {
    public static final String CAR_KEY = "car";
    public static final int CAR_ANIMATION_PERIOD_IDX = 0;
    public static final int CAR_ACTION_PERIOD_IDX = 1;
    public static final int CAR_NUM_PROPERTIES = 2;

    public Car(String id, Point position, List<PImage> images, double actionPeriod, double animationPeriod){
        super(id, position, images, actionPeriod, animationPeriod);
    }
    public static Car createCar(String id, Point position, double actionPeriod, double animationPeriod, List<PImage> images) {
        return new Car(id, position, images, actionPeriod, animationPeriod);
    }

    @Override
    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        List<Class<? extends Entity>> plantTargets = List.of(Tree.class, Sapling.class, Stump.class);
        Optional<Entity> target = world.findNearest(this.getPosition(), plantTargets);

        if (target.isPresent()) {
            if (this.moveTo(world, target.get(), scheduler, imageStore)) {
                // Remove plant on arrival
                world.removeEntity(scheduler, target.get());
                System.out.println("Car removed plant: " + target.get().getId());
            }
        }

        // Schedule next action
        scheduler.scheduleEvent(this,
                ActivityAction.createActivityAction(this, world, imageStore),
                this.getActionPeriod());
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

}
