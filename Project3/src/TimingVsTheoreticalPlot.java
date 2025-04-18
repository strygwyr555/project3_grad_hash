import org.knowm.xchart.*;
import org.knowm.xchart.style.markers.None;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class TimingVsTheoreticalPlot {

    public static void main(String[] args) throws Exception {
        List<Double> loadFactors = new ArrayList<>();
        List<Double> linearTimeSucc = new ArrayList<>();
        List<Double> linearTimeFail = new ArrayList<>();

        // Read timing_vs_load_factor.txt
        try (BufferedReader br = new BufferedReader(new FileReader("timing_vs_load_factor.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.trim().split(",");
                if (parts.length == 3) {
                    loadFactors.add(Double.parseDouble(parts[0]));
                    linearTimeSucc.add(Double.parseDouble(parts[1]));
                    linearTimeFail.add(Double.parseDouble(parts[2]));
                }
            }
        }

        // Compute Q using λ = 0.5
        int indexAtHalf = findClosestIndex(loadFactors, 0.5);
        double lambdaHalf = loadFactors.get(indexAtHalf);
        double theoreticalAtHalf = 0.5 + 1.0 / (2.0 * Math.pow(1.0 - lambdaHalf, 2));
        double empiricalAtHalf = linearTimeSucc.get(indexAtHalf);
        double Q = theoreticalAtHalf / empiricalAtHalf;

        // Compute theoretical values and scaled empirical
        List<Double> linearProbsSucc = new ArrayList<>();
        List<Double> doubleProbsSucc = new ArrayList<>();
        List<Double> scaledSucc = new ArrayList<>();

        List<Double> linearProbsFail = new ArrayList<>();
        List<Double> doubleProbsFail = new ArrayList<>();
        List<Double> scaledFail = new ArrayList<>();

        for (int i = 0; i < loadFactors.size(); i++) {
            double lambda = loadFactors.get(i);
            // Avoid division by zero or log(0)
            if (lambda >= 1.0) lambda = 0.9999;
            if (lambda == 0.0) lambda = 0.0001;

            linearProbsSucc.add(0.5 + 1.0 / (2.0 * Math.pow(1.0 - lambda, 2)));
            doubleProbsSucc.add(Math.log(1.0 - lambda) / lambda);
            scaledSucc.add(Q * linearTimeSucc.get(i));

            linearProbsFail.add(0.5 + 1.0 / (2.0 * (1.0 - lambda)));
            doubleProbsFail.add(1.0 / (1.0 - lambda));
            scaledFail.add(Q * linearTimeFail.get(i));
        }

        // Plot success
        XYChart successChart = new XYChartBuilder()
                .width(800).height(600)
                .title("Theoretical vs Load Factor")
                .xAxisTitle("Load Factor λ").yAxisTitle("Avg Probes / Scaled Time").build();

        successChart.addSeries("Linear Theoretical", loadFactors, linearProbsSucc).setMarker(new None());
        successChart.addSeries("Double Theoretical", loadFactors, doubleProbsSucc).setMarker(new None());
        successChart.addSeries("Scaled Empirical", loadFactors, scaledSucc).setMarker(new None());

        BitmapEncoder.saveBitmap(successChart, "time_succ", BitmapEncoder.BitmapFormat.PNG);

        // Plot failure
        XYChart failChart = new XYChartBuilder()
                .width(800).height(600)
                .title("Theoretical vs Load Factor")
                .xAxisTitle("Load Factor λ").yAxisTitle("Avg Probes / Scaled Time").build();

        failChart.addSeries("Linear Theoretical", loadFactors, linearProbsFail).setMarker(new None());
        failChart.addSeries("Double Theoretical", loadFactors, doubleProbsFail).setMarker(new None());
        failChart.addSeries("Scaled Empirical", loadFactors, scaledFail).setMarker(new None());

        BitmapEncoder.saveBitmap(failChart, "time_fail", BitmapEncoder.BitmapFormat.PNG);

        System.out.println("Graphs saved as time_succ.png and time_fail.png");
    }

    private static int findClosestIndex(List<Double> list, double value) {
        double minDiff = Double.MAX_VALUE;
        int index = 0;
        for (int i = 0; i < list.size(); i++) {
            double diff = Math.abs(list.get(i) - value);
            if (diff < minDiff) {
                minDiff = diff;
                index = i;
            }
        }
        return index;
    }
}
