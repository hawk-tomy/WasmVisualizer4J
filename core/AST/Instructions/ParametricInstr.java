package core.AST.Instructions;

import core.Parser;
import core.util.ParseException;
import core.util.Result.Err;
import core.util.Result.Ok;
import core.util.Result.Result;

public class ParametricInstr implements Instruction {
    boolean isDrop;

    ParametricInstr(boolean isDrop) {
        this.isDrop = isDrop;
    }

    public static Result<Instruction, ParseException> parse(Parser parser, byte b) {
        if (!(b == 0x1A || b == 0x1B)) {
            return new Err<>(new ParseException("Unknown Instruction."));
        }
        parser.next();
        return new Ok<>(new ParametricInstr(b == 0x1A));
    }
}
