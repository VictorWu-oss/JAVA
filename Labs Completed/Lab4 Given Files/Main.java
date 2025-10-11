import processing.core.PApplet;

public class Main extends PApplet {

    public static void main(String[] args) {
        String[] processingArgs = {"main"};
        Plotter plotter = new Plotter();
        PApplet.runSketch(processingArgs, plotter);
    }
}
