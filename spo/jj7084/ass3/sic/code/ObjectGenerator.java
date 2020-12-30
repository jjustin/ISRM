package sic.code;

// used for generating obj files
public class ObjectGenerator {
    public static final int MAX_T_LEN = 30;

    StringBuilder sb;
    int offset;
    int currentBlockLen;
    StringBuilder currentBlock;

    int end;

    ObjectGenerator(String name, int start, int programLen) {
        sb = new StringBuilder();
        appendStart(name, start, programLen);

        offset = start;
        end = start;
        currentBlockLen = 0;
        currentBlock = new StringBuilder();
    }

    protected void appendStart(String name, int start, int programLen) {
        sb.append(String.format("H%-6s%06X%06X\n", name, start, programLen));
    }

    public void append(String s, int len) {
        if (currentBlockLen + len / 2 >= MAX_T_LEN) {
            addTEntry();
        }
        currentBlock.append(s);
        currentBlockLen += len / 2; // we want the length in nibbles
    }

    public void append(String s) {
        append(s, s.length());
    }

    public void skip(int n) {
        addTEntry(offset + currentBlockLen + n);
    }

    private void addTEntry(int offsetTo) {
        if (currentBlockLen > 0) { // if currentBlockLen==0 => nothing was added to last block
            appendT();
            offset = offsetTo;
            currentBlockLen = 0;
            currentBlock = new StringBuilder();
        }
    }

    protected void appendT() {
        sb.append(String.format("T%06X%02X%s\n", offset, currentBlockLen, currentBlock.toString()));
    }

    private void addTEntry() {
        addTEntry(offset + currentBlockLen);
    }

    public void jumpTo(int n) {
        addTEntry(n);
    }

    public void addE() {
        if (currentBlockLen > 0) {
            addTEntry();
        }
        appendE();
    }

    protected void appendE() {
        sb.append(String.format("E%06X\n", end));
    }

    public void addM(int addr, int len) {
        if (currentBlockLen > 0) {
            addTEntry();
        }
        appendM(addr, len);
    }

    protected void appendM(int addr, int len) {
        sb.append(String.format("M%06X%02X\n", addr, len));
    }

    public void setE(int e) {
        end = e;
    }

    @Override
    public String toString() {
        return sb.toString();
    }
}
