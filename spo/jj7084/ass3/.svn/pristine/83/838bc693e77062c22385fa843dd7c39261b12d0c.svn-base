package sic.mnemonic;

import sic.node.InstructionF2;
import sic.node.Node;
import sic.parsing.Parser;
import sic.parsing.SyntaxError;

public class MnemonicF2r extends Mnemonic {
    public MnemonicF2r(String mnemonic, int opcode, String hint, String desc) {
        super(mnemonic, opcode, hint, desc);
    }

    @Override
    public Node parse(Parser parser) throws SyntaxError {
        return new InstructionF2(this, parseRegister(parser));
    }
}
