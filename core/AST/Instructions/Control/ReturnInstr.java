package core.AST.Instructions.Control;

import core.Parser;
import core.util.ParseException;
import core.util.Result.Result;

public class ReturnInstr implements ControlInstr {
    public static Result<ControlInstr, ParseException> parse(Parser parser) {
        return parser
            .nextByte((byte) 0x0F)
            .map(ignored -> new ReturnInstr());
    }

    public String toString() {
        return "ReturnInstr";
    }
}
