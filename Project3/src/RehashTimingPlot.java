import org.knowm.xchart.*;
import org.knowm.xchart.style.Styler;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RehashTimingPlot {

    public static void main(String[] args) {
        // Prepare lists to hold the data from the file
        List<Integer> qValues = new ArrayList<>();
        List<Long> noRehashTimes = new ArrayList<>();
        List<Long> doublingTimes = new ArrayList<>();
        List<Long> additionTimes = new ArrayList<>();

        // Read the data from the simplified text file
        try (BufferedReader reader = new BufferedReader(new FileReader("rehash_timings.txt"))) {
            String line;
            reader.readLine(); // Skip header line
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");
                int q = Integer.parseInt(parts[0]);
                long noRehashTime = Long.parseLong(parts[1]);
                long doublingTime = Long.parseLong(parts[2]);
                long additionTime = Long.parseLong(parts[3]);

                // Add data to the lists
                qValues.add(q);
                noRehashTimes.add(noRehashTime);
                doublingTimes.add(doublingTime);
                additionTimes.add(additionTime);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Create a chart using XChart
        XYChart chart = new XYChartBuilder().width(800).height(600).title("Rehash Timing").xAxisTitle("q").yAxisTitle("Time per Insertion (ns)").build();

        // Add the data to the chart
        chart.addSeries("No Rehash", qValues, noRehashTimes);
        chart.addSeries("Doubling", qValues, doublingTimes);
        chart.addSeries("Addition (+10000)", qValues, additionTimes);

        // Customize the plot appearance (optional)
        chart.getStyler().setMarkerSize(8);  // Fix for Styler issue: add this line
        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNE);  // Correct usage of Styler for legend position

        // Display the chart
        new SwingWrapper<>(chart).displayChart();
        
        // Save the chart to a PNG file
        try {
            // Specify the file path and name for saving the chart
            File chartFile = new File("rehash_timing_plot.png");
            BitmapEncoder.saveBitmap(chart, chartFile.getAbsolutePath(), BitmapEncoder.BitmapFormat.PNG);
        } catch (IOException e) {
            System.out.println("Error saving the chart: " + e.getMessage());
        }
    }
}
