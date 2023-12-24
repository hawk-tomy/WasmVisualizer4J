package core.AST.Instructions;

import core.Parser;
import core.util.ParseException;
import core.util.Result.Err;
import core.util.Result.Ok;
import core.util.Result.Result;
import core.util.UnsignedByteOp;

public class MemoryInstr implements Instruction {
    byte instr;
    int align, offset;

    MemoryInstr(byte instr, int align, int offset) {
        this.instr = instr;
        this.align = align;
        this.offset = offset;
    }

    public static Result<Instruction, ParseException> parse(Parser parser, byte b) {
        if (UnsignedByteOp.isInRange((byte) 0x28, b, (byte) 0x3F)) {
            parser.next();
            int align, offset;
            switch (parser.nextU32()) {
                case Err(ParseException e) -> {return new Err<>(e);}
                case Ok(Integer align_) -> align = align_;
            }
            switch (parser.nextU32()) {
                case Err(ParseException e) -> {return new Err<>(e);}
                case Ok(Integer offset_) -> offset = offset_;
            }
            return new Ok<>(new MemoryInstr(b, align, offset));
        } else if (UnsignedByteOp.isInRange((byte) 0x3F, b, (byte) 0x41)) {
            parser.next();
            return parser
                .nextByte((byte) 0x00)
                .map(ignored -> new MemoryInstr(b, 0, 0));
        }
        return new Err<>(new ParseException("Unknown Instruction."));
    }

    public String toString() {
        return String.format("MemoryInstr(instr=%X, align=%d, offset=%d)", this.instr, this.align, this.offset);
    }
}
