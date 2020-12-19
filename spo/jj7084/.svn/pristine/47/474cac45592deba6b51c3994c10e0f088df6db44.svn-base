import java.io.Reader;

public class Utils {
    static String readString(Reader r, int len) {
        String out = "";
        for (int i = 0; i < len; i++) {
            try {
                out += (char) r.read();
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
        return out;
    }

    static int readByte(Reader r) {
        try {
            return (hexCharToInt((char) r.read()) << 4) + hexCharToInt((char) r.read());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return 0;
        }
    }

    static int readWord(Reader r) {
        try {
            return (readByte(r) << 16) | (readByte(r) << 8) | (readByte(r));
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return 0;
        }
    }

    public static int hexCharToInt(char ch) {
        switch (ch) {
            case '0':
                return 0;
            case '1':
                return 1;
            case '2':
                return 2;
            case '3':
                return 3;
            case '4':
                return 4;
            case '5':
                return 5;
            case '6':
                return 6;
            case '7':
                return 7;
            case '8':
                return 8;
            case '9':
                return 9;
            case 'A':
                return 10;
            case 'B':
                return 11;
            case 'C':
                return 12;
            case 'D':
                return 13;
            case 'E':
                return 14;
            case 'F':
                return 15;
            default:
                System.out.println("NON HEX CHAR FOUND");
                return 0;
        }
    }
}
