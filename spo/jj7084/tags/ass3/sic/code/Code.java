package sic.code;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sic.common.Oper;
import sic.mnemonic.Mnemonic;
import sic.node.Node;
import sic.node.Storage;

public class Code {
    public static final int MAX_ADDR = 1048576;
    public static final int MAX_WORD = 16777216; // 2^24 / 2 -1

    public int objCurrentLen;
    public int objCurrentOffset;

    public String name;
    public int start = 0;

    public int locCounter = 0;
    public int nextLoc = 0;
    public int baseReg = 0;
    public boolean base = false;
    int programLen = 0;

    List<Node> program = new ArrayList<>();
    HashMap<String, Integer> labels = new HashMap<>();
    HashMap<Integer, Integer> relocation = new HashMap<>();
    List<Node> literals = new ArrayList<>();
    int literalSymbolCounter = 0;

    // used for generating nodes for literals
    Mnemonic wordMnemonic;

    public Code(Mnemonic wordMnemonic) {
        this.wordMnemonic = wordMnemonic;
    }

    public void defineSymbol(String sym, int val) {
        if (labels.containsKey(sym)) {
            throw new RuntimeException("duplicate label " + sym);
        }
        labels.put(sym, val);
    }

    // returns address where literal is stored
    public Node addLiteralSymbol(int val) {
        // prepare literal data
        String label = "*" + literalSymbolCounter;
        labels.put(label, val);
        literalSymbolCounter++;

        //
        Node n = new Storage(wordMnemonic, new Oper(val));
        n.location = (programLen);
        n.setLabel(label);
        programLen += 3;

        literals.add(n);
        return n;
    }

    public int resolveSymbol(String sym) {
        if (!labels.containsKey(sym)) {
            throw new RuntimeException("Key does not exists: " + sym);
        }
        return labels.get(sym);
    }

    public void append(Node n) {
        program.add(n);
        n.enter(this);
        n.activate(this);
        programLen = Math.max(programLen, nextLoc - start);
        n.leave(this);
    }

    public void addRelocation(int address, int len) {
        if (len > 5) {
            throw new RuntimeException("Lenght of relocation operand > 5");
        }
        relocation.put(address, len);
    }

    public void resolve() {
        begin();
        for (Node n : program) {
            n.enter(this);
            n.resolve(this);
            n.leave(this);
        }
        for (Node n : literals) {
            n.enter(this);
            n.resolve(this);
            n.leave(this);
        }
        program.addAll(literals);
        end();
    }

    public byte[] emitCode() {
        byte[] out = new byte[programLen];
        int pos = 0;
        begin();
        for (Node n : program) {
            n.enter(this);
            n.emitCode(out, pos);
            pos += n.length();
            n.leave(this);
        }
        end();
        return out;
    }

    public String emitObj() {
        begin();

        ObjectGenerator og = new ObjectGenerator(name, start, programLen);
        for (Node n : program) {
            n.enter(this);
            n.emitObj(og);
            n.leave(this);
        }
        for (Map.Entry<Integer, Integer> e : relocation.entrySet()) {
            og.addM(e.getKey(), e.getValue());
        }
        og.addE();

        end();
        return og.toString();
    }

    public String emitLst() {
        begin();
        LstGenerator lg = new LstGenerator();
        for (Node n : program) {
            n.enter(this);
            n.emitLst(lg);
            n.leave(this);
        }
        end();
        return lg.toString();
    }

    public String emitHtml() {
        begin();
        HtmlGenerator hg = new HtmlGenerator(name, start, programLen);
        for (Node n : program) {
            n.enter(this);
            n.emitHtml(hg);
            n.leave(this);
        }
        for (Map.Entry<Integer, Integer> e : relocation.entrySet()) {
            hg.objGenerator.addM(e.getKey(), e.getValue());
        }
        hg.objGenerator.addE();

        end();
        return hg.toString();
    }

    public void begin() {
        locCounter = start;
        nextLoc = start;
    }

    public void end() {
    }

    @Override
    public String toString() {
        String out = "";
        for (Node n : program) {
            out += n.toString() + "\n";
        }
        return out;
    }
}
