
/**
 * Naloga2Stevila
 */
import java.util.Scanner;

public class Naloga2Stevila {
    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String type = sc.nextLine();
        int base = sc.nextInt();
        sc.nextLine();
        String a = sc.nextLine();
        String b = sc.nextLine();

        switch (type) {
            case "os":
                os(new Number(a, base), new Number(b, base));
                break;
            case "dv":
                sb.append(dv(a, b, base));
                break;
            case "ka":
                sb.append(ka(a, b, base));
        }
        System.out.println(sb.toString());
    }

    public static void os(Number a, Number b) {
        Number[] collection = new Number[b.nums.length];

        for (int i = 0; i < b.nums.length; i++) {
            collection[i] = a.times(b.nums[i]);
            sb.append(collection[i]);
            sb.append('\n');
            collection[i] = collection[i].timesBase(b.nums.length - 1 - i);
        }

        Number x = collection[0];
        for (int i = 1; i < collection.length; i++) {
            x = x.add(collection[i]); // ne dela
        }

        for (int i = 0; i < x.length(); i++) {
            sb.append('-');
        }

        sb.append('\n');
        sb.append(x);
    }

    public static Number dv(String a, String b, int base) {
        a = a.replaceAll("^0+(?!$)", "");
        b = b.replaceAll("^0+(?!$)", "");

        sb.append(a);
        sb.append(" ");
        sb.append(b);
        sb.append('\n');

        if (a.length() == 1) {
            int aint = Number.convertToInt(a.charAt(0));
            Number out = (new Number(b, base)).times(aint);
            return out;
        } else if (b.length() == 1) {
            int bint = Number.convertToInt(b.charAt(0));
            Number out = (new Number(a, base)).times(bint);
            return out;
        }

        while (a.length() < b.length()) {
            a = "0" + a;
        }
        while (a.length() > b.length()) {
            b = "0" + b;
        }

        int n = a.length();
        if (n % 2 != 0) {
            n++;
        }

        String a1 = a.substring(0, a.length() / 2);
        String a0 = a.substring(a.length() / 2, a.length());
        String b1 = b.substring(0, b.length() / 2);
        String b0 = b.substring(b.length() / 2, b.length());

        Number a0b0 = dv(a0, b0, base);
        sb.append(a0b0);
        sb.append('\n');
        Number a0b1 = dv(a0, b1, base);
        sb.append(a0b1);
        sb.append('\n');
        Number a1b0 = dv(a1, b0, base);
        sb.append(a1b0);
        sb.append('\n');
        Number a1b1 = dv(a1, b1, base);
        sb.append(a1b1);
        sb.append('\n');

        return a1b1.timesBase(n).add(a0b1.add(a1b0).timesBase(n / 2)).add(a0b0);
    }

    public static Number ka(String a, String b, int base) {
        a = a.replaceAll("^0+(?!$)", "");
        b = b.replaceAll("^0+(?!$)", "");

        sb.append(a);
        sb.append(" ");
        sb.append(b);
        sb.append('\n');

        if (a.length() == 1) {
            int aint = Number.convertToInt(a.charAt(0));
            Number out = (new Number(b, base)).times(aint);
            return out;
        } else if (b.length() == 1) {
            int bint = Number.convertToInt(b.charAt(0));
            Number out = (new Number(a, base)).times(bint);
            return out;
        }

        while (a.length() < b.length()) {
            a = "0" + a;
        }
        while (a.length() > b.length()) {
            b = "0" + b;
        }

        int n = a.length();
        if (n % 2 != 0) {
            n++;
        }

        String a1 = a.substring(0, a.length() / 2);
        String a0 = a.substring(a.length() / 2, a.length());
        String b1 = b.substring(0, b.length() / 2);
        String b0 = b.substring(b.length() / 2, b.length());

        Number a0b0 = ka(a0, b0, base);
        sb.append(a0b0);
        sb.append('\n');
        Number a1b1 = ka(a1, b1, base);
        sb.append(a1b1);
        sb.append('\n');
        Number left = new Number(a0, base).add(new Number(a1, base));
        Number right = new Number(b0, base).add(new Number(b1, base));
        Number mix = ka(left.toString(), right.toString(), base);
        sb.append(mix);
        sb.append('\n');

        return a1b1.timesBase(n).add(mix.sub(a1b1).sub(a0b0).timesBase(n / 2)).add(a0b0);
    }

}

