package core.AST.Instructions.Control;

import core.Parser;
import core.util.ParseException;
import core.util.Result.Result;
import core.util.ToStringUtil;

public class BranchIfInstr implements ControlInstr {
    int uLabelIdx;

    BranchIfInstr(int uLabelIdx) {
        this.uLabelIdx = uLabelIdx;
    }

    public static Result<ControlInstr, ParseException> parse(Parser parser) {
        return parser.nextU32().map(BranchIfInstr::new);
    }

    public String toString() {
        return "BranchIfInstr(uLabelIdx=" + this.uLabelIdx + ')';
    }

    public String content() {
        return "br_if " + ToStringUtil.intoHex(this.uLabelIdx);
    }
}
