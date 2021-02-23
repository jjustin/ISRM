import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import javax.naming.spi.DirStateFactory.Result;

public class Main {
    public static void main(String[] args) {
        for (int problem = 1; problem <= 10; problem++) {
            long startTime = System.currentTimeMillis();
            System.out.println("Generating graph");
            Graph g = new Graph(String.format("../Problem%d.txt", problem));
            long endTime = System.currentTimeMillis();
            System.out.println(
                    "Total execution time for generating graph of " + problem + ": " + (endTime - startTime) + "ms");

            startTime = System.currentTimeMillis();
            Runner r = new RunnerSingleRun();
            String s = "";
            double cost = 0;
            for (int i = 1; i <= 3; i++) {
                Globals.trashType = i;
                Solution res = r.run(g);
                cost += res.cost(g);
                s += res.toString();
            }
            System.out.printf("Found soloution for problem %d with cost %f\n", problem, cost);
            try {
                File f = new File(String.format("solutions/solution%d.txt", problem));
                f.createNewFile();
                BufferedWriter writer = new BufferedWriter(new FileWriter(f));
                writer.write(s);
                writer.close();
            } catch (Exception ex) {
                System.err.println("Failed to open file " + ex);
            }
            endTime = System.currentTimeMillis();
            System.out.println("Total execution time for finding solution for problem " + problem + ": "
                    + (endTime - startTime) + "ms\n");
        }
    }
}
