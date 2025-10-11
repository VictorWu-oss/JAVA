package part1;

import java.util.Map;

public class Sector {
    // private attributes
    private String name;
    private Map<Integer, Double> emissions;

    // constructor
    public Sector(String name, Map<Integer, Double> emissions) {
        this.name = name;
        this.emissions = emissions;
    }

    // Only necessary getters for part1
    public Map<Integer, Double> getEmissions() {
        return emissions;
    }

    public String getName() {
        return name;
    }

}
