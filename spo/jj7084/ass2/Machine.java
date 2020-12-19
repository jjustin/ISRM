import java.io.Reader;

/**
 * Machine
 */
public class Machine {
    Registers registers = new Registers();
    Memory memory = new Memory();
    Device[] devices = new Device[256];
    boolean stopped = false;

    Machine() {
        devices[0] = new InputDevice(System.in);
        devices[1] = new OutputDevice(System.out);
        devices[2] = new OutputDevice(System.err);
    }

    void reset() {
        registers = new Registers();
        memory = new Memory();
        devices = new Device[256];
        stopped = false;
    }

    // r should be reader of object file
    void load(Reader r) {
        if (!"H".equals(Utils.readString(r, 1))) {
            errNotObjFile();
            return;
        }
        Utils.readString(r, 18);

        while (!"\nE".equals(Utils.readString(r, 2))) {
            int codeAddress = Utils.readWord(r);
            int codeLen = Utils.readByte(r);

            for (int i = 0; i < codeLen; i++) {
                memory.setByte(codeAddress + i, Utils.readByte(r));
            }
        }
        registers.setPC(Utils.readWord(r));
        stopped = false;
    }

    int fetch() {
        int pc = registers.getPC();
        registers.setPC(pc + 1);
        return memory.getByte(pc);
    }

    // DEVICES

    Device getDevice(int n) {
        if (0 <= n && n < 256) {
            if (devices[n] == null) {
                devices[n] = new FileDevice(n);
            }
            return devices[n];
        } else {
            System.out.printf("Device %d is out of bounds, returning first device", n);
            return devices[0];
        }
    }

    void setDevice(int n, Device device) {
        if (n < 256) {
            devices[n] = device;
        } else {
            System.out.printf("Device %d is out of bounds, nothing was set", n);
        }
    }

    // EXECUTES
    void execute() {
        int opcode = fetch();
        if (execF1(opcode)) {
            return;
        }

        int operand = fetch();
        if (execF2(opcode, operand)) {
            return;
        }

        int disp = fetch();
        int nixbpe = ((opcode & 0b11) << 4) + ((operand & 0b11110000) >> 4);
        opcode = opcode & 0b11111100;
        operand = ((operand & 0b00001111) << 8) + disp;
        if ((nixbpe & 0b1) == 1) {
            operand = (operand << 8) + fetch();
        }

        if (execSICF3F4(opcode, nixbpe, operand)) {
            return;
        }
        System.out.printf(
                "Execute did not execute - could no find operation to execute. code: 0x%02X nixbpe: 0x%06X operand: 0x%04X",
                opcode, nixbpe, operand);
    }

    boolean execF1(int opcode) {
        switch (opcode) {
            case Opcode.FIX:
                errNIY("FIX");
                return true;
            case Opcode.FLOAT:
                errNIY("FLOAT");
                return true;
            case Opcode.HIO:
                errNIY("HIO");
                return true;
            case Opcode.NORM:
                errNIY("NORM");
                return true;
            case Opcode.SIO:
                errNIY("SIO");
                return true;
            case Opcode.TIO:
                errNIY("TIO");
                return true;
        }
        return false;
    }

