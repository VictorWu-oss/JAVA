import processing.core.PImage;
import java.util.List;

public class Sapling extends Plant {
    protected static final int healthLimit = 5;

    public Sapling(String id, Point position, List<PImage> images) {
        this.id = id;
        this.images = images;
        this.position = position;
        this.animationPeriod = 1.0;
        this.actionPeriod = this.animationPeriod;
    }

    void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        this.health++;
        if (!this.transform(world, scheduler, imageStore)) {
            scheduler.scheduleEvent(this, new Activity(this, world, imageStore), this.actionPeriod);
        }
    }

    public boolean transform(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        if (this.health <= 0) {
            Stump stump = new Stump("stump_" + this.id, this.position, imageStore.getImageList("stump"));

            world.removeEntity(scheduler, this);

            world.addEntity(stump);

            return true;
        } else if (this.health >= this.healthLimit) {
            Tree tree = new Tree("tree_" + this.id, this.position, Functions.getNumFromRange(Tree.actionMax, Tree.actionMin), Functions.getNumFromRange(Tree.animationMax, Tree.animationMin), Functions.getIntFromRange(Tree.healthMax, Tree.healthMin), imageStore.getImageList("tree"));

            world.removeEntity(scheduler, this);

            world.addEntity(tree);
            tree.scheduleActions(scheduler, world, imageStore);

            return true;
        }

        return false;
    }

    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
        scheduler.scheduleEvent(this, new Activity(this, world, imageStore), this.actionPeriod);
        scheduler.scheduleEvent(this, new Animation(this, 0), getAnimationPeriod());
    }
}