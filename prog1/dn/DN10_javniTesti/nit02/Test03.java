
public class Test03 {

    public static void main(String[] args) {
        Izraz izraz = Izraz.zgradi("2-7");
        System.out.println(izraz.steviloOperatorjev());
        System.out.println(izraz.postfiksno());
        System.out.println(izraz.vrednost());
    }
}
