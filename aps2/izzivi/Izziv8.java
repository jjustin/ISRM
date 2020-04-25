import java.util.*;

class Complex {
    double re;
    double im;

    public Complex(double real, double imag) {
        re = real;
        im = imag;
    }

    public String toString() {
        double tRe = (double) Math.round(re * 100000) / 100000;
        double tIm = (double) Math.round(im * 100000) / 100000;
        if (tIm == 0)
            return tRe + "";
        if (tRe == 0)
            return tIm + "i";
        if (tIm < 0)
            return tRe + "-" + (-tIm) + "i";
        return tRe + "+" + tIm + "i";
    }

    public Complex conj() {
        return new Complex(re, -im);
    }

    // sestevanje
    public Complex plus(Complex b) {
        Complex a = this;
        double real = a.re + b.re;
        double imag = a.im + b.im;
        return new Complex(real, imag);
    }

    // odstevanje
    public Complex minus(Complex b) {
        Complex a = this;
        double real = a.re - b.re;
        double imag = a.im - b.im;
        return new Complex(real, imag);
    }

    // mnozenje z drugim kompleksnim stevilo
    public Complex times(Complex b) {
        Complex a = this;
        double real = a.re * b.re - a.im * b.im;
        double imag = a.re * b.im + a.im * b.re;
        return new Complex(real, imag);
    }

    // mnozenje z realnim stevilom
    public Complex times(double alpha) {
        return new Complex(alpha * re, alpha * im);
    }

    // reciprocna vrednost kompleksnega stevila
    public Complex reciprocal() {
        double scale = re * re + im * im;
        return new Complex(re / scale, -im / scale);
    }

    // deljenje
    public Complex divides(Complex b) {
        Complex a = this;
        return a.times(b.reciprocal());
    }

    // e^this
    public Complex exp() {
        return new Complex(Math.exp(re) * Math.cos(im), Math.exp(re) * Math.sin(im));
    }

    // potenca komplesnega stevila
    public Complex pow(int k) {

        Complex c = new Complex(1, 0);
        for (int i = 0; i < k; i++) {
            c = c.times(this);
        }
        return c;
    }
}

public class Izziv8 {
    static StringBuilder sb = new StringBuilder();
    static LinkedList<Integer> roots = new LinkedList<Integer>();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int m = sc.nextInt();
        int n = (int) Math.pow(2, Math.ceil(Math.log(2 * (m - 1) + 1) / Math.log(2)));

        double angle = (2 * Math.PI) / n;
        Complex[] omegas = new Complex[n];
        omegas[0] = new Complex(1, 0);
        omegas[1] = new Complex(Math.cos(angle), Math.sin(angle)); // nth root
        for (int i = 2; i < n; i++) {
            omegas[i] = omegas[i - 1].times(omegas[1]);
        }

        Complex[] a = new Complex[n];
        for (int i = 0; i < m; i++) {
            a[i] = new Complex(sc.nextDouble(), 0);
        }
        for (int i = m; i < n; i++) {
            a[i] = new Complex(0, 0);
        }

        Complex[] b = new Complex[n];
        for (int i = 0; i < m; i++) {
            b[i] = new Complex(sc.nextDouble(), 0);
        }
        for (int i = m; i < n; i++) {
            b[i] = new Complex(0, 0);
        }

        // fill aC
        Complex[] aC = ffe(a, omegas);
        // print aC
        for (int i = 0; i < aC.length; i++) {
            sb.append(aC[i].toString() + " ");
        }
        sb.append("\n");

        // print bC
        Complex[] bC = ffe(b, omegas);
        for (int i = 0; i < bC.length; i++) {
            sb.append(bC[i].toString() + " ");
        }
        sb.append("\n");

        // inverse omegas
        omegas[0] = new Complex(1, 0);
        omegas[1] = new Complex(Math.cos(angle), -Math.sin(angle)); // nth root
        for (int i = 2; i < n; i++) {
            omegas[i] = omegas[i - 1].times(omegas[1]);
        }

        // fill cC
        Complex[] cC = new Complex[n];
        for (int i = 0; i < bC.length; i++) {
            cC[i] = aC[i].times(bC[i]);
        }
        cC = ffe(cC, omegas);

        // print cC
        for (int i = 0; i < cC.length; i++) {
            sb.append(cC[i].toString() + " ");
        }
        sb.append("\n");

        // print c
        for (int i = 0; i < cC.length; i++) {
            Complex x = cC[i].times(1.0 / n);
            sb.append(x.toString() + " ");
        }
        sb.append("\n");

        System.out.println(sb.toString());
    }

    public static Complex[] ffe(Complex[] polinom, Complex[] omegas) {
        if (polinom.length == 1) {
            return polinom;
        }

        Complex[] liha = new Complex[polinom.length / 2];
        Complex[] soda = new Complex[polinom.length / 2];
        for (int i = 0; i < polinom.length; i++) {
            if (i % 2 == 0) { // sodo
                soda[i / 2] = polinom[i];
            } else {
                liha[i / 2] = polinom[i];
            }
        }

        Complex[] sodaC = ffe(soda, omegas);
        if (sodaC.length != 1) {
            for (int i = 0; i < sodaC.length; i++) {
                sb.append(sodaC[i].toString() + " ");
            }
            sb.append("\n");
        }
        Complex[] lihaC = ffe(liha, omegas);
        if (lihaC.length != 1) {
            for (int i = 0; i < lihaC.length; i++) {
                sb.append(lihaC[i].toString() + " ");
            }
            sb.append("\n");
        }

        int k = omegas.length / polinom.length;
        Complex[] polinomC = new Complex[polinom.length];

        for (int i = 0; i < polinomC.length / 2; i++) {
            polinomC[i] = sodaC[i].plus(omegas[k * i].times(lihaC[i]));
            polinomC[i + polinomC.length / 2] = sodaC[i].minus(omegas[k * i].times(lihaC[i]));
        }

        return polinomC;
    }
}