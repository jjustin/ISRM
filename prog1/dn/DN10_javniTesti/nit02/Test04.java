
public class Test04 {

    public static void main(String[] args) {
        Izraz izraz = Izraz.zgradi("9/5");
        System.out.println(izraz.steviloOperatorjev());
        System.out.println(izraz.postfiksno());
        System.out.println(izraz.vrednost());
    }
}
