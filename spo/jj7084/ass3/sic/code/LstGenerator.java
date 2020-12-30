package sic.code;

public class LstGenerator {
    StringBuilder sb = new StringBuilder();

    public void add(int location, String hexRep, String label, String name, String oper1, String oper2,
            String comment) {
        // generate operands output
        String opers = "";
        if (!oper1.equals("")) {
            opers = oper1;
            if (!oper2.equals("")) {
                opers += ", " + oper2;
            }
        }

        this.appendEntry(location, hexRep, label, name, opers, comment);
    }

    void appendEntry(int location, String hexRep, String label, String name, String opers, String comment) {
        sb.append(String.format("%06X  %-8s   %-6s %-5s %-10s %s", location, hexRep, label, name, opers, comment));
        sb.append("\n");
    }

    public void add(String comment) {
        appendComment(comment);
    }

    void appendComment(String comment) {
        sb.append(" ".repeat(18) + comment);
        sb.append("\n");
    }

    @Override
    public String toString() {
        return sb.toString();
    }
}
