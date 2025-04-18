import java.io.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class TimingVsLoadFactor {

    // Method to run the timing experiment
    public static void main(List<String> addValues, List<String> checkValues) {
        // Create the hash table
        HashTable ht = new HashTable(65536, 0.75, 1);  // Table size 65536, load factor 0.75, doubling strategy

        // Set up the list to store the timing data
        List<Double> loadFactors = new ArrayList<>();
        List<Long> successTimings = new ArrayList<>();
        List<Long> failTimings = new ArrayList<>();

        // Loop through different load factors (from 0.0 to 0.99)
        for (double lambda = 0.0; lambda < 1.0; lambda += 0.033) {
            int targetSize = (int) (lambda * ht.getTableSize());

            // Insert values to reach the target size
            while (ht.size() < targetSize && ht.size() < addValues.size()) {
                ht.insert(addValues.get(ht.size()));  // Insert one value at a time to avoid infinite loop
            }

            // Measure successful search time (searching for items in addValues)
            long successTotalTime = 0;
            for (String value : addValues) {
                long startTime = System.nanoTime();
                ht.find(value);  // Perform a successful search
                long endTime = System.nanoTime();
                successTotalTime += (endTime - startTime);
            }
            long avgSuccessTime = successTotalTime / addValues.size();

            // Measure unsuccessful search time (searching for items in checkValues)
            long failTotalTime = 0;
            for (String value : checkValues) {
                long startTime = System.nanoTime();
                ht.find(value);  // Perform an unsuccessful search
                long endTime = System.nanoTime();
                failTotalTime += (endTime - startTime);
            }
            long avgFailTime = failTotalTime / checkValues.size();

            // Store the results
            loadFactors.add(lambda);
            successTimings.add(avgSuccessTime);
            failTimings.add(avgFailTime);
        }

        // Write the results to a file for plotting
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("timing_vs_load_factor.txt"))) {
            for (int i = 0; i < loadFactors.size(); i++) {
                writer.write(loadFactors.get(i) + "," + successTimings.get(i) + "," + failTimings.get(i));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Results saved to timing_vs_load_factor.txt");
    }
}
