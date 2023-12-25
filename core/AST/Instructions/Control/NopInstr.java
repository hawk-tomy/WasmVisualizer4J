package core.AST.Instructions.Control;

import core.Parser;
import core.util.ParseException;
import core.util.Result.Result;

public class NopInstr implements ControlInstr {
    public static Result<ControlInstr, ParseException> parse(Parser parser) {
        return parser
            .nextByte((byte) 0x01)
            .map(ignored -> new NopInstr());
    }

    public String toString() {
        return "NopInstr";
    }
}
