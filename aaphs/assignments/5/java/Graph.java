import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Graph
 */
public class Graph {
    Node[] nodes;
    private double[][] trash;
    ArrayList<Edge> edges;

    double avgEdgeLen = 0;

    int[][][] fwNext;
    double[][][] fwDist;

    public Graph(String file) {
        BufferedReader objReader;
        try {
            objReader = new BufferedReader(new FileReader(file));

            String[] l = objReader.readLine().split(",");
            Globals.nOfSites = Integer.parseInt(l[0]);
            Globals.maxWeigth = Integer.parseInt(l[1]);

            this.nodes = new Node[Globals.nOfSites];
            this.edges = new ArrayList<Edge>();
            this.trash = new double[3][Globals.nOfSites];
            this.fwNext = new int[Globals.maxWeigth + 1][Globals.nOfSites][Globals.nOfSites];
            this.fwDist = new double[Globals.maxWeigth + 1][Globals.nOfSites][Globals.nOfSites];

            // parse nodes
            for (int i = 0; i < Globals.nOfSites; i++) {
                l = objReader.readLine().split(",");

                Node n = new Node(Integer.parseInt(l[0]), Double.parseDouble(l[1]), Double.parseDouble(l[2]),
                        Double.parseDouble(l[3]), Double.parseDouble(l[4]), Double.parseDouble(l[5]));
                this.nodes[i] = n;
                this.trash[0][i] = n.trash[0];
                this.trash[1][i] = n.trash[1];
                this.trash[2][i] = n.trash[2];
            }

            l = objReader.readLine().split(",");

            // parse edges
            while (true) {
                // read data
                int f = Integer.parseInt(l[0]) - 1;
                int t = Integer.parseInt(l[1]) - 1;
                Node from = nodes[f];
                Node to = nodes[t];
                double len = Double.parseDouble(l[2]);
                boolean twoWay = Integer.parseInt(l[3]) == 0;
                double maxW = Double.parseDouble(l[4]);
                // create new edge
                Edge e = new Edge(from, to, len, maxW);
                this.edges.add(e);
                from.addEdge(this, e);
                if (twoWay) {
                    // create reverse edge
                    e = new Edge(to, from, len, maxW);
                    to.addEdge(this, e);
                }
                String lStr = objReader.readLine();
                if (lStr == null) {
                    break;
                }
                l = lStr.split(",");
            }
            floWar();
            objReader.close();
        } catch (FileNotFoundException ex) {
            System.err.println("File not found");
            return;
        } catch (IOException ex) {
            System.err.println("IOex " + ex.getMessage());
            return;
        }
    }

    private void floWar() {
        for (int w = 0; w <= Globals.maxWeigth; w++) {
            double[][] dist = new double[Globals.nOfSites][Globals.nOfSites];
            int[][] next = new int[Globals.nOfSites][Globals.nOfSites];
            for (double[] row : dist)
                Arrays.fill(row, Double.POSITIVE_INFINITY);
            for (int[] row : next)
                Arrays.fill(row, -1);

            for (Edge e : edges) {
                avgEdgeLen += e.len;
                if (e.maxW >= w && dist[e.u.ix][e.v.ix] > e.len) {
                    dist[e.u.ix][e.v.ix] = e.len;
                    next[e.u.ix][e.v.ix] = e.v.ix;
                }
            }

            avgEdgeLen = avgEdgeLen / edges.size();

            for (Node v : nodes) {
                dist[v.ix][v.ix] = 0;
                next[v.ix][v.ix] = v.ix;
            }
            for (int k = 0; k < nodes.length; k++) {
                for (int i = 0; i < nodes.length; i++) {
                    for (int j = 0; j < nodes.length; j++) {
                        if (dist[i][j] > dist[i][k] + dist[k][j]) {
                            dist[i][j] = dist[i][k] + dist[k][j];
                            next[i][j] = next[i][k];
                        }
                    }
                }
            }
            fwNext[w] = next;
            fwDist[w] = dist;
        }
    }

