package sic.node;

import sic.code.Code;
import sic.code.LstGenerator;
import sic.code.ObjectGenerator;
import sic.common.Oper;
import sic.mnemonic.Mnemonic;

public class InstructionF3 extends InstructionF34 {
    public InstructionF3(Mnemonic mnemonic) {
        super(mnemonic, false);
    }

    public InstructionF3(Mnemonic mnemonic, Oper op, boolean indexed) {
        super(mnemonic, indexed);
        this.oper1 = op;
    }

    @Override
    public void activate(Code code) {
        saveLabel(code);
    }

    @Override
    public void resolve(Code code) {
        if (oper1 != null) {
            oper = oper1.resolve(code);

            ni = oper1.ni();

            int diffBase = oper - (code.baseReg);
            int diffLoc = oper - (code.nextLoc);
            if (code.base && 0 <= diffBase && diffBase <= 4095) {
                // base
                xbpe = 0b0100;
                oper = diffBase;
            } else if (-2048 <= diffLoc && diffLoc <= 2047) {
                // pc relative
                xbpe += 0b0010;
                oper = diffLoc;
            } else if (oper1.isInt()) {
                if (!(0 <= oper && oper <= 4095)) {
                    throw new RuntimeException(String.format("Oper out of bounds (0, 4095): %d", oper));
                }
            }

            if (ni != 0b01 && isIndexed()) {
                // indexed
                xbpe += 0b1000;
            }

            // absolute
            if ((xbpe & 0b0110) == 0) {
                code.addRelocation(location + 1, 3);
            }
        }
    }

    @Override
    public void emitCode(byte[] data, int pos) {
        data[pos] = (byte) ((mnemonic.opcode | ni) & 0xFF);
        data[pos + 1] = (byte) (((xbpe << 4) | (oper >> 8) & 0xF) & 0xFF);
        data[pos + 2] = (byte) (oper & 0xFF);
    }

    public String getHexString() {
        return String.format("%02X%02X%02X", (mnemonic.opcode | ni) & 0xFF, ((xbpe << 4) | (oper >> 8) & 0xF) & 0xFF,
                oper & 0xFF);
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
        return 3;
    }

}
