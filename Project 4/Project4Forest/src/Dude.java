import processing.core.PImage;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public abstract class Dude extends ActiveAnimatedEntity implements MovementEntity {
    public static final String DUDE_KEY = "dude";
    public static final int DUDE_ACTION_PERIOD_IDX = 0;
    public static final int DUDE_ANIMATION_PERIOD_IDX = 1;
    public static final int DUDE_RESOURCE_LIMIT_IDX = 2;
    public static final int DUDE_NUM_PROPERTIES = 3;
    public int resourceLimit;
    public int resourceCount;

    public Dude(String id, Point position, List<PImage> images, double actionPeriod, double animationPeriod, int resourceLimit, int resourceCount) {
        super(id, position, images, actionPeriod, animationPeriod);
        this.resourceLimit = resourceLimit;
        this.resourceCount = resourceCount;
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
                        world.getOccupancyCell(p).getClass() == Stump.class ||
                        world.getOccupancyCell(p) instanceof Portal);
    }

    public abstract boolean transformDude (WorldModel world, EventScheduler scheduler, ImageStore imageStore);

}

