
public class Test07 {

    public static void main(String[] args) {
        Datum datum = new Datum(6, 9, 2018);

        Bankomat bankomat = new Bankomat();
        bankomat.nalozi(10, 10, 1);
        System.out.println(bankomat.dvigni(8, datum));
        bankomat.izpisi();
        System.out.println(bankomat.dvigni(8, datum));
        bankomat.izpisi();
        System.out.println(bankomat.dvigni(23, datum));
        bankomat.izpisi();
        System.out.println(bankomat.dvigni(26, datum));
        bankomat.izpisi();
        System.out.println(bankomat.dvigni(27, datum));
        bankomat.izpisi();
    }
}
