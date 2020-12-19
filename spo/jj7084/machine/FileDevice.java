import java.io.RandomAccessFile;

public class FileDevice extends Device {
    RandomAccessFile file;
    int fileNumber;

    FileDevice(int num) {
        super();
        fileNumber = num;
        try {
            file = new RandomAccessFile(String.format("%02X.dev", num), "rws");
        } catch (Exception ex) {
            System.out.printf("Failed to init FileDevice %02X: %s\n", num, ex.getMessage());
        }
    }

    @Override
    void write(byte val) {
        try {
            file.write(val);
        } catch (Exception ex) {
            System.out.printf("Failed to write FileDevice %02X: %s\n", fileNumber, ex.getMessage());
        }
    }

    @Override
    byte read() {
        try {
            return (byte) (file.read() & 0xFF);
        } catch (Exception ex) {
            System.out.printf("Failed to read FileDevice %02X: %s\n", fileNumber, ex.getMessage());
            return (byte) 0xFF;
        }
    }
}
