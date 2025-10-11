import java.util.List;
import processing.core.PImage;

public class Sapling extends PlantEntity {
    public static final double SAPLING_ACTION_ANIMATION_PERIOD = 1.000;
    public static final int SAPLING_HEALTH_LIMIT = 5;
    public static final String SAPLING_KEY = "sapling";
    public static final int SAPLING_HEALTH_IDX = 0;
    public static final int SAPLING_NUM_PROPERTIES = 1;

    public Sapling(String id, Point position, List<PImage> images, double actionPeriod, double animationPeriod, int health, int healthLimit){
        super(id, position, images, actionPeriod, animationPeriod, health, healthLimit);
    }

    public static Sapling createSapling(String id, Point position, List<PImage> images, int health) {
        return new Sapling(id, position, images, SAPLING_ACTION_ANIMATION_PERIOD, SAPLING_ACTION_ANIMATION_PERIOD, health, SAPLING_HEALTH_LIMIT);
    }

    @Override
    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        this.setHealth(this.getHealth() + 1);
        if (!this.transformPlant(world, scheduler, imageStore)) {
            scheduler.scheduleEvent(this, ActivityAction.createActivityAction(this, world, imageStore), this.getActionPeriod());
        }
    }

    @Override
    public boolean transformPlant(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        if (this.getHealth() <= 0) {
            Stump stump = Stump.createStump(Stump.STUMP_KEY + "_" + this.getId(), this.getPosition(), imageStore.getImageList(Stump.STUMP_KEY));

            world.removeEntity(scheduler, this);

            world.addEntity(stump);

            return true;
        } else if (this.getHealth() >= this.getHealthLimit()) {
            Tree tree = Tree.createTree(Tree.TREE_KEY + "_" + this.getId(), this.getPosition(), Functions.getNumFromRange(Tree.TREE_ACTION_MAX, Tree.TREE_ACTION_MIN), Functions.getNumFromRange(Tree.TREE_ANIMATION_MAX, Tree.TREE_ANIMATION_MIN), Functions.getIntFromRange(Tree.TREE_HEALTH_MAX, Tree.TREE_HEALTH_MIN), imageStore.getImageList(Tree.TREE_KEY));

            world.removeEntity(scheduler, this);

            world.addEntity(tree);
            tree.scheduleActions(scheduler, world, imageStore);

            return true;
        }

        return false;
    }
}
