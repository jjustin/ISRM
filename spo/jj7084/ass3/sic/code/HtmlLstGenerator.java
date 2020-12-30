package sic.code;

public class HtmlLstGenerator extends LstGenerator {
    HtmlGenerator parent;

    public HtmlLstGenerator(HtmlGenerator parent) {
        super();
        this.parent = parent;
    }

    @Override
    void appendEntry(int location, String hexRep, String label, String name, String opers, String comment) {
        sb.append(String.format(
                "%06X: <div id='%s' style='color:%s;display:inline'>%-8s</div>   %-6s %-5s %-10s <div style='color:lightgray;display:inline'>%s</div>",
                location, parent.commandNumber(), parent.getcolor(), hexRep, label, name, opers, comment));
        sb.append("\n");
    }

    @Override
    void appendComment(String comment) {
        sb.append("<div style='color:lightgray;display:inline'>");
        sb.append(" ".repeat(18) + comment);
        sb.append("</div>");
        sb.append("\n");
    }
}
