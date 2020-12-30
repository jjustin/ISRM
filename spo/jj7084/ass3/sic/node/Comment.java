package sic.node;

import sic.code.Code;
import sic.code.LstGenerator;
import sic.code.ObjectGenerator;

public class Comment extends Node {
    public Comment(String comm) {
        super();
        this.comment = comm;
    }

    @Override
    public void enter(Code code) {
    }

    @Override
    public void activate(Code code) {
    }

    @Override
    public void leave(Code code) {
    }

    @Override
    public void resolve(Code code) {
    }

    @Override
    public void emitCode(byte[] data, int pos) {
    }

    @Override
    public void emitObj(ObjectGenerator og) {
    }

    @Override
    public void emitLst(LstGenerator lg) {
        lg.add(comment);
    }

    @Override
    public int length() {
        return 0;
    }

    @Override
    public String toString() {
        return comment;
    }
}
