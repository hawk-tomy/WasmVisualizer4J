package core.AST.Instructions.Control;

import core.Parser;
import core.util.ParseException;
import core.util.Result.Err;
import core.util.Result.Result;

public class CallInstr implements ControlInstr {
    int funcIdx;

    CallInstr(int funcIdx) {
        this.funcIdx = funcIdx;
    }

    public static Result<ControlInstr, ParseException> parse(Parser parser) {
        if (parser.nextByte((byte) 0x10) instanceof Err(ParseException e)) {
            return new Err<>(e);
        }
        return parser
            .nextU32()
            .map(CallInstr::new);
    }

    public String toString() {
        return "CallInstr(funcIdx=" + this.funcIdx + ')';
    }
}
