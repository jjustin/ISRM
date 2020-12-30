package sic.code;

public class HtmlObjGenerator extends ObjectGenerator {
    HtmlGenerator parent;

    public HtmlObjGenerator(HtmlGenerator parent, String name, int start, int programLen) {
        super(name, start, programLen);
        this.parent = parent;
    }

    @Override
    protected void appendStart(String name, int start, int programLen) {
        sb.append(String.format(
                "H%-6s<div style='color:green;display:inline'>%06X</div><div style='color:brown;display:inline'>%06X</div>\n",
                name, start, programLen));
    }

    @Override
    public void append(String s) {
        String htmlS = String.format("<a href='#%s' style='color:%s;display:inline;text-decoration: none;'>%s</a>",
                parent.nextCommand(), parent.getcolor(), s);
        super.append(htmlS, s.length());
        parent.switchColor();
    }

    @Override
    protected void appendT() {
        sb.append(String.format("T%06X<b>%02X</b>%s\n", offset, currentBlockLen, currentBlock.toString()));
    }

    @Override
    protected void appendM(int addr, int len) {
        sb.append(String.format("M%06X<b>%02X</b>\n", addr, len));
    }
}
