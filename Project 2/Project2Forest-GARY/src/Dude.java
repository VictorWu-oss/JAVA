public abstract class Dude extends Active implements Interactive{
    protected int resourceLimit;
    protected int resourceCount = 0;

    public Point nextPosition(WorldModel world, Point destPos, boolean force) {
        if (path == null || path.isEmpty() || force) {
            path = strategy.computePath(this.position, destPos,
                    p -> world.withinBounds(p) && !world.isOccupied(p));
        }

        if (!(path.isEmpty())) {
            return path.remove(0);
        }
        System.out.println(path);
        return this.position;
    }
}
