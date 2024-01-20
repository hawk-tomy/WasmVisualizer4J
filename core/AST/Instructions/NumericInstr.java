package core.AST.Instructions;

import core.Parser;
import core.util.ParseException;
import core.util.Result.Err;
import core.util.Result.Ok;
import core.util.Result.Result;
import core.util.UnsignedByteOp;

public class NumericInstr implements Instruction {
    final byte instr;

    NumericInstr(byte instr) {
        this.instr = instr;
    }

    public static Result<Instruction, ParseException> parse(Parser ignoredParser, byte b) {
        if (UnsignedByteOp.isInRange((byte) 0x45, b, (byte) 0xC0)) {
            return new Ok<>(new NumericInstr(b));
        }
        return new Err<>(new ParseException("Unknown Instruction."));
    }

    public String toString() {
        return String.format("NumericInstr(instr=0x%X)", this.instr);
    }

    public String content() {
        return switch (((int) this.instr) & 0xFF) {
            case 0x45 -> "i32.eqz";
            case 0x46 -> "i32.eq";
            case 0x47 -> "i32.ne";
            case 0x48 -> "i32.lt_s";
            case 0x49 -> "i32.lt_u";
            case 0x4A -> "i32.gt_s";
            case 0x4B -> "i32.gt_u";
            case 0x4C -> "i32.le_s";
            case 0x4D -> "i32.le_u";
            case 0x4E -> "i32.ge_s";
            case 0x4F -> "i32.ge_u";
            case 0x50 -> "i64.eqz";
            case 0x51 -> "i64.eq";
            case 0x52 -> "i64.ne";
            case 0x53 -> "i64.lt_s";
            case 0x54 -> "i64.lt_u";
            case 0x55 -> "i64.gt_s";
            case 0x56 -> "i64.gt_u";
            case 0x57 -> "i64.le_s";
            case 0x58 -> "i64.le_u";
            case 0x59 -> "i64.ge_s";
            case 0x5A -> "i64.ge_u";
            case 0x5B -> "f32.eq";
            case 0x5C -> "f32.ne";
            case 0x5D -> "f32.lt";
            case 0x5E -> "f32.gt";
            case 0x5F -> "f32.le";
            case 0x60 -> "f32.ge";
            case 0x61 -> "f64.eq";
            case 0x62 -> "f64.ne";
            case 0x63 -> "f64.lt";
            case 0x64 -> "f64.gt";
            case 0x65 -> "f64.le";
            case 0x66 -> "f64.ge";
            case 0x67 -> "i32.clz";
            case 0x68 -> "i32.ctz";
            case 0x69 -> "i32.popcnt";
            case 0x6A -> "i32.add";
            case 0x6B -> "i32.sub";
            case 0x6C -> "i32.mul";
            case 0x6D -> "i32.div_s";
            case 0x6E -> "i32.div_u";
            case 0x6F -> "i32.rem_s";
            case 0x70 -> "i32.rem_u";
            case 0x71 -> "i32.and";
            case 0x72 -> "i32.or";
            case 0x73 -> "i32.xor";
            case 0x74 -> "i32.shl";
            case 0x75 -> "i32.shr_s";
            case 0x76 -> "i32.shr_u";
            case 0x77 -> "i32.rotl";
            case 0x78 -> "i32.rotr";
            case 0x79 -> "i64.clz";
            case 0x7A -> "i64.ctz";
            case 0x7B -> "i64.popcnt";
            case 0x7C -> "i64.add";
            case 0x7D -> "i64.sub";
            case 0x7E -> "i64.mul";
            case 0x7F -> "i64.div_s";
            case 0x80 -> "i64.div_u";
            case 0x81 -> "i64.rem_s";
            case 0x82 -> "i64.rem_u";
            case 0x83 -> "i64.and";
            case 0x84 -> "i64.or";
            case 0x85 -> "i64.xor";
            case 0x86 -> "i64.shl";
            case 0x87 -> "i64.shr_s";
            case 0x88 -> "i64.shr_u";
            case 0x89 -> "i64.rotl";
            case 0x8A -> "i64.rotr";
            case 0x8B -> "f32.abs";
            case 0x8C -> "f32.neg";
            case 0x8D -> "f32.ceil";
            case 0x8E -> "f32.floor";
            case 0x8F -> "f32.trunc";
            case 0x90 -> "f32.nearest";
            case 0x91 -> "f32.sqrt";
            case 0x92 -> "f32.add";
            case 0x93 -> "f32.sub";
            case 0x94 -> "f32.mul";
            case 0x95 -> "f32.div";
            case 0x96 -> "f32.min";
            case 0x97 -> "f32.max";
            case 0x98 -> "f32.copysign";
            case 0x99 -> "f64.abs";
            case 0x9A -> "f64.neg";
            case 0x9B -> "f64.ceil";
            case 0x9C -> "f64.floor";
            case 0x9D -> "f64.trunc";
            case 0x9E -> "f64.nearest";
            case 0x9F -> "f64.sqrt";
            case 0xA0 -> "f64.add";
            case 0xA1 -> "f64.sub";
            case 0xA2 -> "f64.mul";
            case 0xA3 -> "f64.div";
            case 0xA4 -> "f64.min";
            case 0xA5 -> "f64.max";
            case 0xA6 -> "f64.copysign";
            case 0xA7 -> "i32.wrap_i64";
            case 0xA8 -> "i32.trunc_f32_s";
            case 0xA9 -> "i32.trunc_f32_u";
            case 0xAA -> "i32.trunc_f64_s";
            case 0xAB -> "i32.trunc_f64_u";
            case 0xAC -> "i64.extend_i32_s";
            case 0xAD -> "i64.extend_i32_u";
            case 0xAE -> "i64.trunc_f32_s";
            case 0xAF -> "i64.trunc_f32_u";
            case 0xB0 -> "i64.trunc_f64_s";
            case 0xB1 -> "i64.trunc_f64_u";
            case 0xB2 -> "f32.convert_i32_s";
            case 0xB3 -> "f32.convert_i32_u";
            case 0xB4 -> "f32.convert_i64_s";
            case 0xB5 -> "f32.convert_i64_u";
            case 0xB6 -> "f32.demote_f64";
            case 0xB7 -> "f64.convert_i32_s";
            case 0xB8 -> "f64.convert_i32_u";
            case 0xB9 -> "f64.convert_i64_s";
            case 0xBA -> "f64.convert_i64_u";
            case 0xBB -> "f64.promote_f32";
            case 0xBC -> "i32.reinterpret_f32";
            case 0xBD -> "i64.reinterpret_f64";
            case 0xBE -> "f32.reinterpret_i32";
            case 0xBF -> "f64.reinterpret_i64";
            default -> throw new Error(String.format("Unknown Instruction: 0x%X", this.instr));
        };
    }
}
