package sic.mnemonic;

import sic.node.InstructionF3;
import sic.node.Node;
import sic.parsing.Parser;
import sic.parsing.SyntaxError;

public class MnemonicF3 extends Mnemonic {
    public MnemonicF3(String mnemonic, int opcode, String hint, String desc) {
        super(mnemonic, opcode, hint, desc);
    }

    @Override
    public Node parse(Parser parser) throws SyntaxError {
        return new InstructionF3(this);
    }
}
