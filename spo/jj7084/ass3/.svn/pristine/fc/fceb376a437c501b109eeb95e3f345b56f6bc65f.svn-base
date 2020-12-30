package sic.mnemonic;

import sic.code.Code;
import sic.common.Oper;
import sic.common.RegisterMapper;
import sic.node.Node;
import sic.parsing.Parser;
import sic.parsing.SyntaxError;

/**
 * Podporni razred za predmet Sistemska programska oprema.
 * 
 * @author jure
 */
public abstract class Mnemonic {
	public String name;
	public int opcode;
	public String hint;
	public String desc;

	public Mnemonic(String name, int opcode, String hint, String desc) {
		this.name = name;
		this.opcode = opcode;
		this.hint = hint;
		this.desc = desc;
	}

	public abstract Node parse(Parser parser) throws SyntaxError;

	public Oper parseLabelOrNumber(Parser parser) throws SyntaxError {
		if (Character.isDigit(parser.lexer.peek()))
			return new Oper(parser.parseNumber(0, Code.MAX_ADDR));
		// symbol
		else if (Character.isLetter(parser.lexer.peek()))
			return new Oper(parser.parseSymbol());
		switch (parser.lexer.peek()) {
			case '#':
				parser.lexer.advance();
				if (Character.isDigit(parser.lexer.peek()))
					return new Oper(parser.parseNumber(0, Code.MAX_ADDR), "#");
				else if (Character.isLetter(parser.lexer.peek()))
					return new Oper(parser.parseSymbol(), "#");
				break;
			case '@':
				parser.lexer.advance();
				if (Character.isDigit(parser.lexer.peek()))
					return new Oper(parser.parseNumber(0, Code.MAX_ADDR), "@");
				else if (Character.isLetter(parser.lexer.peek()))
					return new Oper(parser.parseSymbol(), "@");
				break;
			case '*':
				parser.lexer.advance();
				return new Oper(0, "*");
			case '=':
				parser.lexer.advance();
				return new Oper(parser.parseNumber(0, Code.MAX_ADDR), "=");
			case '-':
				return new Oper(parser.parseNumber(0, Code.MAX_ADDR), "");
		}
		// otherwise: error
		throw new SyntaxError(String.format("Invalid character '%c", parser.lexer.peek()), parser.lexer.row,
				parser.lexer.col);

	}

	public Oper parseRegister(Parser parser) throws SyntaxError {
		return new Oper(RegisterMapper.getRegister(parser.parseRegister()));
	}

	@Override
	public String toString() {
		return String.format("%-6s", name);
	}

	public String operandToString(Node instruction) {
		return "";
	}
}
