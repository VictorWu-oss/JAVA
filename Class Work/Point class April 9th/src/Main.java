public class Main {
    public static void main(String[] args) {
        Point p1 = new Point(1, 2);
        Point p2 = new Point(2, 3);
        System.out.println("x: " + p2.getXCord() + " y: " + p2.getYCord());
        System.out.println(p1);
        p1.setxCord(34);
        System.out.println(p1);
        Point p3 = p1;
        Point p4 = new Point();
        System.out.println(p1 == p3);
        System.out.println(p2 == p4);
        System.out.println(Point.distance(p1, p2));

        Point.distance(p1, p2); // this is tied through the class so Class -> method
        p1.dist(p2); // call it through class name because it's not tied through the class

        Object pp = new Point(0, 12);
        ((Point) pp).dist(p2); // Down casting, instead of using Superclass, bring it down to the Point class
    }
}
