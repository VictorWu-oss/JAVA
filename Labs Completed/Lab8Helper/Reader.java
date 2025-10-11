import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Reader {
    private List<Point> points;

    public Reader(){
        points = new ArrayList<>();
    }

    // Read the input file and stores points in a list
    public void readFromFile(String filename){
        try (Scanner scanner = new Scanner(new File(filename))){
            while (scanner.hasNext()){
                String line = scanner.nextLine().trim();
                if (!line.isEmpty()){
                    // Read by splitting them with commas
                    String[] values = line.split(",");
                    double x = Double.parseDouble(values[0].trim());
                    double y = Double.parseDouble(values[1].trim());
                    double z = Double.parseDouble(values[2].trim());
                    points.add((new Point(x, y, z)));
                }
            }
        } catch (IOException e){
            System.err.println("File error: " + e.getMessage());
        }
    }

    public void writeToFile(String filename, List<Point> processedPoints){
        try(PrintWriter writer = new PrintWriter(filename)){
            for (Point point : processedPoints){
                writer.println(point.getX() + ", " + point.getY() + ", " + point.getZ());
            }

        } catch (IOException e){
            System.err.println("File write error: " + e.getMessage());
        }
    }

    // Process points with streams
    public List<Point> processPointsWithStream(){
        return points.stream()
                // Remove all points that have a z value > 2.0
                .filter(p -> p.getZ() <= 2.0)
                // Scale down all points by 0.5 && Translate x-150 abd y-37
                .map(p -> new Point(((p.getX() * 0.5) - 150) , ((p.getY() * 0.5) - 37), p.getZ() * 0.5))
                .collect(Collectors.toList());
    }

    public static void main (String[] args){
        // Accept the file as the first argument
        String inputFileName = args[0];

        Reader reader = new Reader();
        reader.readFromFile((inputFileName));

        // Process points with streams
        List<Point> processedPoints = reader.processPointsWithStream();
        reader.writeToFile("drawMe.txt", processedPoints);
    }
}
