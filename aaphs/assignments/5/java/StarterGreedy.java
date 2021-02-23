import java.util.ArrayList;
import java.util.HashSet;

public class StarterGreedy {
    public Solution generateStarting(Graph g) {
        Solution sol = new Solution();
        HashSet<Node> toCheck = new HashSet<>();
        for (Node n : g.nodes) {
            toCheck.add(n);
        }
        double[] trash = g.currentTrash();

        while (!toCheck.isEmpty()) {
            // generate new truck
            Truck truck = new Truck();
            int u = 0; // current node
            double w = 0;

            boolean isFinished = false;
            ArrayList<Node> best = null;
            while (!isFinished) {
                // get which is best
                best = findBestPathFor(g, u, trash, w);
                // System.out.printf("Found path: %s", best);

                // remove found node from all checks
                Node traveledTo = best.get(best.size() - 1);
                u = traveledTo.ix; // update the starting node
                w += trash[u];
                trash[u] = 0; // trash has been collected
                toCheck.remove(traveledTo); // no longer needing that node

                best.remove(best.size() - 1); // do not append last one
                truck.add(best);
                isFinished = traveledTo.ix == 0;
            }
            toCheck.removeAll(best);
            truck.add(g.nodes[0]);
            sol.addTruck(truck);
        }
        return sol;
    }

    public ArrayList<Node> findBestPathFor(Graph g, int u, double[] trash, double tw) {
        int w = (int) Math.ceil(tw);
        double[][] dist = g.fwDist[w];

        double mindist = Double.POSITIVE_INFINITY;
        int bestv = 0;
        for (int v = 0; v < Globals.nOfSites; v++) {
            if (trash[v] > 0 && trash[v] <= (Globals.maxWeigth - tw) && dist[u][v] < mindist) {
                bestv = v;
                mindist = dist[u][v];
            }
        }

        return g.path(u, bestv, tw);
    }
}
