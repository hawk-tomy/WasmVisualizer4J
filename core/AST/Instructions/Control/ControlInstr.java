package core.AST.Instructions.Control;

import core.AST.Instructions.Instruction;
import core.Parser;
import core.util.ParseException;
import core.util.Result.Err;
import core.util.Result.Result;

public interface ControlInstr extends Instruction {
    static Result<ControlInstr, ParseException> parse(Parser parser, byte b) {
        return switch (b) {
            case 0x00 -> UnreachableInstr.parse(parser);
            case 0x01 -> NopInstr.parse(parser);
            case 0x02 -> BlockInstr.parse(parser);
            case 0x03 -> LoopInstr.parse(parser);
            case 0x04 -> IfInstr.parse(parser);
            case 0x0C -> BranchInstr.parse(parser);
            case 0x0D -> BranchIfInstr.parse(parser);
            case 0x0E -> BranchTableInstr.parse(parser);
            case 0x0F -> ReturnInstr.parse(parser);
            case 0x10 -> CallInstr.parse(parser);
            case 0x11 -> CallIndirectInstr.parse(parser);
            default -> new Err<>(new ParseException("Not Found."));
        };
    }
}
