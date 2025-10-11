import java.util.List;
import java.util.Map;

public class Sector implements GreenhouseGasEmitter{
    // private attributes
    private String name;
    private Map<Integer, Double> emissions;

    // constructor
    public Sector(String name, Map<Integer, Double> emissions) {
        this.name = name;
        this.emissions = emissions;
    }

    //getters
    @Override
    public String getName() {
        return this.name;
    }
    public Map<Integer, Double> getEmissions() {
        return emissions;
    }

    @Override
    public double getTotalEmissionsInYear(int year){
        if (this.emissions.containsKey(year)) {
            return this.emissions.get(year);
        }
        return 0.0;
    }

    //setters
    public void setName(String name) {
        this.name = name;
    }
    public void setEmissions(Map<Integer, Double> emissions) {
        this.emissions = emissions;
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

    // Given a list of sectors identify the sector which has seen the highest average greenhouse gas emissions between 2 specified year
    // Print the Sector name and the average value you identified (DO NOT USE ABS VALUE) End year must be included in comp. (INCLUSIVE)
    public static Sector sectorWithBiggestChangeInEmissions(List<Sector> sectors, int startYear, int endYear){
        double totalEmissions;
        double highestAverage = 0.0;
        int counter;
        Sector topSector = null;

        // Loop through each Sector object in the list
        for(Sector sector : sectors){
            totalEmissions = 0;
            counter = 0;
            // Access emission map for each Sector
            Map<Integer, Double> emissionMap = sector.getEmissions();

            // Loop through each entry (year and emission) in the map
            for (Map.Entry<Integer, Double> entry : emissionMap.entrySet()){
                Integer year = entry.getKey();
                // ONLY between startYear and endYear
                if (year >= startYear && year <= endYear){
                    Double emission = entry.getValue();
                    totalEmissions += emission;
                    counter++;
                }
            }
            // Calculate average
            double totalAverage = totalEmissions / counter;
            // If higher average is found then update
            if (totalAverage > highestAverage ){
                highestAverage = totalAverage;
                topSector = sector;
            }
        }
        System.out.println(topSector.getName() + " is the sector with the biggest change in emissions with " + highestAverage + " average kilo-tons of GHG");
        return topSector;
    }

}
