public class RunnerSingleRun extends Runner {
    @Override
    public Solution run(Graph g) {
        StarterGreedy starter = new StarterGreedy();
        return starter.generateStarting(g);
    }
}
