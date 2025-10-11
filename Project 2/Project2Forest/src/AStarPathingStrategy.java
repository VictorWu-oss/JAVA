import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

///  computePath: compute path of points returned as a list from start pt to end pt
///  potentialNeighbors: pathing algo needs to know direct. in which travel may be able to proceed
///  canPassThrough: pathing algo needs to know it can be traversed (valid position in world and location where it can move to)
///  withinReach: pathing algo should not attempt to move to end pt because its occupied, so a path is completely when point is reached withinReach of end pt

class AStarPathingStrategy implements PathingStrategy {

    /// Helper function to compute distance between 2 points
    /// Especially G cost and H cost
    private double manhattanDistance(Point a, Point b){
        return Math.abs(a.x-b.x) + Math.abs(a.y-b.y);
    }

    public List<Point> computePath(Point start, Point end,
                                   Predicate<Point> canPassThrough, BiPredicate<Point, Point> withinReach,
                                   Function<Point, Stream<Point>> potentialNeighbors)
    {
        class Node{
            Point point;
            Node parent;
            double g;
            double f;
            double h;

            public Node(Point point){
                this.point = point;     /// Actual location
                this.parent = null;     /// Link to previous node on the path
                this.g = 0;             /// G Cost = Starting Node -> Current Node
                this.h = 0;             /// H Cost = Current Node -> End Point
                this.f = 0;             /// F Cost = G + H
            }

            public double getGCost() {
                return g;
            }

            public double getFCost() {
                return f;
            }

            public double getHCost(){
                return h;
            }

            public Point getPos(){
                return point;
            }

            public Node getPrior(){
                return parent;
            }

            public void setPrior(Node parent){
                this.parent = parent;
            }

            public void setGCost(double g){
                this.g = g;
            }

            public void setFCost(double f){
                this.f = f;
            }

            public void setHCost(double h){
                this.h = h;
            }
        }

        /// Final path to take from start to goal
        List<Point> path = new LinkedList<>();

        /// Open list: Stores nodes to be evaluated, (Priority queue via a min-heap & SORTED BY lowest F COST & H COST bc if F cost is tied default to H Cost)
        PriorityQueue<Node> openList = new PriorityQueue<>(Comparator.comparingDouble(Node::getFCost).thenComparing(Node::getHCost));

        ///  Maps Point to its Node, check if a point is ALREADY IN OPEN LIST
        HashMap<Point, Node> openListMap = new HashMap<>();

        /// Closed list: Stores nodes already evaluated, so you don't revisit it (HashMap)
        Map<Point, Node> closedList = new HashMap<>();

        /// INITIALIZATION
        Node startNode = new Node(start);                   /// Given start point create a startNode
        double startingValue = manhattanDistance(start, end);  /// Set an initial starting distance from start to end
        startNode.setHCost(startingValue);                  /// Initially f = h and g = 0 (bc its starting) for start Node
        startNode.setFCost(startingValue);
        openList.add(startNode);                            /// Start node is added to the open list to be inspected
        openListMap.put(start, startNode);

        /// MAIN LOOP: While open list is not empty{
        while(!openList.isEmpty()){

            /// Remove Node with lowest F Cost from openList
            Node current = openList.remove();

            /// If Current Point is (withinReach) or adjacent to End Point, then build a path
            if (withinReach.test(current.getPos(), end)){
                /// Add until you can no longer find a parent
                while(current.getPrior() != null) {
                    path.add(current.getPos());
                    current = current.getPrior();
                }

                /// Return the path by reversing it because you want it form the start to the end
                Collections.reverse(path);
                return path;
            }

            /// Filtered list containing neighbors of Current, you can actually move to (potentialNeighbors) uses streams
            List<Point> filtered = potentialNeighbors.apply(current.getPos())   ///  Cardinal Neighbors (U D L R)
                    .filter(canPassThrough)                                     ///  Account for obstacles in the way
                    .filter(pt -> !pt.equals(start))                      ///  Avoid going back to start
                    .filter(pt -> !closedList.containsKey(pt))            ///  Skip processed points
                    .collect(Collectors.toList());

            ///  Process each neighbor
            for (Point neighbor : filtered){
                /// TOTAL COST it takes to reach neighbor node from the start / ACTUAL G COST DISTANCEEEE
                /// Cost to reach current node (g) + Cost to move from current to neighbor
                ///  Current will be the parent of neighbor
                double new_gCost = current.getGCost() + manhattanDistance(current.getPos(), neighbor);

                /// If neighbor is in the openList and meaning you found a path to a node you've already seen, only update if new path is cheaper
                if (openListMap.containsKey(neighbor)){
                    Node existing = openListMap.get(neighbor);
                    ///  If it's worse than previous g cost skip it
                    if (new_gCost >= existing.getGCost()){
                        continue;
                    }

                    ///  Remove worse node to replace it
                    openList.remove(existing);
                }

                /// Set the g, h, f values for Neighbor to create a Link
                Node neighborNode = new Node(neighbor); /// Create node of neighbor Point
                neighborNode.setPrior(current);
                neighborNode.setGCost(new_gCost);
                neighborNode.setHCost(manhattanDistance(neighbor, end));
                neighborNode.setFCost(neighborNode.getGCost() + neighborNode.getHCost());

                ///  Update the map with neighbor and the priority q
                openListMap.put(neighbor, neighborNode);
                openList.add(neighborNode);
            }

            /// Current node is now processed
            closedList.put(current.getPos(), current);
        }
        return path;
    }
}
