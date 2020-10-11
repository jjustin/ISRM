
public class Test04 {

    public static void main(String[] args) {
        Bankomat bankomat = new Bankomat();
        System.out.println(bankomat.vrniN5());
        System.out.println(bankomat.vrniN2());
        System.out.println(bankomat.vrniN1());

        bankomat.nalozi(3, 8, 7);
        System.out.println(bankomat.vrniN5());
        System.out.println(bankomat.vrniN2());
        System.out.println(bankomat.vrniN1());

        bankomat.nalozi(4, 0, 5);
        System.out.println(bankomat.vrniN5());
        System.out.println(bankomat.vrniN2());
        System.out.println(bankomat.vrniN1());
    }
}
