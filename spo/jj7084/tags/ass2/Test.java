public class Test {
    public static void main(String[] args) {
        Machine m = new Machine();

        // m.memory.setByte(0, 33);
        // System.out.println(m.memory.getByte(0));
        // m.memory.setByte(0, -33);
        // System.out.println(m.memory.getByte(0));
        // m.memory.setFloat(0, 300.1);
        // double x = m.memory.getFloat(0);
        // m.memory.setFloat(0, 400.5);
        // double y = m.memory.getFloat(0);
        // System.out.println(x + y);
        // System.out.println(Long.toBinaryString(Double.doubleToLongBits(400.5 +
        // 300.1)));
        // System.out.println(Long.toBinaryString(Double.doubleToLongBits(x + y)));

        // m.getDevice(1).write((byte) 0x40);

        // m.registers.setA(33);
        // m.registers.setS(44);
        // m.execF2(Opcode.ADDR, 0b00000100);
        // System.out.println(m.registers.getA());
        // System.out.println(m.registers.getS());

        // m.registers.setA(33);
        // m.registers.setS(22);
        // m.execF2(Opcode.COMPR, 0b00000100);
        // System.out.println(m.registers.getSW());

        // m.registers.setA(-32);
        // m.execF2(Opcode.SHIFTR, 0b00000001);
        // System.out.println(Integer.toBinaryString(-32));
        // System.out.println(Integer.toBinaryString(-16));
        // System.out.println(Integer.toBinaryString((m.registers.getA())));

        // int opcode = 0b00000010;
        // int operand = 0b11110000;
        // int nixbpe = ((opcode & 0b11) << 4) + ((operand & 0b11110000) >> 4);
        // System.out.println(Integer.toBinaryString(nixbpe));

        // m.memory.setWord(0, 0x01002A);
        // m.memory.setWord(3, 0x1B2006);
        // m.memory.setWord(6, 0x0F2003);
        // m.memory.setWord(9, 0x3F2FFD);
        // m.memory.setWord(12, 0x000016);
        // // m.execute();
        // // m.execute();
        // // m.execute();
        // // System.out.println(m.registers.getA());

        // Controller c = new Controller(m, null);
        // c.step();
        // c.step();
        // c.step();
        // c.stop();

    }
}
