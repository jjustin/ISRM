import java.util.ArrayList;
import java.util.Arrays;

public class Solution {
    ArrayList<Truck> trucks;
    Parameters params;

    Solution() {
        trucks = new ArrayList<Truck>();
    }

    public void addTruck(Truck truck) {
        trucks.add(truck);
    }

    public void removeTruck(Truck truck) {
        trucks.remove(truck);
    }

    public void removeTruck(int truckIx) {
        trucks.remove(truckIx);
    }

    public void switchTrucks(int ix1, int ix2) {
        if (ix1 == ix2) {
            System.err.println("Ix1 == Ix2");
        }
        if (ix2 < ix1) {
            int tmp = ix1;
            ix1 = ix2;
            ix2 = tmp;
        }

        Truck t1 = trucks.get(ix1);
        Truck t2 = trucks.get(ix2);

        trucks.set(ix1, t2);
        trucks.set(ix2, t1);
    }

    public double cost(Graph g, boolean log) {
        double[] trash = g.currentTrash();

        double cost = 0;
        for (Truck tr : trucks) {
            ArrayList<Node> route = tr.toArrayList();
            cost += 10;
            double w = 0; // weight of current truck
            double roadLen = 0; // length of ride
            double t = 0.5; // time (unload counted in)

            if (route.get(route.size() - 1).ix != 0 && route.get(0).ix != 0) {
                if (log)
                    System.err.println("Last or first stop is not firm");
                return (Double.POSITIVE_INFINITY);
            }

            Node nprev = route.get(0);
            boolean firstIter = true;
            for (Node n : route) {
                if (firstIter) {
                    firstIter = false;
                    continue;
                }
                // get possible edges
                ArrayList<Edge> edgs = nprev.edgesTo(n, w);
                if (edgs == null || edgs.size() == 0) {
                    if (log)
                        System.err.printf("NonExistent connection used: %s->%s w: %f\nTruck: %s\n", nprev, n, w, tr);
                    return (Double.POSITIVE_INFINITY);
                }
                // get best edge
                double shortestCost = Double.POSITIVE_INFINITY;
                for (Edge e : edgs) {
                    if (e.len < shortestCost) {
                        shortestCost = e.len;
                    }
                }
                roadLen += shortestCost;
                if (trash[n.ix] != 0) { // already picked up
                    double newW = w + trash[n.ix];
                    if (newW <= Globals.maxWeigth) {
                        w = newW;
                        trash[n.ix] = 0;
                        t += 0.2;
                    }
                }
                nprev = n;
            }
            if (w == 0) {
                if (log)
                    System.err.printf("Nothing picked up: %s\n", tr);
            }
            cost += roadLen * 0.1;
            t += roadLen / 50; // 50 kmh
            if (t > 8) {
                cost += 20 * (t - 8) + 80;
            } else {
                cost += 10 * t;
            }
        }

        double trashsum = 0;
        for (double t : trash) {
            trashsum += t;
        }
        if (trashsum != 0) {
            if (log)
                System.err.printf("Not all trash was collected: %s\n", Arrays.toString(trash));
            return (Double.POSITIVE_INFINITY);
        }
        return cost;
    }

    public Solution clone() {
        Solution out = new Solution();
        for (Truck truck : trucks) {
            out.addTruck(truck.clone());
        }
        return out;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Truck truck : trucks) {
            sb.append(truck.toString());
            sb.append("\n");
        }
        return sb.toString();
    }
}

class Truck {
    ArrayList<Node> truck;

    public Truck() {
        truck = new ArrayList<>();
    }

    public Truck(ArrayList<Node> tr) {
        truck = tr;
    }

    public void add(ArrayList<Node> a) {
        truck.addAll(a);
    }

    public void add(Node e) {
        truck.add(e);
    }

    public ArrayList<Node> toArrayList() {
        return truck;
    }

    public Truck clone() {
        System.out.println(this.toString());
        return new Truck((ArrayList<Node>) truck.clone());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Globals.trashType);
        for (Node node : truck) {
            sb.append(',');
            sb.append(node.toString());
        }
        return sb.toString();
    }
}