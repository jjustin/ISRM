import java.util.ArrayList;
import java.util.Random;

public class StarterGreedy2 {
    public Solution generate(Graph g, Parameters p) {
        Solution sol = new Solution();
        sol.params = p;
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

        boolean isTrashEmpty = true;
        for (double t : ts.trash) {
            if (t > 0) {
                isTrashEmpty = false;
                break;
            }
        }

        if (isTrashEmpty) {
            return g.findAndSimulatePath(u, 0, ts);
        }

        if (Math.random() < p.chooseRandom) {
            Random r = new Random();
            int v = u;
            while (v == u) {
                v = r.nextInt(ts.trash.length);
            }
            return g.findAndSimulatePath(u, v, ts);
        }

        double mindist = Double.POSITIVE_INFINITY;
        int bestv = 0;
        for (int v = 0; v < Globals.nOfSites; v++) {
            int wAfterVisit = (int) Math.ceil(ts.truckWeigth + ts.trash[v]);
            if (ts.trash[v] > 0 && ts.trash[v] <= (Globals.maxWeigth - ts.truckWeigth) && dist[u][v] < mindist) {
                if (p.maxWorkTime >= (ts.time + (dist[u][v] + g.fwDist[wAfterVisit][v][0]) / 50)
                        && p.longestPath >= (ts.pathLen + dist[u][v])
                        && (Globals.maxWeigth - p.emptySpaceOnTruck) >= ts.truckWeigth + ts.trash[v]) {
                    bestv = v;
                    mindist = dist[u][v];
                }
            }
        }
        // no contrains in case there is not way to calculate new path based on the
        // constrians (reach unreachable paths)
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
            ArrayList<Node> out2 = findBestPathFor(g, last.ix, ts, p);
            out2.remove(0);
            out.addAll(out2);
        }
        return out;
    }
}
