package part1;

public class Emission {
    // private attributes
    private double co2;
    private double n2o;
    private double ch4;

    // constructor
    public Emission(double co2, double n2o, double ch4){
        this.co2 = co2;
        this.n2o = n2o;
        this.ch4 = ch4;
    }

    // getters
    public double getCO2() {
        return co2;
    }
    public double getN2O() {
        return n2o;
    }
    public double getCH4() {
        return ch4;
    }

    // No necessary setters
//    public void setCO2(double co2) {
//        this.co2 = co2;
//    }
//    public void setN2O(double n2o) {
//        this.n2o = n2o;
//    }
//    public void setCH4(double ch4) {
//        this.ch4 = ch4;
//    }
}
