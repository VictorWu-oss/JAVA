import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import processing.core.PImage;

public class DudeSuper extends ActiveAnimatedEntity implements MovementEntity{
    public static final String SUPERDUDE_KEY = "superdude";
    public static final int SUPERDUDE_ACTION_PERIOD_IDX = 0;
    public static final int SUPERDUDE_ANIMATION_PERIOD_IDX = 1;
    public static final int SUPERDUDE_RESOURCE_LIMIT_IDX = 2;
    public static final int SUPERDUDE_NUM_PROPERTIES = 3;
    public int resourceLimit;
    public int resourceCount;

    public DudeSuper(String id, Point position, List<PImage> images, double actionPeriod, double animationPeriod, int resourceLimit, int resourceCount) {
        super(id, position, images, actionPeriod, animationPeriod);
        this.resourceLimit = resourceLimit;
        this.resourceCount = resourceCount;
    }

    public static DudeSuper createDudeSuper(String id, Point position, double actionPeriod, double animationPeriod, int resourceLimit, List<PImage> images) {
        return new DudeSuper(id, position, images, actionPeriod, animationPeriod, resourceLimit, 99);
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

    private Predicate<Point> nextPositionPassThrough(WorldModel world) {
        return p -> world.withinBounds(p) &&
                (world.getOccupancyCell(p) == null ||
                        world.getOccupancyCell(p) instanceof Stump ||
                        world.getOccupancyCell(p) instanceof Portal); // optional: || instanceof Darkmatter
    }

    @Override
    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        List<Class<? extends Entity>> targets = List.of(Fairy.class);
        Optional<Entity> targetOpt = world.findNearest(this.getPosition(), targets);

        if (targetOpt.isPresent()) {
            Entity target = targetOpt.get();

            if (this.getPosition().adjacent(target.getPosition())) {
                Point fairyPos = target.getPosition();
                Point carPos = new Point(fairyPos.x + 1, fairyPos.y); // position to the right

                // Remove the Fairy and unschedule its events
                world.removeEntity(scheduler, target);
                scheduler.unscheduleAllEvents(target);

                // Check if carPos is valid for placement
                if (world.withinBounds(carPos) && !world.isOccupied(carPos)) {
                    Car car = Car.createCar("car" + target.getId(), carPos, 0.8, 0.5, imageStore.getImageList("car"));
                    world.addEntity(car);
                    car.scheduleActions(scheduler, world, imageStore);
                    System.out.println("Fairy transformed into Car at " + carPos);
                } else {

                    Car car = Car.createCar("car" + target.getId(), fairyPos, 0.8, 0.5, imageStore.getImageList("car"));
                    world.addEntity(car);
                    car.scheduleActions(scheduler, world, imageStore);
                    System.out.println("Fairy transformed into Car at Fairy's original position " + fairyPos);
                }
            } else {
                this.moveTo(world, target, scheduler, imageStore);
            }
        }

        scheduler.scheduleEvent(this,
                ActivityAction.createActivityAction(this, world, imageStore),
                this.getActionPeriod());
    }

    // Doesnt transform

    @Override
    public boolean moveTo(WorldModel world, Entity target, EventScheduler scheduler, ImageStore imageStore) {
        if (this.getPosition().adjacent(target.getPosition())) {
            if (target instanceof PlantEntity plant) {
                plant.setHealth(plant.getHealth() - 1, world, scheduler, imageStore);
            }
            return true;
        } else {
            Point nextPos = this.nextPosition(world, target.getPosition(), false);

            if (!this.getPosition().equals(nextPos) && world.isOccupied(nextPos)) {
                return false;
            }

            else if (world.getOccupant(nextPos) != null) {
                nextPos = nextPosition(world, target.getPosition(), true);
                world.moveEntity(scheduler, this, nextPos);
            }
        world.moveEntity(scheduler, this, nextPos);
            return false;
        }
    }
}
