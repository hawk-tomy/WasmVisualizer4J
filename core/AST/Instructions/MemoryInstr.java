package core.AST.Instructions;

import core.Parser;
import core.util.ParseException;
import core.util.Result.Err;
import core.util.Result.Ok;
import core.util.Result.Result;
import core.util.ToStringUtil;
import core.util.UnsignedByteOp;

public class MemoryInstr implements Instruction {
    final byte instr;
    final int align;
    final int offset;

    MemoryInstr(byte instr, int align, int offset) {
        this.instr = instr;
        this.align = align;
        this.offset = offset;
    }

    public static Result<Instruction, ParseException> parse(Parser parser, byte b) {
        if (UnsignedByteOp.isInRange((byte) 0x28, b, (byte) 0x3F)) {
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
            return parser.nextByte((byte) 0x00).map(ignored -> new MemoryInstr(b, 0, 0));
        }
        return new Err<>(new ParseException("Unknown Instruction."));
    }

    public String toString() {
        return String.format("MemoryInstr(instr=0x%X, align=%d, offset=%d)", this.instr, this.align, this.offset);
    }

    public String content() {
        StringBuilder sb = new StringBuilder();
        if (this.instr == 0x3F) {
            sb.append("memory.size");
        } else if (this.instr == 0x40) {
            sb.append("memory.grow");
        } else {
            sb.append(switch (this.instr) {
                case 0x28 -> "i32.load";
                case 0x29 -> "i64.load";
                case 0x2A -> "f32.load";
                case 0x2B -> "f64.load";
                case 0x2C -> "i32.load8_s";
                case 0x2D -> "i32.load8_u";
                case 0x2E -> "i32.load16_s";
                case 0x2F -> "i32.load16_u";
                case 0x30 -> "i64.load8_s";
                case 0x31 -> "i64.load8_u";
                case 0x32 -> "i64.load16_s";
                case 0x33 -> "i64.load16_u";
                case 0x34 -> "i64.load32_s";
                case 0x35 -> "i64.load32_u";
                case 0x36 -> "i32.store";
                case 0x37 -> "i64.store";
                case 0x38 -> "f32.store";
                case 0x39 -> "f64.store";
                case 0x3A -> "i32.store8";
                case 0x3B -> "i32.store16";
                case 0x3C -> "i64.store8";
                case 0x3D -> "i64.store16";
                case 0x3E -> "i64.store32";
                default -> throw new Error(String.format("Unknown Instruction: 0x%X", this.instr));
            });
            sb.append(" align: ");
            sb.append(ToStringUtil.intoHex(this.align));
            sb.append(" offset: ");
            sb.append(ToStringUtil.intoHex(this.offset));
        }
        return sb.toString();
    }
}
