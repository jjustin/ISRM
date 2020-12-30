package sic.node;

import sic.StorageCodes;
import sic.code.Code;
import sic.code.LstGenerator;
import sic.code.ObjectGenerator;
import sic.common.Oper;
import sic.mnemonic.Mnemonic;

public class Storage extends Node {
    int oper;

    public Storage(Mnemonic mnemonic, Oper o) {
        super(mnemonic);
        this.oper1 = o;
    }

    @Override
    public void activate(Code code) {
        saveLabel(code);
    }

    @Override
    public void resolve(Code code) {
        switch (mnemonic.opcode) {
            case StorageCodes.BYTE:
                oper = oper1.resolve(code);
                if (!(0 <= oper && oper <= 255)) {
                    throw new RuntimeException("Byte to big: " + oper);
                }
                break;
            case StorageCodes.WORD:
                oper = oper1.resolve(code) & 0xFFFFFF;
                if (!(0 <= oper && oper <= Code.MAX_WORD)) {
                    throw new RuntimeException("word to big: " + oper);
                }
                break;
            default:
                oper = oper1.resolve(code);
                break;
        }
    }

    @Override
    public void emitCode(byte[] data, int pos) {
        switch (mnemonic.opcode) {
            case StorageCodes.WORD:
                data[pos + 2] = (byte) (oper & 0xFF);
                data[pos + 1] = (byte) ((oper >> 8) & 0xFF);
                data[pos] = (byte) ((oper >> 16) & 0xFF);
                break;
            case StorageCodes.BYTE:
                data[pos] = (byte) (oper & 0xFF);
                break;
        }
    }

    @Override
    public void emitObj(ObjectGenerator og) {
        switch (mnemonic.opcode) {
            case StorageCodes.WORD:
                og.append(String.format("%06X", oper & 0xFFFFFF));
                break;
            case StorageCodes.BYTE:
                og.append(String.format("%02X", (byte) oper & 0xFF));
                break;
            case StorageCodes.RESB:
                og.skip(oper);
                break;
            case StorageCodes.RESW:
                og.skip(3 * oper);
                break;
        }
    }

    @Override
    public void emitLst(LstGenerator lg) {
        String hexRep = "";
        switch (mnemonic.opcode) {
            case StorageCodes.WORD:
                hexRep = String.format("%06X", oper & 0xFFFFFF);
                break;
            case StorageCodes.BYTE:
                hexRep = String.format("%02X", (byte) oper & 0xFF);
                break;
            case StorageCodes.RESB:
                if (oper <= 3) {
                    hexRep = "00".repeat(oper);
                } else {
                    hexRep = "00..00";
                }
                break;
            case StorageCodes.RESW:
                if (oper <= 1) {
                    hexRep = "000000".repeat(oper);
                } else {
                    hexRep = "00..00";
                }
                break;
        }
        lg.add(location, hexRep, label, mnemonic.name, oper1Str(), oper2Str(), comment);
    }

    @Override
    public int length() {
        switch (mnemonic.opcode) {
            case StorageCodes.RESB:
                return 1 * oper1.resolve();
            case StorageCodes.RESW:
                return 3 * oper1.resolve();
            case StorageCodes.BYTE:
                return 1;
            case StorageCodes.WORD:
                return 3;
        }
        throw new RuntimeException(mnemonic.name + " wrong node");
    }
}