class Number {
    int[] nums;
    int base;
    boolean negative = false;

    Number(String x, int base) {
        this.base = base;
        // x = x.toLowerCase();
        if (x.charAt(0) == '-') {
            negative = true;

        }

        nums = new int[x.length()];
        for (int i = 0; i < nums.length; i++) {
            nums[i] = convertToInt(x.charAt(i));
        }
    }

    Number(int[] a, int b, boolean n) {
        nums = a;
        base = b;
        negative = n;
    }

    static int convertToInt(char x) {
        if (x >= '0' && x <= '9')
            return x - '0';
        return x - 'a' + 10;
    }

    static char convertToChar(int x) {
        if (x <= 9)
            return (char) ((char) x + '0');
        return (char) ((char) x + 'a' - 10);
    }

    Number add(Number a) {
        Number n;
        Number m;
        int d;
        if (nums.length < a.nums.length) {
            n = a.copy(1, false);
            m = this;
        } else {
            n = copy(1, false);
            m = a;
        }
        d = n.nums.length - m.nums.length;

        int extra = 0;
        for (int i = m.nums.length - 1; i >= 0; i--) {
            int curr = n.nums[d + i] + m.nums[i] + extra;
            n.nums[d + i] = curr % base;
            extra = curr / base;
        }
        for (int i = d - 1; i >= 0; i--) {
            int curr = n.nums[i] + extra;
            n.nums[i] = curr % base;
            extra = curr / base;
        }

        return n;
    }

    Number sub(Number a) {
        Number n = copy(0, false);
        Number m = a;
        int d = n.nums.length - m.nums.length;

        int extra = 0;
        if (d >= 0) {
            for (int i = m.nums.length - 1; i >= 0; i--) {
                int curr = n.nums[d + i] - m.nums[i] - extra;
                if (curr < 0) {
                    extra = 1;
                    curr += base;
                } else {
                    extra = 0;
                }
                n.nums[d + i] = curr;
            }
            if (extra != 0) {
                n.nums[d - 1] -= extra;
            }
        } else {
            for (int i = n.nums.length - 1; i >= 0; i--) {
                int curr = n.nums[i] - m.nums[i - d] - extra;
                if (curr < 0) {
                    extra = 1;
                    curr += base;
                } else {
                    extra = 0;
                }
                n.nums[i] = curr;
            }
            if (extra != 0) {
                n.nums[0] -= extra;
            }
        }

        return n;
    }

    Number times(int m) {
        Number n = copy(1, false);

        int extra = 0;
        for (int i = n.nums.length - 1; i > 0; i--) {
            int curr = n.nums[i] * m + extra;
            n.nums[i] = curr % base;
            extra = curr / base;
        }
        n.nums[0] = extra;

        return n;

    }

    Number timesBase(int n) {
        return copy(n, true);
    }

    Number copy(int extraSpaces, boolean atEnd) {
        int[] newNums = new int[nums.length + extraSpaces];

        for (int i = 0; i < nums.length; i++) {
            if (atEnd) {
                newNums[i] = nums[i];
            } else {
                newNums[i + extraSpaces] = nums[i];
            }
        }

        return new Number(newNums, base, negative);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        boolean start = false;

        for (int i = 0; i < nums.length; i++) {
            if (!start && nums[i] != 0) {
                start = true;
            }
            if (start) {
                sb.append(convertToChar(nums[i]));
            }
        }
        if (!start) {
            sb.append(0);
        }

        return sb.toString().toLowerCase();
    }

    public int length() {
        boolean start = false;
        int len = 0;
        for (int i = 0; i < nums.length; i++) {
            if (!start && nums[i] != 0) {
                start = true;
            }
            if (start) {
                len++;
            }
        }
        return len;

    }
}