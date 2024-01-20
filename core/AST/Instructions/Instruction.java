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
    static Result<Instruction, ParseException> parse(Parser parser) {
        byte b;
        switch (parser.peek()) {
            case Err(InvalidIndexException e) -> {return new Err<>(e.into());}
            case Ok(Byte b_) -> b = b_;
        }
        if (UnsignedByteOp.isInRange((byte) 0x00, b, (byte) 0x12)) {
            parser.consume();
            return ControlInstr.parse(parser, b).map(i -> i);
        } else if (b == 0x1A || b == 0x1B) {
            parser.consume();
            return ParametricInstr.parse(parser, b);
        } else if (UnsignedByteOp.isInRange((byte) 0x20, b, (byte) 0x25)) {
            parser.consume();
            return VariableInstr.parse(parser, b);
        } else if (UnsignedByteOp.isInRange((byte) 0x28, b, (byte) 0x41)) {
            parser.consume();
            return MemoryInstr.parse(parser, b);
        } else if (UnsignedByteOp.isInRange((byte) 0x41, b, (byte) 0x45)) {
            parser.consume();
            return ConstInstr.parse(parser, b);
        } else if (UnsignedByteOp.isInRange((byte) 0x45, b, (byte) 0xC0)) {
            parser.consume();
            return NumericInstr.parse(parser, b);
        }
        return new Err<>(new ParseException("Unknown Instruction"));
    }

    String content();
}
