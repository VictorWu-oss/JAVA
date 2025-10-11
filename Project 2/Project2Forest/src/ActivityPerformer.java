public interface ActivityPerformer{
   double getActionPeriod();
   void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler);
}
