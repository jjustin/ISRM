import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        for (int problem = 6; problem <= 6; problem++) {
            long startTime = System.currentTimeMillis();
            System.out.println("Generating graph");
            Graph g = new Graph(String.format("../Problem%d.txt", problem));
            long endTime = System.currentTimeMillis();
            System.out.println(
                    "Total execution time for generating graph of " + problem + ": " + (endTime - startTime) + "ms");

            Runner r = new RunnerDE();
            String s = "";
            double cost = 0;
            for (int i = 1; i <= 3; i++) {
                Globals.trashType = i;
                double bestCost = Double.POSITIVE_INFINITY;
                Solution bestSol = null;

                System.out.printf("Starting subproblem %s\n", i);
                for (int loop = 0; loop < numberOfLoops(problem); loop++) {
                    // startTime = System.currentTimeMillis();
                    Globals.crossoverRate = Math.random();
                    Globals.mutateScalar = 2.0 * Math.random();
                    Solution res = r.run(g);
                    double c = res.cost(g, true);
                    if (c < bestCost) {
                        bestSol = res;
                        bestCost = c;
                    }
                    // endTime = System.currentTimeMillis();
                    // System.out.printf(
                    // "Found soloution for problem %d|%d with current best cost %.3f iter: %d time
                    // needed: %dms\n",
                    // problem, i, bestCost, loop, endTime - startTime);
                }
                cost += bestCost;
                s += bestSol.toString();
            }
            System.out.printf("Found solution for %d with cost %.3f\n", problem, cost);
            try {
                File f = new File(String.format("solutions/solution%d.txt", problem));
                f.createNewFile();
                BufferedWriter writer = new BufferedWriter(new FileWriter(f));
                writer.write(s);
                writer.close();
            } catch (Exception ex) {
                System.err.println("Failed to open file " + ex);
            }
        }
    }

    private static int numberOfLoops(int n) {
        switch (n) {
            case 1:
                return 100;
            case 2:
                return 200;
            case 3:
                return 20;
            case 4:
                return 10;
            case 5:
                return 10;
            case 6:
                return 300;
            case 7:
                return 10;
            case 8:
                return 10;
            case 9:
                return 10;
            case 10:
                return 100;
            default:
                return 1;
        }
    }
}
