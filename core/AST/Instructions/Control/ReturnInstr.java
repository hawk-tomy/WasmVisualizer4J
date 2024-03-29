package core.AST.Instructions.Control;

import core.Parser;
import core.util.ParseException;
import core.util.Result.Ok;
import core.util.Result.Result;

public class ReturnInstr implements ControlInstr {
    public static Result<ControlInstr, ParseException> parse(Parser ignoredParser) {
        return new Ok<>(new ReturnInstr());
    }

    public String toString() {
        return "ReturnInstr";
    }

    public String content() {
        return "return";
    }
}
