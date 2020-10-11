
public class Test05 {

    public static void main(String[] args) {
        Bankomat bankomat = new Bankomat();
        bankomat.izpisi();
        System.out.println(bankomat.kolicinaDenarja());

        bankomat.nalozi(3, 8, 7);
        bankomat.izpisi();
        System.out.println(bankomat.kolicinaDenarja());

        bankomat.nalozi(4, 0, 5);
        bankomat.izpisi();
        System.out.println(bankomat.kolicinaDenarja());
    }
}
