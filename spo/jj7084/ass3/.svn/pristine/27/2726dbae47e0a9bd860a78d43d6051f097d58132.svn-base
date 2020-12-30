package sic.node;

import sic.code.Code;
import sic.code.LstGenerator;
import sic.code.ObjectGenerator;
import sic.common.Oper;
import sic.mnemonic.Mnemonic;

public class InstructionF2 extends Node {
    int oper;

    public InstructionF2(Mnemonic mnemonic, Oper o1) {
        super(mnemonic);
        this.oper1 = o1;
    }

    public InstructionF2(Mnemonic mnemonic, Oper o1, Oper o2) {
        super(mnemonic);
        this.oper1 = o1;
        this.oper2 = o2;
    }

    @Override
    public void activate(Code code) {
        saveLabel(code);
    }

    @Override
    public void resolve(Code code) {
        oper = oper1.resolve(code) << 4;
        if (oper2 != null) {
            oper += oper2.resolve(code);
        }

        if (oper > 0xFF) {
            throw new RuntimeException(String.format("Oper too big F2: %X", oper));
        }
    }

    @Override
    public void emitCode(byte[] data, int pos) {
        data[pos] = (byte) (mnemonic.opcode & 0xFF);
        data[pos + 1] = (byte) (oper & 0xFF);
    }

    public String getHexString() {
        return String.format("%02X%02X", mnemonic.opcode, oper);
    }

    @Override
    public void emitObj(ObjectGenerator og) {
        og.append(getHexString());
    }

    @Override
    public void emitLst(LstGenerator lg) {
        lg.add(location, getHexString(), label, mnemonic.name, oper1Str(), oper2Str(), comment);
    }

    @Override
    public int length() {
        return 2;
    }
}
