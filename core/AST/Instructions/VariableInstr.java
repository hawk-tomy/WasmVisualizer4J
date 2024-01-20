package core.AST.Instructions;

import core.Parser;
import core.util.ParseException;
import core.util.Result.Err;
import core.util.Result.Result;
import core.util.ToStringUtil;

public class VariableInstr implements Instruction {
    final int uIdx;
    final byte instr;

    VariableInstr(int uIdx, byte instr) {
        this.uIdx = uIdx;
        this.instr = instr;
    }

    public static Result<Instruction, ParseException> parse(Parser parser, byte b) {
        return switch (b) {
            case 0x20, 0x21, 0x22, 0x23, 0x24 -> parser.nextU32().map(idx -> new VariableInstr(idx, b));
            default -> new Err<>(new ParseException("Unknown Instruction."));
        };
    }

    public String toString() {
        return (
            "VariableInstr(\n"
            + (
                "uIdx=" + this.uIdx
                + "\ninstr=" + String.format("0x%X", this.instr)
            ).indent(2)
            + ')'
        );
    }

    public String content() {
        StringBuilder sb = new StringBuilder();
        sb.append(switch (this.instr) {
            case 0x20 -> "local.get";
            case 0x21 -> "local.set";
            case 0x22 -> "local.tee";
            case 0x23 -> "global.get";
            case 0x24 -> "global.set";
            default -> throw new Error(String.format("Unknown Instruction: 0x%X", this.instr));
        });
        sb.append(" ");
        sb.append(ToStringUtil.intoHex(this.uIdx));
        return sb.toString();
    }
}
