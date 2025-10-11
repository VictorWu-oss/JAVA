package part2;

import java.util.List;
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

    //getters
    public String getName() {
        return name;
    }

    public Map<Integer, Double> getEmissions() {
        return emissions;
    }

    // Takes a Sector and return year with the highest greenhouse gas emission as an int
    // Return the year with the highest emissions in its Map of years to emissions
    public int getYearWithHighestEmissions(){
        Map<Integer, Double> emissionMap = getEmissions();

        // Keep track of year with highest Emissions && total
        double highestEmissions = 0;
        int highestEmissionsYear = 0;

        for (Map.Entry<Integer, Double> entry : emissionMap.entrySet()) {
            Integer year = entry.getKey();
            Double emission = entry.getValue();

            double totalEmissions = 0;
            totalEmissions = emission;

            if(totalEmissions > highestEmissions){
                highestEmissions = totalEmissions;
                highestEmissionsYear = year;
            }
        }
        return highestEmissionsYear;
    }
}
