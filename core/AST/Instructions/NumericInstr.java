package core.AST.Instructions;

import core.Parser;
import core.util.ParseException;
import core.util.Result.Err;
import core.util.Result.Ok;
import core.util.Result.Result;
import core.util.UnsignedByteOp;

public class NumericInstr implements Instruction {
    byte instr;

    NumericInstr(byte instr) {
        this.instr = instr;
    }

    public static Result<Instruction, ParseException> parse(Parser parser, byte b) {
        if (UnsignedByteOp.isInRange((byte) 0x41, b, (byte) 0xC0)) {
            parser.next();
            return new Ok<>(new NumericInstr(b));
        }
        return new Err<>(new ParseException("Unknown Instruction."));
    }
}