    boolean execF2(int opcode, int operand) {
        // [opcode(8), r1(4), r2(4)]
        int r1 = (operand & 0xF0) >> 4;
        int r2 = (operand & 0x0F);
        switch (opcode) {
            case Opcode.ADDR:
                registers.setI(r2, registers.getI(r2) + registers.getI(r1));
                return true;
            case Opcode.CLEAR:
                registers.setI(r1, 0);
                return true;
            case Opcode.COMPR:
                registers.setSWcompare(registers.getI(r1), registers.getI(r2));
                return true;
            case Opcode.DIVR:
                registers.setI(r2, registers.getI(r2) / registers.getI(r1));
                return true;
            case Opcode.MULR:
                registers.setI(r2, registers.getI(r2) * registers.getI(r1));
                return true;
            case Opcode.RMO:
                registers.setI(r2, registers.getI(r1));
                return true;
            case Opcode.SHIFTL:
                registers.setI(r1, registers.getI(r1) << r2);
                return true;
            case Opcode.SHIFTR:
                registers.setI(r1, ((registers.getI(r1) >> r2)));
                return true;
            case Opcode.SUBR:
                registers.setI(r2, registers.getI(r2) - registers.getI(r1));
                return true;
            case Opcode.SVC:
                errNIY("SVC");
                return true;
            case Opcode.TIXR:
                registers.setX(registers.getX() + 1);
                registers.setSWcompare(registers.getX(), registers.getI(r1));
                return true;
        }
        return false;
    }

    boolean execSICF3F4(int opcode, int nixbpe, int operand) {
        switch (opcode) {
            case Opcode.ADD:
                registers.setA(registers.getA() + f34Read(nixbpe, operand));
                return true;
            case Opcode.ADDF:
                errNIY("ADDF");
                return true;
            case Opcode.AND:
                registers.setA(registers.getA() & f34Read(nixbpe, operand));
                return true;
            case Opcode.COMP:
                registers.setSWcompare(registers.getA(), f34Read(nixbpe, operand));
                return true;
            case Opcode.COMPF:
                errNIY("COMPF");
                return true;
            case Opcode.DIV:
                registers.setA(registers.getA() / f34Read(nixbpe, operand));
                return true;
            case Opcode.DIVF:
                errNIY("DIVF");
                return true;
            case Opcode.J:
                int oldAddr = registers.getPC();
                int addr = f34GetAddress(nixbpe, operand);
                if (oldAddr - addr == 3) { // halt J halt check
                    stopped = true;
                }
                registers.setPC(addr);
                return true;
            case Opcode.JEQ:
                if (registers.getSW() == registers.SWeq)
                    registers.setPC(f34GetAddress(nixbpe, operand));
                return true;
            case Opcode.JGT:
                if (registers.getSW() == registers.SWgt)
                    registers.setPC(f34GetAddress(nixbpe, operand));
                return true;
            case Opcode.JLT:
                if (registers.getSW() == registers.SWlt)
                    registers.setPC(f34GetAddress(nixbpe, operand));
                return true;
            case Opcode.JSUB:
                registers.setL(registers.getPC());
                registers.setPC(f34GetAddress(nixbpe, operand));
                return true;
            case Opcode.LDA:
                registers.setA(f34Read(nixbpe, operand));
                return true;
            case Opcode.LDB:
                registers.setB(f34Read(nixbpe, operand));
                return true;
            case Opcode.LDCH:
                registers.setA((registers.getA() & 0xFFFF00) + (f34Read(nixbpe, operand) >> 16));
                return true;
            case Opcode.LDF:
                errNIY("LDF");
                return true;
            case Opcode.LDL:
                registers.setL(f34Read(nixbpe, operand));
                return true;
            case Opcode.LDS:
                registers.setS(f34Read(nixbpe, operand));
                return true;
            case Opcode.LDT:
                registers.setT(f34Read(nixbpe, operand));
                return true;
            case Opcode.LDX:
                registers.setX(f34Read(nixbpe, operand));
                return true;
            case Opcode.MUL:
                registers.setA(registers.getA() * f34Read(nixbpe, operand));
                return true;
            case Opcode.MULF:
                errNIY("MULF");
                return true;
            case Opcode.OR:
                registers.setA(registers.getA() | f34Read(nixbpe, operand));
                return true;
            case Opcode.RD:
                int b = getDevice(f34Read(nixbpe, operand) >> 16).read();
                registers.setA(((registers.getA() & 0xFFFF00) + b));
                return true;
            case Opcode.RSUB:
                registers.setPC(registers.getL());
                return true;
            case Opcode.SSK:
                errNIY("SSK");
                return true;
            case Opcode.STA:
                memory.setWord(f34GetAddress(nixbpe, operand), registers.getA());
                return true;
            case Opcode.STB:
                memory.setWord(f34GetAddress(nixbpe, operand), registers.getB());
                return true;
            case Opcode.STCH:
                memory.setByte(f34GetAddress(nixbpe, operand), registers.getA() & 0x0000FF);
                return true;
            case Opcode.STF:
                errNIY("STF");
                return true;
            case Opcode.STI:
                errNIY("STI");
                return true;
            case Opcode.STL:
                memory.setWord(f34GetAddress(nixbpe, operand), registers.getL());
                return true;
            case Opcode.STS:
                memory.setWord(f34GetAddress(nixbpe, operand), registers.getS());
                return true;
            case Opcode.STSW:
                memory.setWord(f34GetAddress(nixbpe, operand), registers.getSW());
                return true;
            case Opcode.STT:
                memory.setWord(f34GetAddress(nixbpe, operand), registers.getT());
                return true;
            case Opcode.STX:
                memory.setWord(f34GetAddress(nixbpe, operand), registers.getX());
                return true;
            case Opcode.SUB:
                registers.setA(registers.getA() - f34Read(nixbpe, operand));
                return true;
            case Opcode.SUBF:
                errNIY("SUBF");
                return true;
            case Opcode.TD:
                errNIY("TD");
                return true;
            case Opcode.TIX:
                registers.setX(registers.getX() + 1);
                registers.setSWcompare(registers.getX(), f34Read(nixbpe, operand));
                return true;
            case Opcode.WD:
                byte write = (byte) (registers.getA() & 0x0000FF);
                getDevice(f34Read(nixbpe, operand) >> 16).write(write);
                return true;
        }
        return false;
    }

