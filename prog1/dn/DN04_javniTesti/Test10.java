
import java.util.Random;

public class Test10 {

    public static void main(String[] args) {
        Random random = new Random(123456);

        Bankomat b1 = new Bankomat();
        Bankomat b2 = new Bankomat();

        for (int i = 0;  i < 100;  i++) {
            int izbira = random.nextInt(4);
            Datum datum = new Datum(i / 10 + 1, 1, 2001);

            int n5 = random.nextInt(100);
            int n2 = random.nextInt(100);
            int n1 = random.nextInt(100);
            int dvig = random.nextInt(750) + 1;

            switch (izbira) {
                case 0:
                    System.out.println("b1");
                    b1.izpisi();
                    System.out.printf("nalozi(%d, %d, %d)%n", n5, n2, n1);
                    b1.nalozi(n5, n2, n1);
                    b1.izpisi();
                    break;

                case 1:
                    System.out.println("b1");
                    b1.izpisi();
                    System.out.printf("dvigni(%d, %s)%n", dvig, datum.toString());
                    System.out.println(b1.dvigni(dvig, datum));
                    b1.izpisi();
                    break;

                case 2:
                    System.out.println("b2");
                    b2.izpisi();
                    System.out.printf("nalozi(%d, %d, %d)%n", n5, n2, n1);
                    b2.nalozi(n5, n2, n1);
                    b2.izpisi();
                    break;

                case 3:
                    System.out.println("b2");
                    b2.izpisi();
                    System.out.printf("dvigni(%d, %s)%n", dvig, datum.toString());
                    System.out.println(b2.dvigni(dvig, datum));
                    b2.izpisi();
                    break;
            }
            System.out.println();
        }

        System.out.println("--- b1 ---");
        b1.izpisi();
        System.out.println(b1.kolicinaDenarja());
        System.out.println(b1.najDvig());
        System.out.println(b1.najDatum().toString());
        System.out.println();

        System.out.println("--- b2 ---");
        b2.izpisi();
        System.out.println(b2.kolicinaDenarja());
        System.out.println(b2.najDvig());
        System.out.println(b2.najDatum().toString());
    }
}
