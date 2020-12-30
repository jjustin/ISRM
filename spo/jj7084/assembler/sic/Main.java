package sic;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;

import sic.code.Code;
import sic.parsing.Parser;

public class Main {
    public static void main(String[] args) throws Exception {
        try {
            Path path = Path.of(args[0]);
            String actual = Files.readString(path);
            Code c = (new Parser()).parse(actual);
            c.resolve();

            String lstStr = c.emitLst();
            String objStr = c.emitObj();
            String htmlStr = c.emitHtml();
            toFile("out.lst", lstStr);
            toFile("out.obj", objStr);
            toFile("out.html", htmlStr);
        } catch (Exception ex) {
            throw ex;
        }
    }

    public static void toFile(String filename, String content) {
        FileWriter fw = null;
        try {
            File f = new File(filename);
            if (!f.exists()) {
                f.createNewFile();
            }
            fw = new FileWriter(f);
            fw.write(content);
        } catch (Exception ex) {
            System.out.println("Failed to create new file: " + ex.getMessage());
        } finally {
            try {
                if (fw != null) {
                    fw.close();
                }
            } catch (Exception ex) {
                System.out.println("Failed to close new file: " + ex.getMessage());
            }
        }
    }
}
