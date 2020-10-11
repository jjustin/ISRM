
public class Test09 {

    public static void main(String[] args) {
        Datum datum = new Datum(15, 3, 2018);

        System.out.println(datum.vrniDan());
        System.out.println(datum.vrniMesec());
        System.out.println(datum.vrniLeto());
        System.out.println(datum.toString());
        System.out.println(datum.jeEnakKot(datum));
        System.out.println(datum.jeEnakKot(new Datum(15, 3, 2018)));
        System.out.println(datum.jeEnakKot(new Datum(15, 3, 2019)));

        System.out.println("----------");
        Bankomat bankomat = new Bankomat();
        bankomat.nalozi(2, 5, 0);
        bankomat.nalozi(6, 2, 1);
        System.out.println(bankomat.vrniN5());
        System.out.println(bankomat.vrniN2());
        System.out.println(bankomat.vrniN1());
        bankomat.izpisi();
        System.out.println(bankomat.kolicinaDenarja());

        System.out.println("----------");
        Datum datum2 = new Datum(20, 1, 2019);
        System.out.println(bankomat.dvigni(9, datum));
        bankomat.izpisi();
        System.out.println(bankomat.dvigni(13, datum));
        bankomat.izpisi();
        System.out.println(bankomat.dvigni(13, datum2));
        bankomat.izpisi();
        System.out.println(bankomat.dvigni(17, datum2));
        bankomat.izpisi();
        System.out.println(bankomat.dvigni(5, datum2));
        bankomat.izpisi();

        System.out.println("----------");
        bankomat.nalozi(10, 10, 10);
        bankomat.dvigni(12, new Datum(3, 2, 2019));
        bankomat.dvigni(11, new Datum(3, 2, 2019));
        bankomat.dvigni(10, new Datum(4, 2, 2019));
        bankomat.dvigni(8, new Datum(4, 2, 2019));
        bankomat.dvigni(5, new Datum(4, 2, 2019));
        bankomat.izpisi();
        System.out.println(bankomat.kolicinaDenarja());

        System.out.println("----------");
        System.out.println(bankomat.najDvig());
        System.out.println(bankomat.najDatum().toString());
    }
}
