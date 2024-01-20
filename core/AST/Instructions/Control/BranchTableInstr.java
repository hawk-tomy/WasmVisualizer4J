package core.AST.Instructions.Control;

import core.Parser;
import core.util.ParseException;
import core.util.Result.Err;
import core.util.Result.Ok;
import core.util.Result.Result;
import core.util.ToStringUtil;

import java.util.ArrayList;

public class BranchTableInstr implements ControlInstr {
    final ArrayList<Integer> uLabelIdxes;
    final int defaultLabelIdx;

    BranchTableInstr(ArrayList<Integer> uLabelIdxes, int defaultLabelIdx) {
        this.uLabelIdxes = uLabelIdxes;
        this.defaultLabelIdx = defaultLabelIdx;
    }

    public static Result<ControlInstr, ParseException> parse(Parser parser) {
        ArrayList<Integer> labelIdxes;
        switch (parser.nextVector(Parser::nextU32)) {
            case Err(ParseException e) -> {return new Err<>(e);}
            case Ok(ArrayList<Integer> idxes) -> labelIdxes = idxes;
        }
        return parser.nextU32().map(idx -> new BranchTableInstr(labelIdxes, idx));
    }

    public String toString() {
        return (
            "BranchTableInstr("
            + (
                "uLabelIdxes=" + ToStringUtil.arrayList(this.uLabelIdxes)
                + "\ndefaultLabelIdx=" + this.defaultLabelIdx
            ).indent(2)
            + ')'
        );
    }

    public String content() {
        StringBuilder sb = new StringBuilder();
        sb.append("br_table ");
        for (Integer idx : this.uLabelIdxes) {
            sb.append(ToStringUtil.intoHex(idx));
            sb.append(" ");
        }
        sb.append(ToStringUtil.intoHex(this.defaultLabelIdx));
        return sb.toString();
    }
}
