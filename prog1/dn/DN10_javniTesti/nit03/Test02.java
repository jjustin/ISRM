
public class Test02 {

    public static void main(String[] args) {
        Izraz izraz = Izraz.zgradi("0");
        System.out.println(izraz.steviloOperatorjev());
        System.out.println(izraz.postfiksno());
        System.out.println(izraz.vrednost());
    }
}
