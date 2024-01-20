package core.AST.Instructions.Control;

import core.Parser;
import core.util.ParseException;
import core.util.Result.Result;
import core.util.ToStringUtil;

public class BranchInstr implements ControlInstr {
    final int uLabelIdx;

    BranchInstr(int uLabelIdx) {
        this.uLabelIdx = uLabelIdx;
    }

    public static Result<ControlInstr, ParseException> parse(Parser parser) {
        return parser.nextU32().map(BranchInstr::new);
    }

    public String toString() {
        return "BranchInstr(uLabelIdx=" + this.uLabelIdx + ')';
    }

    public String content() {
        return "br " + ToStringUtil.intoHex(this.uLabelIdx);
    }
}
