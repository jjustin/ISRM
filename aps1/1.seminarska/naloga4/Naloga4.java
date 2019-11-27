import java.io.*;

/**
 * Naloga4
 */
public class Naloga4 {
    public static void main(String[] args) throws IOException {
        if (args.length < 2) {
            System.out.println("Uporaba: java Naloga4 <vhod> <izhod>");
            System.exit(1);
        }

        BufferedReader in = new BufferedReader(new FileReader(args[0]));
        PrintWriter izhod = new PrintWriter(new FileOutputStream(args[1]));

        try {
            int n = Integer.parseInt(in.readLine());

            Map bags = new Map(n);
            int v, v1, v2, c;
            for (int i = 0; i < n; i++) {
                String[] req = in.readLine().split(",");

                switch (req[0]) {
                case "U":
                    // parse "x:y" blocks into table
                    int[] values = new int[req.length - 2];
                    int[] counts = new int[req.length - 2];
                    for (int j = 0; j < req.length - 2; j++) {
                        String[] vals = req[j + 2].split(":");
                        values[j] = Integer.parseInt(vals[0]);
                        counts[j] = Integer.parseInt(vals[1]);
                    }

                    // save new bag into a map
                    bags.set(Integer.parseInt(req[1]), new Bag(values, counts));
                    break;

                case "Z":
                    v1 = Integer.parseInt(req[1]);
                    v2 = Integer.parseInt(req[2]);

                    // add bag v2 to bag v1
                    ((Bag) bags.get(v1)).add((Bag) bags.get(v2));
                    break;

                case "R":
                    v1 = Integer.parseInt(req[1]);
                    v2 = Integer.parseInt(req[2]);

                    // remove bag v2 from bag v1
                    ((Bag) bags.get(v1)).remove((Bag) bags.get(v2));

                    break;

                case "S":
                    v1 = Integer.parseInt(req[1]);
                    v2 = Integer.parseInt(req[2]);

                    // intersect bags v2 and v1
                    ((Bag) bags.get(v1)).keepCommon((Bag) bags.get(v2));

                    break;

                case "P":
                    v = Integer.parseInt(req[1]);
                    c = Integer.parseInt(req[2]);

                    ((Bag) bags.get(v)).cut(c);
                    break;

                case "O":
                    v = Integer.parseInt(req[1]);
                    c = Integer.parseInt(req[2]);

                    ((Bag) bags.get(v)).keep(c);
                    break;

                case "I":
                    v = Integer.parseInt(req[1]);
                    izhod.write(((Bag) bags.get(v)).toString());
                    break;

                default:
                    System.out.println("Unknown command: " + req[0]);
                }
            }

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

    static class Bag {

        Map bag;

        Bag(int[] values, int[] counts) {
            bag = new Map(values.length);
            for (int i = 0; i < values.length; i++) {
                add(values[i], counts[i]);
            }
        }

        void add(int value, int count) {
            Object nObj = bag.get(value);
            if (nObj == null) {
                set(value, count); // if not present add new element to map bag
                return;
            }

            int n = (int) nObj;
            set(value, n + count); // add new count to current value
        }

        void remove(int value, int count) {
            Object nObj = bag.get(value);
            // value doesnt exists - there is nothing to remove
            if (nObj == null) {
                return;
            }

            int x = (int) nObj - count;

            // if every element has been removed, remove the value from storage
            if (x <= 0) {
                bag.delete(value);
                return;
            }

            set(value, x);
        }

        void remove(int i) {
            bag.delete(i);
            return;
        }

        void set(int i, int count) {
            bag.set(i, count);
        }

        void add(Bag b) {
            // iterate over all values in a bag - add the ones that appear
            int[] minMax = b.bag.minMaxKey(); // [min, max]
            for (int i = minMax[0]; i <= minMax[1]; i++) {
                Object v = b.bag.get(i);

                if (v == null) {
                    continue;
                }

                add(i, (int) v);
            }
        }

        void remove(Bag b) {
            // iterate over all values in a bag - and remove the ones needed
            int[] minMax = b.bag.minMaxKey(); // [min, max]
            for (int i = minMax[0]; i <= minMax[1]; i++) {
                Object v = b.bag.get(i);

                if (v == null) {
                    continue;
                }

                remove(i, (int) v);
            }

        }

        void keepCommon(Bag b) {
            int[] minMax = bag.minMaxKey(); // [min, max]
            for (int i = minMax[0]; i <= minMax[1]; i++) {
                Object val1 = bag.get(i);

                if (val1 == null) { // val1 is not in bag1, ignore it
                    continue;
                }

                Object val2 = b.bag.get(i);
                if (val2 == null) { // val2 is not in bag2, remove it from bag1
                    remove(i);
                    continue;
                }

                int v1 = (int) val1;
                int v2 = (int) val2;
                if (v2 >= v1) {// if less or equal elements are in bag1
                    continue;
                }

                set(i, v2); // remove excess values in bag1
            }
        }

        // keeps elements with less than c occurances
        void cut(int c) {
            int[] minMax = bag.minMaxKey(); // [min, max]

            for (int i = minMax[0]; i <= minMax[1]; i++) {
                Object objV = bag.get(i);

                if (objV == null) {
                    continue;
                }
                int v = (int) objV;
                if (v > c) {
                    remove(i, v - c);
                }
            }

        }

        // keeps elements with more than c occurances
        void keep(int c) {
            int[] minMax = bag.minMaxKey(); // [min, max]

            for (int i = minMax[0]; i <= minMax[1]; i++) {
                Object v = bag.get(i);

                if (v == null) {
                    continue;
                }
                if ((int) v < c) {
                    remove(i);
                }
            }
        }

        public String toString() {
            int[] minMax = bag.minMaxKey(); // [min, max]
            StringBuilder sb = new StringBuilder();

            for (int i = minMax[0]; i <= minMax[1]; i++) {
                Object v = bag.get(i);

                if (v == null) {
                    continue;
                }

                sb.append(i);
                sb.append(":");
                sb.append((int) v);

                if (i != minMax[1]) {
                    sb.append(',');
                }
            }
            sb.append('\n');
            return sb.toString();
        }
    }

    static class MapNode {
        int key;
        Object value;

        public MapNode(int key, Object value) {
            this.key = key;
            this.value = value;
        }

        public void setValue(Object value) {
            this.value = value;
        }

        public boolean equals(Object obj) {
            if (obj instanceof MapNode) {
                MapNode node = (MapNode) obj;
                return key == node.key;
            }

            return false;
        }

        public String toString() {
            return "[" + key + ", " + value + "]";
        }
    }

    public static class Map {
        Set[] table;

        public Map(int size) {
            table = new Set[size];

            for (int i = 0; i < table.length; i++) {
                table[i] = new Set();
            }
        }

        private int hash(Object d) {
            return Math.abs(d.hashCode()) % table.length;
        }

        public void set(int k, Object v) {
            Set l = table[hash(k)];
            MapNode node = new MapNode(k, v);

            Object pos = l.locate(node);

            if (pos != null) {
                ((MapNode) l.retrieve((Set.Element) pos)).setValue(v);
            } else {
                l.insert(node);
            }
        }

        public Object get(int k) {
            Set l = table[hash(k)];

            Set.Element pos = l.locate(new MapNode(k, null));

            if (pos != null) {
                return l.retrieve(pos).value;
            }

            return null;
        }

        public void delete(int d) {
            Set l = table[hash(d)];
            Object pos = l.locate(new MapNode(d, null));

            if (pos != null) {
                l.delete((Set.Element) pos);
            }
        }

        public int[] minMaxKey() {
            int max = Integer.MIN_VALUE;
            int min = Integer.MAX_VALUE;

            for (Set set : table) {
                for (Set.Element c = set.first; c != null; c = c.next) {
                    if (c.element != null) {
                        if (c.element.key > max) {
                            max = c.element.key;
                        }
                        if (c.element.key < min) {
                            min = c.element.key;
                        }
                    }
                }
            }
            return new int[] { min, max };
        }
    }

    public static class Set {
        Set.Element first;

        static class Element {
            MapNode element;
            Element next;

            Element() {
                element = null;
                next = null;
            }
        }

        public Set() {
            first = new Set.Element();
        }

        public void insert(MapNode obj) {
            if (locate(obj) == null) {
                Set.Element newSE = new Set.Element();
                newSE.element = obj;
                newSE.next = first.next;
                first.next = newSE;
            }
        }

        public void delete(Set.Element pos) {
            pos.next = pos.next.next;
        }

        public MapNode retrieve(Set.Element pos) {
            return pos.next.element;
        }

        public Set.Element locate(Object obj) {
            // check if obj exists in iter
            for (Set.Element iter = first; iter.next != null; iter = iter.next) {
                if (obj.equals(retrieve(iter))) {
                    return iter;
                }
            }

            return null;
        }
    }
}