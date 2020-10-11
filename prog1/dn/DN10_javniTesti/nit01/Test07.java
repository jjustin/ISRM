
public class Test07 {

    public static void main(String[] args) {
        Izraz izraz = Izraz.zgradi("3+4*5-6*7/8");
        System.out.println(izraz.steviloOperatorjev());
        System.out.println(izraz.postfiksno());
        System.out.println(izraz.vrednost());
    }
}
