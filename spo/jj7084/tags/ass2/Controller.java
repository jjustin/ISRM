import java.util.Timer;
import java.util.TimerTask;

public class Controller {
    public interface stepCallback {
        void update();
    }

    stepCallback cbClass;
    Machine m;
    Timer t;
    long frequency = 1000000; // in Hz

    Controller(Machine machine, stepCallback cbClass) {
        m = machine;
        this.cbClass = cbClass;
    }

    void start() {
        if (t == null) {
            t = new Timer();
            schedule();
        }
    }

    void schedule() {
        if (t != null) {
            t.schedule(new TimerTask() {
                public void run() {
                    if (frequency <= 1000) { // 1 kHz
                        step();
                    } else {
                        for (int i = 0; i < frequency / 1000; i++) {
                            step();
                        }
                    }

                    if (!m.stopped) {
                        schedule();
                    } else {
                        stop();
                    }
                }
            }, (frequency > 1000) ? 0 : 1000 / frequency);
        }
    }

    void step() {
        m.execute();
        updateGui();
    }

    void updateGui() {
        try {
            if (cbClass != null) {
                cbClass.update();
            }
        } catch (Exception ex) {
            System.out.print(ex.getMessage());
        }
    }

    void stop() {
        if (t != null) {
            t.cancel();
            t = null;
        }
    }

    // speed as ms/a
    long getSpeed() {
        return frequency;
    }

    void setSpeed(long frequency) {
        this.frequency = frequency;
    }
}
