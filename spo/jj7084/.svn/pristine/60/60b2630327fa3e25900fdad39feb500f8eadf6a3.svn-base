package sic.node;

import sic.Directives;
import sic.code.Code;
import sic.code.LstGenerator;
import sic.code.ObjectGenerator;
import sic.common.Oper;
import sic.mnemonic.Mnemonic;

public class Directive extends Node {
    int len = 0;
    int oper = 0;

    public Directive(Mnemonic m) {
        super(m);
    }

    public Directive(Mnemonic m, Oper o1) {
        super(m);
        this.oper1 = o1;
    }

    @Override
    public void activate(Code code) {
        switch (mnemonic.opcode) {
            case Directives.START:
                code.start = oper1.resolve();
                code.nextLoc = code.start;
                code.name = label;
                saveLabel(code);
                break;
            case Directives.ORG:
                oper = oper1.resolve(code);
                len = oper - code.locCounter;
                saveLabel(code);
                break;
            case Directives.EQU:
                saveLabel(code, oper1.resolve(code));
                break;
        }
    }

    @Override
    public void resolve(Code code) {
        switch (mnemonic.opcode) {
            case Directives.BASE:
                code.baseReg = oper1.resolve(code);
                code.base = true;
                break;
            case Directives.NOBASE:
                code.base = false;
                break;
            case Directives.END:
                oper = oper1.resolve(code);
                break;
        }
    }

    @Override
    public void emitCode(byte[] data, int pos) {
    }

    @Override
    public void emitObj(ObjectGenerator og) {
        switch (mnemonic.opcode) {
            case Directives.END:
                og.setE(oper);
                break;
            case Directives.ORG:
                og.jumpTo(oper);
                break;
        }
    }

    @Override
    public void emitLst(LstGenerator lg) {
        lg.add(location, "", label, mnemonic.name, oper1Str(), oper2Str(), comment);
    }

    @Override
    public void leave(Code code) {
        switch (mnemonic.opcode) {
            case Directives.ORG:
                code.nextLoc = oper;
        }
    }

    @Override
    public int length() {
        return len;
    }
}
