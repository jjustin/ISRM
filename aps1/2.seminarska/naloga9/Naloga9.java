import java.io.*;
import java.util.*;

/**
 * Naloga8
 */
public class Naloga9 {
    static PrintWriter izhod;
    static StringBuilder output = new StringBuilder();
    static Point[] points;

    public static void main(String[] args) throws IOException {
        if (args.length < 2) {
            System.out.println("Uporaba: java Naloga1 <mreža> <izhod>");
            System.exit(1);
        }

        BufferedReader in = new BufferedReader(new FileReader(args[0]));
        izhod = new PrintWriter(new FileOutputStream(args[1]));
        try {
            int n = Integer.parseInt(in.readLine());
            points = new Point[n];
            for (int i = 0; i < n; i++) {
                String[] larr = in.readLine().split(",");
                points[i] = new Point(Double.parseDouble(larr[0]), Double.parseDouble(larr[1]), i);
            }
            int m = Integer.parseInt(in.readLine());

            int numOfDis = (n * (n - 1)) / 2;
            TreeSet<Distance> distances = new TreeSet<Distance>();

            // calculate distances
            int k = 0;
            for (int i = 0; i < n; i++) {
                for (int j = i + 1; j < n; j++) {
                    distances.add(new Distance(points[i], points[j]));
                    k++;
                }
            }

            Distance[] d = new Distance[numOfDis];
            distances.toArray(d);
            int i = 0;
            for (k = 0; k < n - m; k++) {
                Distance dst = null;
                // poisci najblizje
                while (true) {
                    dst = d[i];
                    i++;
                    if (dst.a.sosede == dst.b.sosede) {
                        if (dst.a.sosede == null) { // se nista v skupini
                            dst.createGroup();
                            break;
                        }
                    } else {
                        if (dst.a.sosede == null || dst.b.sosede == null) {
                            // eden je v skupini, drugi se ni
                            dst.addGroup();
                        } else {
                            dst.joinGroups();
                        }
                        break; // nista v isti skupini
                    }
                }
            }

            for (i = 0; i < n; i++) {
                points[i].buildString();
            }

            izhod.write(output.toString());
        } catch (IOException ex) {
            System.out.println(ex);
            System.exit(1);
        } finally {
            if (in != null)
                in.close();
            if (izhod != null)
                izhod.close();
        }
    }

    static double len(Point a, Point b) {
        return Math.sqrt(Math.pow(a.x - b.x, 2) + Math.pow(a.y - b.y, 2));
    }

    static class Point implements Comparable<Point> {
        double x;
        double y;
        int i;
        TreeSet<Point> sosede;
        boolean printed = false;

        Point(double a, double b, int i) {
            x = a;
            y = b;
            this.i = i + 1;
        }

        public void buildString() {
            if (printed) {
                return;
            }
            if (sosede == null) {
                printed = true;
                output.append(i);
                output.append('\n');
                return;
            }
            for (Point p : sosede) {
                p.printed = true;
                output.append(p.i);
                output.append(',');
            }
            output.setLength(output.length() - 1);
            output.append('\n');
        }

        @Override
        public int compareTo(Point p) {
            return i - p.i;
        }

    }

    static class Distance implements Comparable<Distance> {
        Point a;
        Point b;
        Double distance;

        Distance(Point a, Point b) {
            this.a = a;
            this.b = b;
            distance = len(a, b);
        }

        // nobena se nima soseda
        void createGroup() {
            a.sosede = new TreeSet<Point>();
            b.sosede = a.sosede;

            a.sosede.add(a);
            a.sosede.add(b);
        }

        // obe imata sosede
        void joinGroups() {
            for (Point x : b.sosede) {
                a.sosede.add(x);
                x.sosede = a.sosede;
            }
        }

        // ena ze ima sosede
        void addGroup() {
            if (a.sosede == null && b.sosede != null) {
                a.sosede = b.sosede;
                a.sosede.add(a);
            } else if (b.sosede == null && a.sosede != null) {
                b.sosede = a.sosede;
                b.sosede.add(b);
            }
        }

        @Override
        public int compareTo(Distance dst) {
            return distance.compareTo(dst.distance);
        }

        public String toString() {
            return Double.toString(distance);
        }
    }
}
