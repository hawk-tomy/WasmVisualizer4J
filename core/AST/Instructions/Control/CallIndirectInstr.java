package core.AST.Instructions.Control;

import core.Parser;
import core.util.ParseException;
import core.util.Result.Err;
import core.util.Result.Ok;
import core.util.Result.Result;

public class CallIndirectInstr implements ControlInstr {
    final int typeIdx;

    CallIndirectInstr(int typeIdx) {
        this.typeIdx = typeIdx;
    }

    public static Result<ControlInstr, ParseException> parse(Parser parser) {
        int typeIdx;
        switch (parser.nextU32()) {
            case Err(ParseException e) -> {return new Err<>(e);}
            case Ok(Integer idx) -> typeIdx = idx;
        }
        return parser.nextByte((byte) 0x00).map(ignored -> new CallIndirectInstr(typeIdx));
    }

    public String toString() {
        return "CallIndirectInstr(typeIdx=" + this.typeIdx + ')';
    }

    public String content() {
        return "call_indirect 0x00";
    }
}
