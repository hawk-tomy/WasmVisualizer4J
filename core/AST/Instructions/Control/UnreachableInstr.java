package core.AST.Instructions.Control;

import core.Parser;
import core.util.ParseException;
import core.util.Result.Ok;
import core.util.Result.Result;

public class UnreachableInstr implements ControlInstr {
    public static Result<ControlInstr, ParseException> parse(Parser parser) {
        return new Ok<>(new UnreachableInstr());
    }

    public String toString() {
        return "UnreachableInstr";
    }
}
