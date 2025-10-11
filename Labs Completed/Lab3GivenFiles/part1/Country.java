package part1;

import java.util.List;
import java.util.Map;

public class Country {
    // private attributes
    private String name;
    private Map<Integer, Emission> emissions;

    // constructor
    public Country(String name, Map<Integer, Emission> emissions){
        this.name = name;
        this.emissions = emissions;
    }

    // Only necessary getter for part1
    public Map<Integer, Emission> getEmissions() {
        return emissions;
    }

    public String getName() {
        return name;
    }

    // No necessary setters
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public void setEmissions(Map<Integer, Emission> emissions) {
//        this.emissions = emissions;
//    }
}
