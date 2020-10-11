
public class Test01 {

    public static void main(String[] args) {
        Datum datum1 = new Datum(6, 9, 2018);
        System.out.println(datum1.vrniDan());
        System.out.println(datum1.vrniMesec());
        System.out.println(datum1.vrniLeto());

        Datum datum2 = new Datum(27, 10, 2017);
        System.out.println(datum2.vrniDan());
        System.out.println(datum2.vrniMesec());
        System.out.println(datum2.vrniLeto());

        Datum datum3 = new Datum(19, 2, 2019);
        System.out.println(datum3.vrniDan());
        System.out.println(datum3.vrniMesec());
        System.out.println(datum3.vrniLeto());
    }
}
