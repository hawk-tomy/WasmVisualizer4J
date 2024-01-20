package core.AST.Instructions.Control;

import core.Parser;
import core.util.ParseException;
import core.util.Result.Result;
import core.util.ToStringUtil;

public class CallInstr implements ControlInstr {
    int funcIdx;

    CallInstr(int funcIdx) {
        this.funcIdx = funcIdx;
    }

    public static Result<ControlInstr, ParseException> parse(Parser parser) {
        return parser.nextU32().map(CallInstr::new);
    }

    public String toString() {
        return "CallInstr(funcIdx=" + this.funcIdx + ')';
    }

    public String content() {
        return "call " + ToStringUtil.intoHex(this.funcIdx);
    }
}
