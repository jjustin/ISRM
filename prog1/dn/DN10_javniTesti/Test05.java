
public class Test05 {

    public static void main(String[] args) {
        Izraz izraz = Izraz.zgradi("7+9+6");
        System.out.println(izraz.steviloOperatorjev());
        System.out.println(izraz.postfiksno());
        System.out.println(izraz.vrednost());
    }
}
