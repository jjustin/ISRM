
public class Test03 {

    public static void main(String[] args) {
        Datum datum1 = new Datum( 6,  9, 2018);
        Datum datum2 = new Datum( 6, 12, 2018);
        Datum datum3 = new Datum(15,  9, 2018);
        Datum datum4 = new Datum( 6,  9, 2017);
        Datum datum5 = new Datum( 6,  9, 2018);

        System.out.println(datum1.jeEnakKot(datum2));
        System.out.println(datum1.jeEnakKot(datum3));
        System.out.println(datum1.jeEnakKot(datum4));
        System.out.println(datum1.jeEnakKot(datum5));

        System.out.println(datum2.jeEnakKot(datum3));
        System.out.println(datum2.jeEnakKot(datum4));
        System.out.println(datum2.jeEnakKot(datum5));

        System.out.println(datum3.jeEnakKot(datum4));
        System.out.println(datum3.jeEnakKot(datum5));

        System.out.println(datum4.jeEnakKot(datum4));
        System.out.println(datum4.jeEnakKot(datum5));
    }
}
