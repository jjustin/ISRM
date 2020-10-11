
public class TestKoren {

    public static void main(String[] args) {
        for (int i = 0;  i < 3;  i++) {
            double epsilon;
            switch (i) {
                case 0:
                    epsilon = 1e-2;
                    break;
                case 1:
                    epsilon = 1e-8;
                    break;
                default:
                    epsilon = 1e-14;
                    break;
            }

            System.out.println("epsilon = " + epsilon);
            System.out.printf("%5s %20s %20s %20s%n", "arg", "Funkcije.koren(arg)", "Math.sqrt(arg)", "|razlika|");
            for (int j = -9;  j <= 9;  j++) {
                double arg = (10 + (double) j) / 10;
                double f = Funkcije.koren(arg, epsilon);
                double sqrt = Math.sqrt(arg);
                double razlika = Math.abs(f - sqrt);
                System.out.printf("%5.1f %20.17f %20.17f %20g%n", arg, f, sqrt, razlika);
            }
            System.out.println();
        }
    }
}
