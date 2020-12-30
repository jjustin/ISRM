package sic.common;

import java.util.HashMap;

public class RegisterMapper {
    static HashMap<Integer, Register> map = new HashMap<>() {
        {
            // AXLBSTF
            put(0, new Register("A", 0));
            put(1, new Register("X", 1));
            put(2, new Register("L", 2));
            put(3, new Register("V", 3));
            put(4, new Register("S", 4));
            put(5, new Register("T", 5));
            put(6, new Register("F", 6));
            put(7, new Register("PC", 7));
            put(8, new Register("SW", 8));
        }
    };

    public static Register getRegister(int i) {
        return map.get(i);
    }
}
