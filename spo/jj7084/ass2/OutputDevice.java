import java.io.OutputStream;

public class OutputDevice extends Device {
    OutputStream outStr;

    OutputDevice(OutputStream out) {
        super();
        outStr = out;
    }

    @Override
    void write(byte val) {
        try {
            outStr.write(val);
            outStr.flush();
        } catch (Exception ex) {
            System.out.println("Failed to write to output device: " + ex.getMessage());
        }
    }
}