    int f34Read(int nixbpe, int op) {
        // https://www.gopalancolleges.com/gcem/course-material/computer-science/course-plan/sem-V/system-software-10CS52.pdf
        // page 10
        int n = (nixbpe & 0b100000) >> 5;
        int i = (nixbpe & 0b10000) >> 4;
        int x = (nixbpe & 0b1000) >> 3;
        int b = (nixbpe & 0b100) >> 2;
        int p = (nixbpe & 0b10) >> 1;
        // indexed
        if (x == 1) {
            op = registers.getX() + op;
        }
        // base relative
        if (b == 1) {
            op = (registers.getB() + op);
        }
        // pc relative
        else if (p == 1) {
            if (op > 2047) {
                op = op - 4096;
            }
            op = (registers.getPC() + op);
        }
        // immediate
        if (i == 1 && n == 0) {
            return op;
        }
        op = memory.getWord(op);
        if (i == 0 && n == 1) {
            op = memory.getWord(op);
        }
        return op;
    }

    int f34GetAddress(int nixbpe, int op) {
        int n = (nixbpe & 0b100000) >> 5;
        int i = (nixbpe & 0b10000) >> 4;
        int x = (nixbpe & 0b1000) >> 3;
        int b = (nixbpe & 0b100) >> 2;
        int p = (nixbpe & 0b10) >> 1;

        // indexed
        if (x == 1) {
            op = registers.getX() + op;
        }
        // base relative
        if (b == 1) {
            op = (registers.getB() + op);
        }
        // pc relative
        else if (p == 1) {
            if (op > 2047) {
                op = op - 4096;
            }
            op = (registers.getPC() + op);
        }
        if (i == 0 && n == 1) {
            op = memory.getWord(op);
        }
        return op;
    }

    // ERRORS
    void errNIY(String method) {
        System.out.printf("NIY: %s\n", method);
    }

    void errUnknownF34Address(int flags) {
        System.out.printf("Unknown addressing: %d\n", flags);
    }

    void errNotObjFile() {
        System.out.println("Provided file is not of correct format");
    }
}
