public class RunnerSingleRun extends Runner {
    @Override
    public Solution run(Graph g) {
        Starter starter = new StarterGreedy();
        return starter.generateStarting(g);
    }
}
