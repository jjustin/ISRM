public class Runner2 extends Runner {
    @Override
    public Solution run(Graph g) {
        Solution bests = null;
        double bestc = Double.POSITIVE_INFINITY;
        double prevc = 0;
        StarterGreedy2 starter = new StarterGreedy2();
        double d = 1;
        for (double i = 1; i < 100; i += d) {
            Parameters p = new Parameters(i);
            Solution sol = starter.generate(g, p);
            double c = sol.cost(g, false);
            if (c < bestc) {
                bests = sol;
                bestc = c;
            }
            if (c > prevc) {
                i -= d;
                if (d >= 0.00000000001) {
                    d = d / 10;
                } else {
                    break;
                }
                // System.out.printf("Cost change @%f: %f\n", i, c);
                prevc = c;
            }
        }
        return bests;
    }
}
