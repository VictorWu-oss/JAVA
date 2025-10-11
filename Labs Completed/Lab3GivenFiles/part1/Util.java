package part1;

import java.util.Map;

public class Util {
    // Overloading

    // Takes a Country and return year with the highest greenhouse gas emission as an int
    // Return year with the highest total emissions in its Map of years to CO2, N2O, and CH4 emissions
    public static int getYearWithHighestEmissions(Country country) {
        Map<Integer, Emission> emissionsMap = country.getEmissions();

        // Keep track
        double highestEmissions = 0;
        int highestEmissionYear = 0;

        for (Map.Entry<Integer, Emission> entry : emissionsMap.entrySet()) {
            Integer year = entry.getKey();               // Year key
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

    // Takes a Sector and return year with the highest greenhouse gas emission as an int
    // Return the year with the highest emissions in its Map of years to emissions
    public static int getYearWithHighestEmissions(Sector sector){
        Map<Integer, Double> emissionMap = sector.getEmissions();

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
