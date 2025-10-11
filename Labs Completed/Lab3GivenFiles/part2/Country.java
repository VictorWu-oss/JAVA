package part2;

import java.util.List;
import java.util.Map;
import java.lang.Math;

public class Country {
    // private attributes
    private String name;
    private Map<Integer, Emission> emissions;

    // constructor
    public Country(String name, Map<Integer, Emission> emissions){
        this.name = name;
        this.emissions = emissions;
    }

    // necessary getters
    public String getName() {
        return name;
    }

    public Map<Integer, Emission> getEmissions() {
        return emissions;
    }

    // Overloading

    // Takes a Country and return year with the highest greenhouse gas emission as an int
    // Return year with the highest total emissions in its Map of years to CO2, N2O, and CH4 emissions
    public int getYearWithHighestEmissions() {
        Map<Integer, Emission> emissionsMap = getEmissions();

        // Keep track
        double highestEmissions = 0;
        int highestEmissionYear = 0;

        for (Map.Entry<Integer, Emission> entry : emissionsMap.entrySet()) {
            Integer year = entry.getKey();           // Year key
            Emission emission = entry.getValue();    // List of emission for that year

            // Go through each year and add all
            double totalEmissions = 0;
            totalEmissions = emission.getCO2() + emission.getCH4() + emission.getN2O();

            // Save the year as the highest emission count and if it was found that another year has higher emission count
            // Change highestEmissionYear to that year.
            if (totalEmissions > highestEmissions) {
                highestEmissions = totalEmissions;
                highestEmissionYear = year;
            }
        }
        return highestEmissionYear;
    }
}
