public class Registers {
    private int A, X, L, B, S, T;
    private double F;
    private int PC, SW;
    public final int SWlt = 0;
    public final int SWeq = 0x40;
    public final int SWgt = 0x80;

    private static final int REG_SIZE = 0xFFFFFF;

    private int wordToInt(int n) {
        return (n << 8) >> 8;
    }

    public int getA() {
        return wordToInt(A);
    }

    public void setA(int a) {
        A = a & REG_SIZE;
    }

    public int getX() {
        return wordToInt(X);
    }

    public void setX(int x) {
        X = x & REG_SIZE;
    }

    public int getL() {
        return wordToInt(L);
    }

    public void setL(int l) {
        L = l & REG_SIZE;
    }

    public int getB() {
        return wordToInt(B);
    }

    public void setB(int b) {
        B = b & REG_SIZE;
    }

    public int getS() {
        return wordToInt(S);
    }

    public void setS(int s) {
        S = s & REG_SIZE;
    }

    public int getT() {
        return wordToInt(T);
    }

    public void setT(int t) {
        T = t & REG_SIZE;
    }

    public double getF() {
        return F;
    }

    public void setF(double f) {
        F = f;
    }

    public int getPC() {
        return wordToInt(PC);
    }

    public void setPC(int pC) {
        if (pC >= REG_SIZE) {
            System.out.println("PC overflow");
        }
        PC = pC & REG_SIZE;
    }

    public int getSW() {
        return wordToInt(SW);
    }

    public void setSWcompare(int val1, int val2) {
        int x = val1 - val2;
        if (x > 0) {
            setSWgt();
        } else if (x < 0) {
            setSWlt();
        } else {
            setSWeq();
        }
    }

    private void setSWlt() {
        SW = SWlt;
    }

    private void setSWeq() {
        SW = SWeq;
    }

    private void setSWgt() {
        SW = SWgt;
    }

    /**
     * 
     * INDEX SETTERS
     * 
     */
    public static final int iA = 0;
    public static final int iX = 1;
    public static final int iL = 2;
    public static final int iB = 3;
    public static final int iS = 4;
    public static final int iT = 5;

    public int getI(int regNo) {
        switch (regNo) {
            case iA:
                return getA();
            case iX:
                return getX();
            case iL:
                return getL();
            case iB:
                return getB();
            case iS:
                return getS();
            case iT:
                return getT();
            default:
                System.out.printf("Unknown register index %d\n", regNo);
                return 0;
        }
    }

    public void setI(int regNo, int val) {
        switch (regNo) {
            case iA:
                setA(val);
                break;
            case iX:
                setX(val);
                break;
            case iL:
                setL(val);
                break;
            case iB:
                setB(val);
                break;
            case iS:
                setS(val);
                break;
            case iT:
                setT(val);
                break;
            default:
                System.out.printf("Unknown register index %d\n", regNo);
        }
    }

    @Override
    public String toString() {
        return String.format("A: %06X\nB: %06X\nX: %06X\nL: %06X\nS: %06X\nT: %06X\nPC: %06X\nSW: %06X", A, B, X, L, S,
                T, PC, SW);
    }
}
