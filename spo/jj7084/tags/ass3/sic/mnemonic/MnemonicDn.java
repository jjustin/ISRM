package sic.mnemonic;

import sic.node.Directive;
import sic.node.Node;
import sic.parsing.Parser;
import sic.parsing.SyntaxError;

/**
 * Directive with one numeric operand. Podporni razred za predmet Sistemska
 * programska oprema.
 * 
 * @author jure
 */
public class MnemonicDn extends Mnemonic {

	public MnemonicDn(String mnemonic, int opcode, String hint, String desc) {
		super(mnemonic, opcode, hint, desc);
	}

	@Override
	public Node parse(Parser parser) throws SyntaxError {
		return new Directive(this, this.parseLabelOrNumber(parser));
	}
}
