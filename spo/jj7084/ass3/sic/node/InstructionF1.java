package sic.node;

import sic.code.Code;
import sic.code.LstGenerator;
import sic.code.ObjectGenerator;
import sic.mnemonic.Mnemonic;

public class InstructionF1 extends Node {
    public InstructionF1(Mnemonic mnemonic) {
        super(mnemonic);
    }

    @Override
    public void activate(Code code) {
        saveLabel(code);
    }

    @Override
    public void resolve(Code code) {
    }

    @Override
    public void emitCode(byte[] data, int pos) {
        data[pos] = (byte) (mnemonic.opcode & 0xFF);
    }

    public String getHexString() {
        return String.format("%02X", mnemonic.opcode);
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
        return 1;
    }
}
