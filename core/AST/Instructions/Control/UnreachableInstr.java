package core.AST.Instructions.Control;

import core.Parser;
import core.util.ParseException;
import core.util.Result.Result;

public class UnreachableInstr implements ControlInstr {
    public static Result<ControlInstr, ParseException> parse(Parser parser) {
        return parser
            .nextByte((byte) 0x00)
            .map(ignored -> new UnreachableInstr());
    }

    public String toString() {
        return "UnreachableInstr";
    }
}
