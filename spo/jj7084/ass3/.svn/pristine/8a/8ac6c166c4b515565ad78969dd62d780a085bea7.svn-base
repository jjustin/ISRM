package sic.node;

import sic.mnemonic.Mnemonic;

public abstract class InstructionF34 extends Node {
    int oper;
    int ni = 0;
    int xbpe = 0;
    boolean indexed;

    InstructionF34(Mnemonic m, boolean indexed) {
        super(m);
        this.indexed = indexed;
    }

    boolean isIndexed() {
        return indexed;
    }

    @Override
    public String oper1Str() {
        if (oper1 != null) {
            return oper1.toString() + (isIndexed() ? ",X" : "");
        }
        return "";
    }

}
