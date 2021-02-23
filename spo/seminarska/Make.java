import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * Make
 */
public class Make {
    // contains set of all targets specified in Makefile
    ArrayList<Node> targets = new ArrayList<>();
    // contains list of already built targets
    HashSet<String> doneTargets = new HashSet<>();
    // list of phony targets
    HashSet<String> phonys = new HashSet<>();

    public static void main(String[] args) {
        Make m = new Make();
        m.readFile();
        try {
            if (args.length > 0) {
                // if targets were specified
                for (String arg : args) {
                    Logger.logUpdate(m.execute(arg));
                }
            } else {
                Logger.logUpdate(m.execute());
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    // read Makefile and generates a list of nodes for each target in makefile
    public void readFile() {
        Reader objReader = null;
        try {
            objReader = new Reader();
            String line = objReader.readLine();

            while (line != null) {
                line = line.trim();
                // skip comments and empty files
                if (line.startsWith("#") || line.equals("")) {
                    line = objReader.readLine();
                    continue;
                }

                // verify rule format is correct
                String[] l = (line + " ").split(":");
                if (l.length != 2) {
                    System.out.printf("Syntax error \":\", line %d\n", objReader.line);
                    return;
                }

                // check if target specifies phonys
                if (l[0].toUpperCase().equals(".PHONY")) {
                    for (String phonyTarget : l[1].split(" ")) {
                        if (!phonyTarget.equals("")) {
                            phonys.add(phonyTarget);
                        }
                    }
                    line = objReader.readLine();
                    continue;
                }

                // create new node with current rule
                Node n = new Node(l[0], l[1].split(" "), this);

                // read rule's recipe
                line = objReader.readLine();
                while (line != null && line.startsWith("\t")) {
                    n.addCommand(line.substring(1));
                    line = objReader.readLine();
                }
                targets.add(n);
            }
        } catch (FileNotFoundException ex) {
            System.err.println("File not found");
            return;
        } catch (IOException ex) {
            System.err.println("IOex " + ex.getMessage());
            return;
        } finally {
            try {
                objReader.close();
            } catch (IOException ex) {
                System.err.println("IOex " + ex.getMessage());
                return;
            }
        }
    }

    // called directly from node when fulfilling the prereqs
    boolean execute(String target, String prevTarget) throws Exception {
        Node n = null;
        // find node that matches
        for (Node n2 : targets) {
            String t = n2.targetOriginal;
            boolean isCorrect = t.equals(target);
            String placeholder = target;
            if (t.contains("%")) {
                // check if wildcard option matches
                String[] fixed = t.split("%");
                if (fixed.length == 2 && target.startsWith(fixed[0]) && target.endsWith(fixed[1])) {
                    isCorrect = true;
                    int i1 = fixed[0].length();
                    int i2 = target.length() - fixed[1].length();
                    placeholder = target.substring(i1, i2);
                }
            }

            // if node was found update data on node
            if (isCorrect) {
                n = n2.duplicate();
                n.targetCurrent = target;
                n.placeholder = placeholder;
                break;
            }
        }

        if (n == null) {
            // if execute was called from node
            if (prevTarget != null) {
                File pf = new File(prevTarget);
                File cf = new File(target);
                if (!cf.exists()) {
                    throw new Exception(String.format("No rule to make target %s", target));
                }
                // current target was updated after parent file was generated
                if (pf.exists() && pf.lastModified() >= cf.lastModified()) {
                    Logger.log("[make] File %s was not updated\n", cf);
                    return false;
                }
                Logger.log("[make] File %s was updated\n", cf);
                return true;
            }

            throw new Exception("No target found for " + target);
        }

        boolean wasUpdated = n.execute();
        n.placeholder = "";
        n.targetCurrent = "";
        return wasUpdated;
    }

    // if there is no previous target
    boolean execute(String target) throws Exception {
        return execute(target, null);
    }

    // if no target was specfied
    boolean execute() throws Exception {
        return this.execute(targets.get(0).targetOriginal);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Node n : targets) {
            sb.append(n.toString());
            sb.append("\n\n");
        }
        return sb.toString();
    }
}

class Node {
    Make make;
    String targetOriginal;
    String targetCurrent;
    String placeholder = "";
    String preReqStr;
    String[] preReq;
    ArrayList<String> commands = new ArrayList<>();

    // creates new node
    Node(String target, String[] preReq, Make make) {
        this.make = make;
        this.preReq = preReq;
        preReqStr = String.join(" ", preReq);
        this.targetOriginal = target;
    }

    // adds command to be executed into node
    void addCommand(String command) {
        this.commands.add(command);
    }

    // checks if all prerequisites are fulfilled and then executes the spceified
    // commands
    boolean execute() throws Exception {
        Logger.log("[make] Executing target %s\n", targetCurrent);
        if (make.doneTargets.contains(targetCurrent)) {
            Logger.log("[make] %s has already been built\n", targetCurrent);
            return true;
        }

        File cf = new File(targetCurrent);
        boolean wasUpdated = preReq.length == 0 || isPhony() || (!isPhony() && !cf.exists());
        for (String p : preReq) {
            p = p.trim();
            if (p.equals("")) {
                continue;
            }
            p = p.replaceAll("%", placeholder);
            Logger.log("[make] Prerequisite %s\n", p);
            Logger.spaceAdd();
            wasUpdated = make.execute(p, targetCurrent) || wasUpdated;
            Logger.spaceRem();
            Logger.log("[make] Prerequisite %s done\n", p);
        }
        if (wasUpdated) {
            for (String command : commands) {
                command = command.trim();
                // check if errors can be ignored
                boolean ignoreErrors = false;
                if (command.startsWith("-")) {
                    ignoreErrors = true;
                    command = command.substring(1);
                }
                // fix macros
                command = command.replace("$@", targetCurrent);
                command = command.replace("$^", preReqStr.replace("%", placeholder));
                if (command.contains("$<")) {
                    if (preReq.length == 0) {
                        command = command.replace("$<", "");
                    } else {
                        command = command.replace("$<", preReq[1]);
                    }
                }
                Logger.println(command);
                String line;
                // execute the command
                Process p = Runtime.getRuntime().exec(new String[] { "bash", "-c", command });
                BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
                // pipe the output of current runtime to stdout
                while ((line = input.readLine()) != null) {
                    Logger.println(line);
                }
                // check exit code of called process
                int response = p.waitFor();
                if (!ignoreErrors && response != 0) {
                    throw new Exception(String.format("%s failed with code %d", command, response));
                }
            }
        }
        make.doneTargets.add(targetCurrent);
        return wasUpdated;
    }

    Node duplicate() {
        Node newn = new Node(targetOriginal, preReq, make);
        newn.commands = commands;
        return newn;
    }

    // checks if current target is phony
    boolean isPhony() {
        return make.phonys.contains(targetOriginal);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%s: %s\n\t", targetOriginal, preReqStr));
        sb.append(String.join("\n\t", commands.toArray(new String[commands.size()])));
        return sb.toString();
    }

}

// used for reading the make file line by line
class Reader {
    int line;
    BufferedReader bf;

    Reader() throws FileNotFoundException {
        bf = new BufferedReader(new FileReader("Makefile"));
        line = 0;
    }

    String readLine() throws IOException {
        line++;
        return bf.readLine();
    }

    void close() throws IOException {
        bf.close();
    }
}

// System.out wrapper
class Logger {
    public static boolean log = true;
    public static int spaces = 0;

    public static void log(String format, Object... args) {
        if (log) {
            System.out.printf(" ".repeat(spaces) + format, args);
        }
    }

    public static void print(String format, Object... args) {
        System.out.printf(" ".repeat(spaces) + format, args);
    }

    public static void println(Object str) {
        System.out.println(" ".repeat(spaces) + str);
    }

    public static void spaceAdd() {
        if (log)
            spaces++;
    }

    public static void spaceRem() {
        if (log)
            spaces--;
    }

    public static void logUpdate(boolean wasUpdated) {
        if (!wasUpdated) {
            System.out.println("Target is up to date\n");
        } else {
            System.out.println("Target updated successfully\n");
        }
    }
}