package core.AST.Instructions;

import core.Parser;
import core.util.ParseException;
import core.util.Result.Err;
import core.util.Result.Result;

public class VariableInstr implements Instruction {
    int uIdx;
    byte instr;

    VariableInstr(int uIdx, byte instr) {
        this.uIdx = uIdx;
        this.instr = instr;
    }

    public static Result<Instruction, ParseException> parse(Parser parser, byte b) {
        return switch (b) {
            case 0x20, 0x21, 0x22, 0x23, 0x24 -> {
                parser.next();
                yield parser
                    .nextU32()
                    .map(idx -> new VariableInstr(idx, b));
            }
            default -> new Err<>(new ParseException("Unknown Instruction."));
        };
    }

    public String toString() {
        return (
            "VariableInstr("
            + (
                "uIdx=" + this.uIdx
                + "instr=" + String.format("%X", this.instr)
            ).indent(2)
            + "\n)"
        );
    }
}
