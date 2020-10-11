
public class Test01 {

    public static void main(String[] args) {
        Izraz izraz = Izraz.zgradi("7");
        System.out.println(izraz.steviloOperatorjev());
        System.out.println(izraz.postfiksno());
        System.out.println(izraz.vrednost());
    }
}
