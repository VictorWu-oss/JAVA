public class Point {
    private double x;
    private double y;
    private double z;

    public Point() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
    }

    public Point(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ", "+ z + ")";
    }
}