package sic.common;

import sic.code.Code;
import sic.node.Node;

public class Oper {
    int value;
    String prefix = "";
    Node literal;
    String label;
    Register reg;

    public Oper(String l) {
        label = l;
    }

    public Oper(int value) {
        this.value = value;
    }

    public Oper(int value, String prefix) {
        this.value = value;
        this.prefix = prefix;
    }

    public Oper(String l, String prefix) {
        this.label = l;
        this.prefix = prefix;
    }

    public Oper(Register reg) {
        this.reg = reg;
    }

    public int resolve(Code code) {
        if (prefix.equals("*")) {
            return code.locCounter;
        }
        if (prefix.equals("=")) {
            literal = code.addLiteralSymbol(value);
            label = literal.label;
            prefix = "";
            return literal.location;
        } else if (label != null) {
            return code.resolveSymbol(label);
        } else if (reg != null) {
            return reg.number;
        }
        return value;
    }

    public int resolve() {
        if (label != null || reg != null || literal != null) {
            throw new RuntimeException("Should be number: " + label);
        }
        return value;
    }

    public int ni() {
        if (prefix.equals("") || prefix.equals("=")) {
            return 0b11;
        }
        if (prefix.equals("@")) {
            return 0b10;
        }
        if (prefix.equals("#")) {
            return 0b01;
        }
        throw new RuntimeException("Unknown prefix: " + prefix);
    }

    public boolean isInt() {
        return label == null && reg == null && !prefix.equals("=");
    }

    @Override
    public String toString() {
        String out = prefix;
        if (out.equals("*")) {
            return out;
        }
        if (literal != null) {
            return label + String.format("(WORD %d)", value);
        }
        if (label != null)
            out += label;
        else if (reg != null)
            out += reg.reg;
        else
            out += String.format("%d", value);

        return out;
    }
}
