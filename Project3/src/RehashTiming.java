
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RehashTiming {

    public static void run(List<String> wholeList) {
        List<Long> noRehashTimes = new ArrayList<>();
        List<Long> doublingTimes = new ArrayList<>();
        List<Long> additionTimes = new ArrayList<>();

        StringBuilder output = new StringBuilder();
        output.append("q NoRehashTime DoublingTime AdditionTime\n");  // Simpler header

        for (int q = 4; q <= 17; q++) {
            int numValues = (int) Math.pow(2, q) - 1;
            List<String> subList = wholeList.subList(0, numValues);

            // No Rehash: big table from start (2^17)
            HashTable noRehashHT = new HashTable((int) Math.pow(2, 17), 0.75, 1);
            long start1 = System.nanoTime();
            for (String val : subList) noRehashHT.insert(val);
            long end1 = System.nanoTime();
            long time1 = (end1 - start1) / numValues;

            // Doubling Strategy
            HashTable doublingHT = new HashTable((int) Math.pow(2, q), 0.75, 1);
            long start2 = System.nanoTime();
            for (String val : subList) doublingHT.insert(val);
            long end2 = System.nanoTime();
            long time2 = (end2 - start2) / numValues;

            // Addition Strategy (+10000)
            HashTable additionHT = new HashTable((int) Math.pow(2, q), 0.75, 2);
            long start3 = System.nanoTime();
            for (String val : subList) additionHT.insert(val);
            long end3 = System.nanoTime();
            long time3 = (end3 - start3) / numValues;

            // Save results in simpler format
            output.append(q).append(" ")
                  .append(time1).append(" ")
                  .append(time2).append(" ")
                  .append(time3).append("\n");

            noRehashTimes.add(time1);
            doublingTimes.add(time2);
            additionTimes.add(time3);
        }

        try (FileWriter writer = new FileWriter("rehash_timings.txt")) {
            writer.write(output.toString());
            System.out.println("timing results written to rehash_timings.txt");
        } catch (IOException e) {
            System.out.println("Error writing timing file: " + e.getMessage());
        }
    }
}
