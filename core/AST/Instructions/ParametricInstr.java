package core.AST.Instructions;

import core.Parser;
import core.util.ParseException;
import core.util.Result.Err;
import core.util.Result.Ok;
import core.util.Result.Result;

public class ParametricInstr implements Instruction {
    final boolean isDrop;

    ParametricInstr(boolean isDrop) {
        this.isDrop = isDrop;
    }

    public static Result<Instruction, ParseException> parse(Parser ignoredParser, byte b) {
        if (b != 0x1A && b != 0x1B) {
            return new Err<>(new ParseException("Unknown Instruction."));
        }
        return new Ok<>(new ParametricInstr(b == 0x1A));
    }

    public String toString() {
        return "ParametricInstr(isDrop=" + this.isDrop + ')';
    }

    public String content() {
        return this.isDrop ? "drop" : "select";
    }
}
