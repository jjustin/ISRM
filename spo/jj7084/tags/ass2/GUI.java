import java.awt.Button;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;

import javax.swing.JFileChooser;

public class GUI extends Frame implements Controller.stepCallback {
    private Label lblRegs; // Declare a Label component
    private Label lblScreen; // Declare a Label component
    private Label lblMemory; // Declare a Label component
    private Button btnStart; // Declare a Button component
    private Button btnReset; // Declare a Button component
    private Button btnStep; // Declare a Button component
    private Button btnLoad; // Declare a Button component
    private Button btnMemNext; // Declare a Button component
    private Button btnMemPrev; // Declare a Button component
    private Controller c;
    private GUI g;
    private int memoryStart = 0;

    class StartEventListener implements ActionListener {
        public void actionPerformed(ActionEvent evt) {
            btnStart.setLabel("Stop");
            for (ActionListener l : btnStart.getActionListeners()) {
                btnStart.removeActionListener(l);
            }
            btnStart.addActionListener(new StopEventListener());
            c.start();
        }
    }

    class StopEventListener implements ActionListener {
        public void actionPerformed(ActionEvent evt) {
            btnStart.setLabel("Start");
            for (ActionListener l : btnStart.getActionListeners()) {
                btnStart.removeActionListener(l);
            }
            btnStart.addActionListener(new StartEventListener());
            c.stop();
        }
    }

    class StepEventListener implements ActionListener {
        public void actionPerformed(ActionEvent evt) {
            c.step();
        }
    }

    class ResetEventListener implements ActionListener {
        public void actionPerformed(ActionEvent evt) {
            c.m.reset();
            update();
        }
    }

    class NextMemoryListener implements ActionListener {
        public void actionPerformed(ActionEvent evt) {
            memoryStart += 256;
            update();
        }
    }

    class PrevMemoryListener implements ActionListener {
        public void actionPerformed(ActionEvent evt) {
            memoryStart -= 256;
            if (memoryStart < 0) {
                memoryStart = 0;
            } else {
                update();
            }
        }
    }

    class LoadObjEventListener implements ActionListener {
        public void actionPerformed(ActionEvent evt) {
            JFileChooser chooser = new JFileChooser(System.getProperty("user.dir"));
            chooser.showOpenDialog(g);
            File file = chooser.getSelectedFile();

            loadFile(file.getAbsolutePath());
        }
    }

    public void loadFile(String file) {
        try {
            Reader reader = new BufferedReader(new FileReader(file));
            c.m.load(reader);
            System.out.println("File read");
            update();
        } catch (Exception ex) {
            System.out.println("Failed to read file: " + ex.getMessage());
        }
    }

    // Constructor to setup GUI components and event handlers
    public GUI() {
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });

        c = new Controller(new Machine(), this);
        setBackground(Color.DARK_GRAY);
        setLayout(new FlowLayout());
        setTitle("SIC/XE Simulator");
        setSize(800, 1000);

        Panel p1 = new Panel();
        lblRegs = new Label(c.m.registers.toString());
        p1.add(lblRegs);
        btnLoad = new Button("Load obj");
        btnLoad.addActionListener(new LoadObjEventListener());
        p1.add(btnLoad);
        btnReset = new Button("Reset");
        btnReset.addActionListener(new ResetEventListener());
        p1.add(btnReset);
        add(p1);

        p1 = new Panel();
        btnStart = new Button("Start");
        btnStart.addActionListener(new StartEventListener());
        p1.add(btnStart);
        btnStep = new Button("Step");
        btnStep.addActionListener(new StepEventListener());
        p1.add(btnStep);
        add(p1);

        lblScreen = new Label(getScreenHtml());
        lblScreen.setSize(0, 0);
        lblScreen.setBackground(Color.gray);
        add(lblScreen);

        p1 = new Panel();
        lblMemory = new Label(getMemoryHtml());
        lblMemory.setSize(0, 0);
        lblMemory.setBackground(Color.gray);
        p1.add(lblMemory);
        btnMemPrev = new Button("<");
        btnMemPrev.addActionListener(new PrevMemoryListener());
        p1.add(btnMemPrev);
        btnMemNext = new Button(">");
        btnMemNext.addActionListener(new NextMemoryListener());
        p1.add(btnMemNext);
        add(p1);

        setVisible(true);
    }

    public void update() {
        lblRegs.setText(c.m.registers.toString());
        lblScreen.setText(getScreenHtml());
        lblMemory.setText(getMemoryHtml());
    }

    public String getScreenHtml() {
        return "<html><pre>" + c.m.memory.toScreen(47104, 25, 80).replace("\n", "<br>") + "</pre></html>";
    }

    public String getMemoryHtml() {
        return "<html><pre>" + c.m.memory.toString(memoryStart, 16, 16).replace("\n", "<br>") + "</pre></html>";
    }

    public static void main(String[] args) {
        GUI g = new GUI();
        if (args.length >= 1) {
            g.loadFile(args[0]);
        }
    }

}
