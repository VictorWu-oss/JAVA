import processing.core.PImage;
import java.util.List;

public class Tree extends Plant {
    public static final double animationMax = .6;
    public static final double animationMin = 0.05;
    public static final double actionMax = 1.4;
    public static final double actionMin = 1;
    public static final int healthMax = 3;
    public static final int healthMin = 1;

    public Tree(String id, Point position, double actionPeriod, double animationPeriod, int health, List<PImage> images){
        this.id = id;
        this.images = images;
        this.position = position;
        this.animationPeriod = animationPeriod;
        this.actionPeriod = actionPeriod;
        this.health = health;
    }

    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {

        if (!this.transform(world, scheduler, imageStore)) {

            scheduler.scheduleEvent(this, new Activity(this, world, imageStore), this.actionPeriod);
        }
    }

    public boolean transform(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        if (this.health <= 0) {
            Stump stump = new Stump( "stump_" + this.id, this.position, imageStore.getImageList("stump"));

            world.removeEntity(scheduler, this);

            world.addEntity(stump);

            return true;
        }

        return false;
    }
}
