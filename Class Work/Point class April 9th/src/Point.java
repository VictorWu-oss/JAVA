public class Point {
    // attributes
    private int xCord = 5;      // Can define a set value if needed
    private int yCord;          // These are private b/c of security and abstraction

    // constructor // in Python it's the __init__ function
    // you can have multiple of these, maybe you wanted a different Point with a default y value but inputted x value
    public Point() {}

    // overloading
    public Point(int newX, int newY) {
        // self. == this.
        this.xCord = newX;
        this.yCord = newY;
    }

    public Point(int newY) {
        this(0, newY);            // points to current object with newY
    }

    // getters or accessors
    public int getXCord() {
        return this.xCord;
    }

    public int getYCord() {
        return this.yCord;
    }

    // to modify private variables within the class you need setters
    public void setxCord(int newx) {
        this.xCord = newx;
    }

    public void setYCord(int newy) {
        this.yCord = newy;
    }

    @Override
    public String toString() {   // like repr you need a string representation otherwise it will print the memory location
        return "Point{" +
                "xCord = " + this.xCord +       // establish that this is the current point object values
                ", yCord = " + this.yCord + '}';
    }

    public boolean equals(Object o) {
        if (o == null) return false;                // Check if it's null
        if (o == this) return true;                 // Check if they are the same
        if (!(o instanceof Point)) return false;    // if o is not an instance of the class Point
        Point other = (Point) o;                    // Cast it to current class in order to compare attributes
        return this.xCord == other.xCord && this.yCord == other.yCord;
    }

    // distance method as static, NOT tied to current object
    public static double distance(Point p1, Point p2) {
        return Math.sqrt(Math.pow((p1.xCord - p2.xCord), 2) + Math.pow((p1.yCord - p2.yCord), 2));
    }

    // Class member instance method
    // distance instance method, tied to current object
    public double dist(Point p1) {
        return Math.sqrt(Math.pow((p1.xCord - this.xCord), 2) + Math.pow((p1.yCord - this.yCord), 2));
    }
}