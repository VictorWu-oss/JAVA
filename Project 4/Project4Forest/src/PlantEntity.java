import processing.core.PImage;
import java.util.List;

public abstract class PlantEntity extends ActiveAnimatedEntity {
    private int health;
    private final int healthLimit;

    public PlantEntity(String id, Point position,
                       List<PImage> images, double actionPeriod,
                       double animationPeriod, int health, int healthLimit) {
        super(id, position, images, actionPeriod, animationPeriod);
        this.health = health;
        this.healthLimit = healthLimit;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setHealth(int health, WorldModel worldModel, EventScheduler  scheduler, ImageStore imageStore){
        this.health = health;
        if (this.health <= 0){
            this.transformPlant(worldModel, scheduler, imageStore);
        }
    }

    public int getHealthLimit() {
        return this.healthLimit;
    }

    public abstract boolean transformPlant(WorldModel world, EventScheduler scheduler, ImageStore imageStore);
}
