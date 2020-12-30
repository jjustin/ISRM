package sic.code;

public class HtmlGenerator {
    public LstGenerator lstGenerator;
    public ObjectGenerator objGenerator;
    String name;
    int color;
    int commandCounter;

    public HtmlGenerator(String name, int start, int programLen) {
        this.name = name;
        this.color = 0;
        this.commandCounter = 0;
        objGenerator = new HtmlObjGenerator(this, name, start, programLen);
        lstGenerator = new HtmlLstGenerator(this);
    }

    public String getcolor() {
        switch (color) {
            case 0:
                return "blue";
            case 1:
                return "red";
        }
        throw new RuntimeException("Unknown color code: " + color);
    }

    public void switchColor() {
        color = (color + 1) % 2;
    }

    public String nextCommand() {
        commandCounter++;
        return String.format("%s", commandCounter - 1);
    }

    public String commandNumber() {
        return String.format("%s", commandCounter);
    }

    @Override
    public String toString() {
        return String.format("<html><header><title>%s</title></header><body><pre>%s<br>%s<pre></body></html>", name,
                lstGenerator.toString(), objGenerator.toString());
    }
}
