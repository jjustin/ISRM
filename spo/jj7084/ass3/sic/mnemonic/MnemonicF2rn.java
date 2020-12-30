package sic.mnemonic;

import sic.common.Oper;
import sic.node.InstructionF2;
import sic.node.Node;
import sic.parsing.Parser;
import sic.parsing.SyntaxError;

public class MnemonicF2rn extends Mnemonic {
    public MnemonicF2rn(String mnemonic, int opcode, String hint, String desc) {
        super(mnemonic, opcode, hint, desc);
    }

    @Override
    public Node parse(Parser parser) throws SyntaxError {
        Oper r = parseRegister(parser);
        parser.parseComma();
        Oper n = parseRegister(parser);
        return new InstructionF2(this, r, n);
    }
}
