package core.AST.Instructions;

import core.AST.Instructions.Control.ControlInstr;
import core.Parser;
import core.util.InvalidIndexException;
import core.util.ParseException;
import core.util.Result.Err;
import core.util.Result.Ok;
import core.util.Result.Result;
import core.util.UnsignedByteOp;

public interface Instruction {
    public static Result<Instruction, ParseException> parse(Parser parser) {
        byte b;
        switch (parser.peek()) {
            case Err(InvalidIndexException e) -> {return new Err<>(e.into());}
            case Ok(Byte b_) -> b = b_;
        }
        parser.next();
        if (UnsignedByteOp.isInRange((byte) 0x00, b, (byte) 0x12)) {
            return ControlInstr
                .parse(parser, b)
                .map(i -> i);
        }
        if (b == 0x1A || b == 0x1B) {
            return ParametricInstr.parse(parser, b);
        }
        if (UnsignedByteOp.isInRange((byte) 0x20, b, (byte) 0x25)) {
            return VariableInstr.parse(parser, b);
        }
        if (UnsignedByteOp.isInRange((byte) 0x28, b, (byte) 0x41)) {
            return MemoryInstr.parse(parser, b);
        }
        if (UnsignedByteOp.isInRange((byte) 0x41, b, (byte) 0x45)) {
            return ConstInstr.parse(parser, b);
        }
        if (UnsignedByteOp.isInRange((byte) 0x45, b, (byte) 0xC0)) {
            return NumericInstr.parse(parser, b);
        }
        return new Err<>(new ParseException("Unknown Instruction"));
    }
}
