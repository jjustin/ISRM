import java.util.Random;

public class RunnerDE extends Runner {

    @Override
    public Solution run(Graph g) {
        Solution bests = null;
        double bestc = Double.POSITIVE_INFINITY;
        StarterGreedy2 starter = new StarterGreedy2();
        double d = 1;
        Parameters[] population = new Parameters[Globals.popSize];

        Random r = new Random();
        for (int i = 0; i < Globals.popSize; i++) {
            population[i] = new Parameters(((double) g.nodes.length) * g.avgEdgeLen);
        }

        // System.out.printf("Starting with fresh population\n");
        for (double i = 1; i < Globals.numberOfIters; i += d) {
            for (Parameters pars : population) {
                Solution s = starter.generate(g, pars);
                double c = s.cost(g, false);
                pars.cost = c;
                if (c < bestc) {
                    bestc = c;
                    bests = s;
                }
            }
            // System.out.println("Current best cost: " + bestc);
            for (int j = 0; j < population.length; j++) {
                int mutate = r.nextInt(Globals.popSize);
                Parameters x = population[mutate];
                Parameters a = population[r.nextInt(Globals.popSize)];
                Parameters b = population[r.nextInt(Globals.popSize)];
                Parameters c = population[r.nextInt(Globals.popSize)];

                Parameters mutated = x.mutate(a, b, c);
                mutated.solution = starter.generate(g, mutated);
                mutated.cost = mutated.solution.cost(g, false);
                if (mutated.cost < x.cost) {
                    population[mutate] = mutated;
                }
            }
        }
        return bests;
    }
}
