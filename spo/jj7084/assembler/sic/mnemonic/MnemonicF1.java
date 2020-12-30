package sic.mnemonic;

import sic.node.InstructionF1;
import sic.node.Node;
import sic.parsing.Parser;
import sic.parsing.SyntaxError;

public class MnemonicF1 extends Mnemonic {
    public MnemonicF1(String mnemonic, int opcode, String hint, String desc) {
        super(mnemonic, opcode, hint, desc);
    }

    @Override
    public Node parse(Parser parser) throws SyntaxError {
        return new InstructionF1(this);
    }

}
