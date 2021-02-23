import java.io.BufferedReader;
import java.io.FileReader;

public class Checker {
    public static void main(String[] args) {
        for (int problem = 1; problem <= 10; problem++) {
            Graph g = new Graph(String.format("../Problem%d.txt", problem));
            BufferedReader objReader = null;
            try {
                objReader = new BufferedReader(new FileReader(String.format("../za oddajo/solution%d.txt", problem)));
            } catch (Exception ex) {
                System.out.printf("File not found for problem: %d\n", problem);
                continue;
            }
            double c = 0;
            String lStr = "";
            try {
                lStr = objReader.readLine();
            } catch (Exception ex) {
                System.out.println(ex);
                return;
            }
            for (int i = 1; i <= 3; i++) {
                Globals.trashType = i;
                Solution sol = new Solution();
                while ((i == 3 && lStr != null) || (i != 3 && Integer.parseInt(String.valueOf(lStr.charAt(0))) == i)) {
                    String[] l = lStr.split(",");
                    Truck truck = new Truck();
                    for (int j = 1; j < l.length; j++) {
                        truck.add(g.nodes[Integer.parseInt(l[j]) - 1]);
                    }
                    sol.addTruck(truck);

                    try {
                        lStr = objReader.readLine();
                    } catch (Exception ex) {
                        System.out.println(ex);
                        return;
                    }
                }
                c += sol.cost(g, true);
            }
            System.out.printf("Problem %s cost: %.3f\n", problem, c);
        }
    }
}
