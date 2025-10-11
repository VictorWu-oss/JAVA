import processing.core.PImage;

public abstract class Animated extends Entity{
    protected int imageIndex = 0;
    protected double actionPeriod;
    protected double animationPeriod;

    public double getAnimationPeriod() {
        return this.animationPeriod;
    }

    @Override
    public PImage getCurrentImage(){
        return this.images.get(this.imageIndex % this.images.size());
    }

    public void nextImage() {
        this.imageIndex = this.imageIndex + 1;
    }

    @Override
    public String log(){
        return this.id.isEmpty() ? null :
                String.format("%s %d %d %d", this.id, this.position.x, this.position.y, this.imageIndex);
    }

    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
        scheduler.scheduleEvent(this, new Animation(this, 0), getAnimationPeriod());
    }
}