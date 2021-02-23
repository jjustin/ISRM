public class Runner2 extends Runner {
    @Override
    public Solution run(Graph g) {
        Solution bests = null;
        double bestc = Double.POSITIVE_INFINITY;
        double prevc = 0;
        StarterGreedy2 starter = new StarterGreedy2();
        for (double i = 1; i < 100; i += 0.5) {
            Parameters p = new Parameters(i);
            Solution sol = starter.generate(g, p);
            double c = sol.cost(g, false);
            // if (c != prevc) {
            // System.out.printf("Cost change @%f: %f\n", i, c);
            // prevc = c;
            // }
            if (c < bestc) {
                bests = sol;
                bestc = c;
            }
        }
        return bests;
    }
}
