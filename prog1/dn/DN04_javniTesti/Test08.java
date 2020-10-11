
public class Test08 {

    public static void main(String[] args) {
        Datum datum = new Datum(6, 9, 2018);

        Bankomat bankomat = new Bankomat();
        bankomat.nalozi(10, 10, 10);
        System.out.println(bankomat.dvigni(40, datum));
        System.out.println(bankomat.dvigni(50, datum));
        bankomat.nalozi(10, 10, 10);
        System.out.println(bankomat.dvigni(45, datum));
        System.out.println(bankomat.dvigni(30, datum));
        bankomat.nalozi(20, 20, 20);
        System.out.println(bankomat.dvigni(42, datum));
        System.out.println(bankomat.dvigni(25, datum));

        System.out.println(bankomat.najDvig());
    }
}
