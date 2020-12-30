package sic.mnemonic;

import sic.common.Oper;
import sic.node.InstructionF4;
import sic.node.Node;
import sic.parsing.Parser;
import sic.parsing.SyntaxError;

public class MnemonicF4m extends Mnemonic {
    public MnemonicF4m(String mnemonic, int opcode, String hint, String desc) {
        super(mnemonic, opcode, hint, desc);
    }

    @Override
    public Node parse(Parser parser) throws SyntaxError {
        Oper o = parseLabelOrNumber(parser);
        if (parser.lexer.advanceIf(',')) {
            parser.lexer.skipWhitespace();
            if (parser.lexer.advanceIf('X')) {
                return new InstructionF4(this, o, true);
            } else {
                throw new SyntaxError("Expected X", parser.lexer.row, parser.lexer.col);
            }
        }
        return new InstructionF4(this, o, false);
    }
}
