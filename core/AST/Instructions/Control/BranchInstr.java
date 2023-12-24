package core.AST.Instructions.Control;

import core.Parser;
import core.util.ParseException;
import core.util.Result.Err;
import core.util.Result.Ok;
import core.util.Result.Result;

public class BranchInstr implements ControlInstr {
    int uLabelIdx;

    BranchInstr(int uLabelIdx) {
        this.uLabelIdx = uLabelIdx;
    }

    public static Result<ControlInstr, ParseException> parse(Parser parser) {
        if (parser.nextByte((byte) 0x0C) instanceof Err(ParseException e)) {
            return new Err<>(e);
        }
        return switch (parser.nextU32()) {
            case Err(ParseException e) -> new Err<>(e);
            case Ok(Integer idx) -> new Ok<>(new BranchInstr(idx));
        };
    }

    public String toString() {
        return "BranchInstr(uLabelIdx=" + this.uLabelIdx + ')';
    }
}
