package sic.node;

import sic.code.Code;
import sic.code.HtmlGenerator;
import sic.code.LstGenerator;
import sic.code.ObjectGenerator;
import sic.common.Oper;
import sic.mnemonic.Mnemonic;

public abstract class Node {
    public String label;
    Oper oper1;
    Oper oper2;
    Mnemonic mnemonic;
    String comment = "";
    public int location;

    Node() {
    }

    Node(Mnemonic mnemonic) {
        this.mnemonic = mnemonic;
    }

    public String oper1Str() {
        if (oper1 != null) {
            return oper1.toString();
        }
        return "";
    }

    public String oper2Str() {
        if (oper2 != null) {
            return oper2.toString();
        }
        return "";
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setLabel(String label) {
        if (label == null) {
            label = "";
        }
        this.label = label;
    }

    public void setMnemonic(Mnemonic mnemonic) {
        this.mnemonic = mnemonic;
    }

    public boolean isLabelEmpty() {
        return label == null || label.equals("");
    }

    public void enter(Code code) {
        code.locCounter = code.nextLoc;
        code.nextLoc += length();
        location = code.locCounter;
    }

    public void saveLabel(Code code) {
        saveLabel(code, code.locCounter);
    }

    public void saveLabel(Code code, int value) {
        if (!isLabelEmpty()) {
            code.defineSymbol(label, value);
        }
    }

    public abstract void emitCode(byte[] data, int pos);

    public void leave(Code code) {
    }

    public abstract void activate(Code code);

    public abstract void resolve(Code code);

    public abstract void emitObj(ObjectGenerator og);

    public abstract void emitLst(LstGenerator lg);

    public void emitHtml(HtmlGenerator hg) {
        emitLst(hg.lstGenerator);
        emitObj(hg.objGenerator);
    }

    public abstract int length();

    @Override
    public String toString() {
        String out = String.format("%06X %-10s ", location, (isLabelEmpty()) ? "" : label);
        if (mnemonic != null) {
            out += mnemonic.toString();
        }
        if (oper1 != null) {
            out += oper1.toString() + " ";
            if (oper2 != null) {
                out += ", " + oper2.toString() + " ";
            }
        }
        if (comment != null && !comment.equals("")) {
            out += comment;
        }

        return out;
    }
}
