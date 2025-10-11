import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.Random;

import processing.core.PImage;

public class Soldier extends ActiveAnimatedEntity implements MovementEntity {
    public static final String SOLDIER_KEY = "soldier";
    public static final int SOLDIER_ANIMATION_PERIOD_IDX = 0;
    public static final int SOLDIER_ACTION_PERIOD_IDX = 1;
    public static final int SOLDIER_NUM_PROPERTIES = 2;

    public Soldier(String id, Point position, List<PImage> images, double actionPeriod, double animationPeriod){
        super(id, position, images, actionPeriod, animationPeriod);
    }
    public static Soldier createSoldier(String id, Point position, double actionPeriod, double animationPeriod, List<PImage> images) {
        return new Soldier(id, position, images, actionPeriod, animationPeriod);
    }

    @Override
    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        Optional<Entity> nearestDude = world.findNearest(this.getPosition(), DudeSuper.class);
        Optional<Entity> nearestCar = world.findNearest(this.getPosition(), Car.class);

        Optional<Entity> target = Optional.empty();

        if (nearestDude.isPresent() && nearestCar.isPresent()) {
            double distDude = this.getPosition().distanceSquared(nearestDude.get().getPosition());
            double distCar = this.getPosition().distanceSquared(nearestCar.get().getPosition());

            target = distDude <= distCar ? nearestDude : nearestCar;
        } else if (nearestDude.isPresent()) {
            target = nearestDude;
        } else if (nearestCar.isPresent()) {
            target = nearestCar;
        }

        if (target.isPresent()) {
            Point tgtPos = target.get().getPosition();

            if (this.moveTo(world, target.get(), scheduler, imageStore)) {
                // Remove the original entity
                world.removeEntity(scheduler, target.get());
                scheduler.unscheduleAllEvents(target.get());

                Random rand = new Random();
                boolean makeFairy = rand.nextBoolean(); // 50/50 chance

                if (makeFairy) {
                    // Create Fairy
                    Fairy fairy = Fairy.createFairy(
                            Fairy.FAIRY_KEY + "_" + target.get().getId(),
                            tgtPos, getActionPeriod(), getAnimationPeriod(),
                            imageStore.getImageList(Fairy.FAIRY_KEY));

                    world.addEntity(fairy);
                    fairy.scheduleActions(scheduler, world, imageStore);
                    System.out.println("Transformed into Fairy at " + tgtPos);

                } else {
                    // Create Sapling
                    Sapling sapling = Sapling.createSapling(
                            Sapling.SAPLING_KEY + "_" + target.get().getId(),
                            tgtPos,
                            imageStore.getImageList(Sapling.SAPLING_KEY),
                            0
                    );
                    world.addEntity(sapling);
                    sapling.scheduleActions(scheduler, world, imageStore);
                    System.out.println("Transformed into Sapling at " + tgtPos);
                }
            }
        }

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

