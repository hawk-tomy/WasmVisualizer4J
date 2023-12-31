package core.AST.Instructions;

import core.Parser;
import core.util.ParseException;
import core.util.Result.Err;
import core.util.Result.Ok;
import core.util.Result.Result;

import java.util.ArrayList;

public class ConstInstr implements Instruction {
    byte instr;
    int i32;
    long i64;
    float f32;
    double f64;

    ConstInstr(byte instr, int i32) {
        this.instr = instr;
        this.i32 = i32;
    }

    ConstInstr(byte instr, long i64) {
        this.instr = instr;
        this.i64 = i64;
    }

    ConstInstr(byte instr, float f32) {
        this.instr = instr;
        this.f32 = f32;
    }

    ConstInstr(byte instr, double f64) {
        this.instr = instr;
        this.f64 = f64;
    }

    public static Result<Instruction, ParseException> parse(Parser parser, byte b) {
        return switch (b) {
            case 0x41 -> parser.nextI32().map(i -> new ConstInstr(b, i));
            case 0x42 -> parser.nextI64().map(i -> new ConstInstr(b, i));
            case 0x43 -> {
                int x = 0;
                ArrayList<Byte> bytes;
                switch (parser.nextBytes(4)) {
                    case Err(ParseException e) -> {yield new Err<>(e);}
                    case Ok(ArrayList<Byte> bytes_) -> bytes = bytes_;
                }
                for (int i = 0; i < 4; i++) {
                    x |= (int) bytes.get(i) << i;
                }
                yield new Ok<>(new ConstInstr(b, Float.intBitsToFloat(x)));
            }
            case 0x44 -> {
                long x = 0;
                ArrayList<Byte> bytes;
                switch (parser.nextBytes(8)) {
                    case Err(ParseException e) -> {yield new Err<>(e);}
                    case Ok(ArrayList<Byte> bytes_) -> bytes = bytes_;
                }
                for (int i = 0; i < 8; i++) {
                    x |= (long) bytes.get(i) << i;
                }
                yield new Ok<>(new ConstInstr(b, Double.longBitsToDouble(x)));
            }
            default -> new Err<>(new ParseException("Unknown Instruction."));
        };
    }

    public String toString() {
        String s = switch (this.instr) {
            case 0x41 -> ((Integer) this.i32).toString();
            case 0x42 -> ((Long) this.i64).toString();
            case 0x43 -> ((Float) this.f32).toString();
            case 0x44 -> ((Double) this.f64).toString();
            default -> throw new Error(String.format("Unknown Instruction: 0x%X", this.instr));
        };
        return String.format("ConstInstr(instr=0x%X, %s)", this.instr, s);
    }
}