    public ArrayList<Node> path(int u, int v, double tw) {
        int w = (int) Math.ceil(tw);
        int[][] next = fwNext[w];
        ArrayList<Node> path = new ArrayList<>();
        if (next[u][v] == -1) {
            System.err.printf("Path %d->%d not found for w=%d", u + 1, v + 1, w);
            return path;
        }
        path.add(nodes[u]);
        while (u != v) {
            u = next[u][v];
            path.add(nodes[u]);
        }
        return path;
    }

    // this will return if v can no longer be collected
    public ArrayList<Node> findAndSimulatePath(int u, int v, TruckState ts) {
        int w = (int) Math.ceil(ts.truckWeigth);
        int[][] next = fwNext[w];
        double[][] dist = fwDist[w];
        ArrayList<Node> path = new ArrayList<>();
        if (next[u][v] == -1) {
            System.err.printf("Path %d->%d not found for w=%d", u + 1, v + 1, w);
            return path;
        }
        path.add(nodes[u]);
        int uprev = u;
        while (u != v) {
            ts.pathLen += dist[uprev][u];
            ts.cost += dist[uprev][u] * 0.1;
            if (ts.trash[u] != 0 && ts.trash[u] <= (Globals.maxWeigth - ts.truckWeigth)) {
                ts.time += 0.2;
                ts.truckWeigth += ts.trash[u]; // collect current trash
                ts.trash[u] = 0;
                next = fwNext[(int) Math.ceil(ts.truckWeigth)];
                if (ts.trash[v] > Globals.maxWeigth - ts.truckWeigth) {
                    return path; // v can no longer be collected
                }
            }
            uprev = u;
            u = next[u][v];
            path.add(nodes[u]);
        }
        // empty last stop if possible
        if (ts.trash[u] != 0 && ts.trash[u] <= (Globals.maxWeigth - ts.truckWeigth)) {
            ts.truckWeigth += ts.trash[u]; // collect current trash
            ts.trash[u] = 0;
        }

        return path;
    }

    public double[] currentTrash() {
        return Arrays.copyOf(this.trash[Globals.trashType - 1], this.trash[Globals.trashType - 1].length);
    }
}

class Node {
    ArrayList<Edge>[] edges;
    ArrayList<Edge>[][] edgesTo;
    int ix;
    double trash[];
    double x;
    double y;

    Node(int ix, double x, double y, double organic, double plastic, double paper) {
        this.edgesTo = new ArrayList[Globals.maxWeigth + 1][Globals.nOfSites];
        this.ix = ix - 1;
        this.x = x;
        this.y = y;
        this.trash = new double[] { organic, plastic, paper };
        this.edges = new ArrayList[Globals.maxWeigth + 1];
    }

    public void addEdge(Graph g, Edge newE) {
        g.edges.add(newE);
        for (int w = 0; w <= Globals.maxWeigth; w++) {
            if (newE.maxW < w) {
                return;
            }
            if (edgesTo[w][newE.v.ix] == null) {
                edgesTo[w][newE.v.ix] = new ArrayList<Edge>();
            }
            if (edges[w] == null) {
                edges[w] = new ArrayList<Edge>();
            }
            ArrayList<Edge> et = edgesTo[w][newE.v.ix];
            ArrayList<Edge> es = edges[w];

            es.add(newE);
            et.add(newE);
        }
    }

    public ArrayList<Edge> edgesTo(Node n) {
        return edgesTo[0][n.ix];
    }

    public ArrayList<Edge> edgesTo(Node n, double tw) {
        int w = (int) Math.ceil(tw);
        return edgesTo[w][n.ix];
    }

    @Override
    public String toString() {
        return String.format("%d", ix + 1);
    }
}

class Edge {
    Node u;
    Node v;
    double len;
    double maxW;

    public Edge(Node u, Node v, double len, double maxW) {
        this.u = u;
        this.v = v;
        this.len = len;
        this.maxW = maxW;
    }

    @Override
    public String toString() {
        return String.format("%s->%s", u.toString(), v.toString());
    }
}