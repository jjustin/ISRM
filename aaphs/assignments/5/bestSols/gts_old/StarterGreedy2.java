import java.util.ArrayList;

public class StarterGreedy2 {
    public Solution generate(Graph g, Parameters p) {
        Solution sol = new Solution();
        double[] trash = g.currentTrash();
        boolean stillTrashToCollect = true;
        while (stillTrashToCollect) {
            // generate new truck
            Truck truck = new Truck();
            int u = 0; // current node

            TruckState ts = new TruckState(0.5, 0, 10, trash, 0);

            boolean isFinished = false;
            ArrayList<Node> best = null;
            while (!isFinished) {
                // get which is best
                best = findBestPathFor(g, u, ts, p);
                // System.out.printf("Found path: %s", best);

                // remove found node from all checks
                Node traveledTo = best.get(best.size() - 1);
                u = traveledTo.ix; // update the starting node

                best.remove(best.size() - 1); // do not append last one
                truck.add(best);
                isFinished = traveledTo.ix == 0;
            }
            stillTrashToCollect = false;
            for (double t : trash) {
                if (t > 0) {
                    stillTrashToCollect = true;
                    break;
                }
            }
            truck.add(g.nodes[0]);
            sol.addTruck(truck);
        }
        return sol;
    }

    public ArrayList<Node> findBestPathFor(Graph g, int u, TruckState ts, Parameters p) {
        int w = (int) Math.ceil(ts.truckWeigth);
        double[][] dist = g.fwDist[w];

        double mindist = Double.POSITIVE_INFINITY;
        int bestv = 0;
        for (int v = 0; v < Globals.nOfSites; v++) {
            int wAfterVisit = (int) Math.ceil(ts.truckWeigth + ts.trash[v]);
            if (ts.trash[v] > 0 && ts.trash[v] <= (Globals.maxWeigth - ts.truckWeigth) && dist[u][v] < mindist) {
                if (p.maxWorkTime >= (ts.time + (dist[u][v] + g.fwDist[wAfterVisit][v][0]) / 50)) {
                    bestv = v;
                    mindist = dist[u][v];
                }
            }
        }
        if (u == 0 && bestv == u) {
            for (int v = 0; v < Globals.nOfSites; v++) {
                if (ts.trash[v] > 0 && ts.trash[v] <= (Globals.maxWeigth - ts.truckWeigth) && dist[u][v] < mindist) {
                    bestv = v;
                    mindist = dist[u][v];
                }
            }
        }

        ArrayList<Node> out = g.findAndSimulatePath(u, bestv, ts);
        Node last = out.get(out.size() - 1);
        if (last.ix != bestv) {
            out.addAll(findBestPathFor(g, last.ix, ts, p));
        }
        return out;
    }
}
