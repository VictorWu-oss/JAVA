public abstract class Action {
    protected final Entity entity;

    public Action(Entity entity) {
        this.entity = entity;
    }

    public abstract void executeAction(EventScheduler scheduler);
}
