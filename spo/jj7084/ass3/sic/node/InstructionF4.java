package sic.node;

import sic.code.Code;
import sic.code.LstGenerator;
import sic.code.ObjectGenerator;
import sic.common.Oper;
import sic.mnemonic.Mnemonic;

public class InstructionF4 extends InstructionF34 {
    public InstructionF4(Mnemonic mnemonic, Oper op, boolean indexed) {
        super(mnemonic, indexed);
        this.oper1 = op;
    }

    @Override
    public void activate(Code code) {
        saveLabel(code);
    }

    @Override
    public void resolve(Code code) {
        oper = oper1.resolve(code);
        ni = oper1.ni();
        xbpe = 1;

        if (ni != 0b01 && isIndexed()) {
            xbpe += 0b1000;
        }

        code.addRelocation(location + 1, 5);
    }

    @Override
    public void emitCode(byte[] data, int pos) {
        data[pos] = (byte) ((mnemonic.opcode | ni) & 0xFF);
        data[pos + 1] = (byte) (((xbpe << 4) | oper >> 16) & 0xFF);
        data[pos + 2] = (byte) ((oper >> 8) & 0xFF);
        data[pos + 2] = (byte) (oper & 0xFF);
    }

    public String getHexString() {
        int first = (mnemonic.opcode | ni) & 0xFF;
        int second = ((xbpe << 4) | oper >> 16) & 0xFF;
        int third = (oper >> 8) & 0xFF;
        int fourth = oper & 0xFF;
        return String.format("%02X%02X%02X%02X", first, second, third, fourth);
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
        return 4;
    }
}
