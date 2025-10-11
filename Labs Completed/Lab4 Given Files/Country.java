import java.util.List;
import java.util.Map;
import java.lang.Math;

public class Country implements GreenhouseGasEmitter{
    // private attributes
    private String name;
    private Map<Integer, Emission> emissions;

    // constructor
    public Country(String name, Map<Integer, Emission> emissions){
        this.name = name;
        this.emissions = emissions;
    }

    // necessary getters
    @Override
    public String getName() {
        return this.name;
    }

    public Map<Integer, Emission> getEmissions() {
        return emissions;
    }

    @Override
    public double getTotalEmissionsInYear(int year){
        if (emissions.containsKey(year)){
            Emission e = emissions.get(year);
            return e.getN2O() + e.getCO2() + e.getCH4();
        }
        return 0.0;
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

    // Given a list of countries (List<Country>) and identify the country with the highest CH4 emissions in a specified year
    public static Country countryWithHighestCH4InYear(List<Country> countries, int year){
        Country topCountry = null;
        double highestCH4 = 0.0;

        for (Country country : countries){
            Map<Integer, Emission> emissionMap = country.getEmissions();

            // check if emissionMap contains the year
            if(emissionMap.containsKey(year)){
                Emission emission = emissionMap.get(year);
                // If so get the CH4 value
                double ch4 = emission.getCH4();

                // Compare it with the highest CH4
                if(ch4 > highestCH4){
                    highestCH4 = ch4;
                    topCountry = country;
                    // If it's greater update topCountry and highest CH4
                }
            }
        }
        System.out.println(topCountry.getName() + " has the highest CH4 in that year, with" + " " + highestCH4 + " kilo-tons of CH4");
        return topCountry;
    }

    // Given a list of countries identify the country with the highest change in TOTAL GREENHOUSE GAS EMISSIONS between 2 specified years
    public static Country countryWithHighestChangeInEmissions(List<Country> countries, int startYear, int endYear) {
        Country highestChangeCountry = null;
        double highestChange = 0.0;

        // Country
        for (Country country : countries) {
            // Map of years to Emission objects for that specific country
            Map<Integer, Emission> emissionMap = country.getEmissions();

            // Also have to check if the country names match somehow
            // check if EmissionMap contains the start year
            if(emissionMap.containsKey(startYear) && emissionMap.containsKey(endYear)){
                Emission emissionStart = emissionMap.get(startYear);
                // Add up all the emissions
                double totalStartEmission = emissionStart.getCH4() + emissionStart.getCO2() + emissionStart.getN2O();

                Emission emissionEnd = emissionMap.get(endYear);
                // Add up all the emissions
                double totalEndEmission = emissionEnd.getCH4() + emissionEnd.getCO2() + emissionEnd.getN2O();

                double change = Math.abs(totalStartEmission - totalEndEmission);

                if (change > highestChange){
                    highestChange = change;
                    highestChangeCountry = country;
                }
            }
        }
        // return the country name
        System.out.println(highestChangeCountry.getName() + " " + "has the highest change in TOTAL GREENHOUSE GAS EMISSIONS with:" + " " + highestChange + " total kilo-tons");
        return highestChangeCountry;
    }
}
