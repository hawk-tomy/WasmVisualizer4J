package core.AST.Instructions.Control;

import core.Parser;
import core.util.ParseException;
import core.util.Result.Err;
import core.util.Result.Ok;
import core.util.Result.Result;

public class NopInstr implements ControlInstr {
    public static Result<ControlInstr, ParseException> parse(Parser parser) {
        if (parser.nextByte((byte) 0x01) instanceof Err(ParseException e)) {
            return new Err<>(e);
        } else {
            return new Ok<>(new NopInstr());
        }
    }
}
