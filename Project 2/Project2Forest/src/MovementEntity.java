import java.util.List;
import java.util.function.Predicate;

public interface MovementEntity{
    boolean moveTo(WorldModel world, Entity target, EventScheduler scheduler);
    Point nextPosition(WorldModel world, Point destPos, boolean force);

}
