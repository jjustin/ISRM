public class Memory {
    public static int MAX_ADDRESS = 1048576; // 1 MB = 2^20 B
    private final byte[] ram = new byte[MAX_ADDRESS];

    public int getByte(int addr) {
        if (addr > MAX_ADDRESS) {
            System.out.printf("Address out of bounds %d\n", addr);
            return 0;
        }
        return ((int) ram[addr]) & 0xFF;
    }

    /**
     * setByte saves last byte of `val` to `addr`
     */
    public void setByte(int addr, int val) {
        if (addr > MAX_ADDRESS) {
            System.out.printf("Address out of bounds %d\n", addr);
            return;
        }
        ram[addr] = (byte) (val & 0xFF);
    }

    public int getWord(int addr) {
        return (getByte(addr) << 16) | (getByte(addr + 1) << 8) | (getByte(addr + 2));
    }

    public void setWord(int addr, int val) {
        setByte(addr, val >> 16);
        setByte(addr + 1, val >> 8);
        setByte(addr + 2, val);
    }

    public double getFloat(int addr) {
        long val = ((long) getWord(addr) << 24) | getWord(addr + 3);
        return Double.longBitsToDouble(val << 16);
    }

    public void setFloat(int addr, double val) {
        long lVal = Double.doubleToLongBits(val) >> 16;
        setWord(addr, (int) (lVal >> 24)); // set first 24 bits == 1 word
        setWord(addr + 3, (int) val);
    }

    String toScreen(int start, int rows, int columns) {
        String out = "";
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                char c = (char) ram[i * columns + j + start];
                if (c == 0) {
                    c = ' ';
                }
                out += c;
            }
            out += '\n';
        }
        return out;
    }

    String toString(int start, int rows, int columns) {
        String out = "";
        for (int i = 0; i < rows; i++) {
            out += String.format("%06X ", i * columns + start);
            for (int j = 0; j < columns; j++) {
                out += String.format(" %02X", ram[i * columns + j + start]);
            }
            out += '\n';
        }
        return out;

    }
}
