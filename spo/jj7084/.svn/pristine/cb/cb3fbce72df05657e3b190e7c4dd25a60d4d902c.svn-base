package sic.mnemonic;

import sic.common.Oper;
import sic.node.InstructionF3;
import sic.node.Node;
import sic.parsing.Parser;
import sic.parsing.SyntaxError;

public class MnemonicF3m extends Mnemonic {
    public MnemonicF3m(String mnemonic, int opcode, String hint, String desc) {
        super(mnemonic, opcode, hint, desc);
    }

    @Override
    public Node parse(Parser parser) throws SyntaxError {
        Oper o = parseLabelOrNumber(parser);
        if (parser.lexer.advanceIf(',')) {
            parser.lexer.skipWhitespace();
            if (parser.lexer.advanceIf('X')) {
                return new InstructionF3(this, o, true);
            } else {
                throw new SyntaxError("Expected X", parser.lexer.row, parser.lexer.col);
            }
        }
        return new InstructionF3(this, o, false);
    }
}
