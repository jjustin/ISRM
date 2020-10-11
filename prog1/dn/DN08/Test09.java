
import java.util.Arrays;

public class Test09 {

    public static void main(String[] args) {
        Naloga.Modul[] moduli = {
            new Naloga.Modul(2345, "Programska oprema"),
            new Naloga.Modul(7654, "Strojna oprema"),
            new Naloga.Modul(1357, "Informatika")
        };

        Naloga.Izvajalec[] izvajalci = {
            new Naloga.Izvajalec(725, "Ana Arko"),
            new Naloga.Izvajalec(318, "Boris Bergant"),
            new Naloga.Izvajalec(944, "Cvetka Cevc"),
            new Naloga.Izvajalec( 56, "Denis Dolenc"),
            new Naloga.Izvajalec(801, "Eva Erlah"),
            new Naloga.Izvajalec( 39, "Franci Frece"),
            new Naloga.Izvajalec(490, "Gabrijela Govekar"),
            new Naloga.Izvajalec(623, "Hinko Holc")
        };

        Naloga.Predmet[] vsiPredmeti = {
            new Naloga.Predmet(79805, "Analiza", 6, 0, null,
                    new Naloga.Izvajalec[]{izvajalci[2], izvajalci[6]}),

            new Naloga.Predmet(94023, "Digitalna obdelava signalov", 8, 2, moduli[1],
                    new Naloga.Izvajalec[]{izvajalci[1]}),

            new Naloga.Predmet(61932, "Diskretne strukture", 6, 0, null,
                    new Naloga.Izvajalec[]{izvajalci[2], izvajalci[4]}),

            new Naloga.Predmet(24860, "Informacijski sistemi", 5, 2, moduli[2],
                    new Naloga.Izvajalec[]{izvajalci[3], izvajalci[6], izvajalci[7]}),

            new Naloga.Predmet(13470, "Operacijski sistemi", 9, 2, moduli[0],
                    new Naloga.Izvajalec[]{izvajalci[4]}),

            new Naloga.Predmet(39815, "Osnove ekonomije", 4, 1, null,
                    new Naloga.Izvajalec[]{izvajalci[0]}),

            new Naloga.Predmet(33426, "Podatkovne baze", 7, 2, moduli[2],
                    new Naloga.Izvajalec[]{izvajalci[6]}),

            new Naloga.Predmet(52131, "Prevajalniki", 6, 2, moduli[0],
                    new Naloga.Izvajalec[]{izvajalci[1], izvajalci[4]}),

            new Naloga.Predmet(40036, "Umetna inteligenca", 7, 2, moduli[0],
                    new Naloga.Izvajalec[]{izvajalci[5], izvajalci[7]}),

            new Naloga.Predmet(85193, "Vhodno-izhodne naprave", 4, 2, moduli[1],
                    new Naloga.Izvajalec[]{izvajalci[5], izvajalci[6]})
        };

        Naloga.Predmet[] izborPredmetov = {
            vsiPredmeti[0], vsiPredmeti[1], vsiPredmeti[2],
            vsiPredmeti[4], vsiPredmeti[7], vsiPredmeti[9]
        };

        Naloga.Predmetnik predmetnik = new Naloga.Predmetnik(izborPredmetov);

        System.out.println(predmetnik.steviloIzvajalcev());
    }
}
