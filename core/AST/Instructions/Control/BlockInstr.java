package core.AST.Instructions.Control;

import core.AST.Instructions.Instruction;
import core.AST.Type.BlockType;
import core.Parser;
import core.util.ParseException;
import core.util.Result.Err;
import core.util.Result.Ok;
import core.util.Result.Result;
import core.util.ToStringUtil;

import java.util.ArrayList;

public class BlockInstr implements ControlInstr {
    BlockType rt;
    ArrayList<Instruction> its;

    BlockInstr(BlockType rt, ArrayList<Instruction> its) {
        this.rt = rt;
        this.its = its;
    }

    public static Result<ControlInstr, ParseException> parse(Parser parser) {
        BlockType rt;
        switch (BlockType.parse(parser)) {
            case Err(ParseException e) -> {return new Err<>(e);}
            case Ok(BlockType rt_) -> rt = rt_;
        }
        ArrayList<Instruction> its;
        switch (parser.nextSequence(
            p -> p.nextByte((byte) 0x0B).isErr(),
            Instruction::parse
        )) {
            case Err(ParseException e) -> {return new Err<>(e);}
            case Ok(ArrayList<Instruction> its_) -> its = its_;
        }
        return new Ok<>(new BlockInstr(rt, its));
    }

    public String toString() {
        return (
            "BlockInstr(\n"
            + (
                "rt=" + this.rt
                + "\nits=" + ToStringUtil.arrayList(this.its)
            ).indent(2)
            + ')'
        );
    }

    public String content() {
        StringBuilder sb = new StringBuilder();
        sb.append("block ");
        sb.append(this.rt.content());
        ControlInstr.arrayInstructionContent(sb, this.its);
        return sb.toString();
    }
}
