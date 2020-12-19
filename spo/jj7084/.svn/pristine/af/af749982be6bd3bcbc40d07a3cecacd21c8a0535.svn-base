import java.io.InputStream;

public class InputDevice extends Device {
    InputStream inStr;

    InputDevice(InputStream in) {
        super();
        inStr = in;
    }

    @Override
    byte read() {
        try {
            return (byte) (inStr.read() & 0xFF);
        } catch (Exception ex) {
            System.out.println("Failed to read from device: " + ex.getMessage());
            return (byte) 0xFF;
        }
    }
}
