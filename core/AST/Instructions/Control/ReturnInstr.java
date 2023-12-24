package core.AST.Instructions.Control;

import core.Parser;
import core.util.ParseException;
import core.util.Result.Err;
import core.util.Result.Ok;
import core.util.Result.Result;

public class ReturnInstr implements ControlInstr {
    public static Result<ControlInstr, ParseException> parse(Parser parser) {
        if (parser.nextByte((byte) 0x0F) instanceof Err(ParseException e)) {
            return new Err<>(e);
        } else {
            return new Ok<>(new ReturnInstr());
        }
    }
}
