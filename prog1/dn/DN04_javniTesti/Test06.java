
public class Test06 {

    public static void main(String[] args) {
        Datum datum = new Datum(6, 9, 2018);

        Bankomat bankomat = new Bankomat();
        bankomat.nalozi(0, 1, 1);
        bankomat.izpisi();
        System.out.println(bankomat.dvigni(4, datum));
        bankomat.izpisi();
        System.out.println(bankomat.dvigni(1, datum));
        bankomat.izpisi();
        System.out.println(bankomat.dvigni(2, datum));
        bankomat.izpisi();
        System.out.println(bankomat.dvigni(1, datum));
        bankomat.izpisi();
        System.out.println("----------");

        bankomat.nalozi(5, 3, 4);
        bankomat.izpisi();
        System.out.println(bankomat.dvigni(4, datum));
        bankomat.izpisi();
        System.out.println(bankomat.dvigni(4, datum));
        bankomat.izpisi();
        System.out.println(bankomat.dvigni(3, datum));
        bankomat.izpisi();
        System.out.println(bankomat.dvigni(1, datum));
        bankomat.izpisi();
    }
}
