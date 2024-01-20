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

public class LoopInstr implements ControlInstr {
    final BlockType rt;
    final ArrayList<Instruction> its;

    LoopInstr(BlockType rt, ArrayList<Instruction> its) {
        this.rt = rt;
        this.its = its;
    }

    public static Result<ControlInstr, ParseException> parse(Parser parser) {
        BlockType rt;
        switch (BlockType.parse(parser)) {
            case Err(ParseException e) -> {return new Err<>(e);}
            case Ok(BlockType rt_) -> rt = rt_;
        }
        return parser
            .nextSequence(p -> p.nextByte((byte) 0x0B).isErr(), Instruction::parse)
            .map(its -> new LoopInstr(rt, its));
    }

    public String toString() {
        return (
            "LoopInstr(\n"
            + (
                "rt=" + this.rt
                + "\nits=" + ToStringUtil.arrayList(this.its)
            ).indent(2)
            + ')'
        );
    }

    public String content() {
        StringBuilder sb = new StringBuilder();
        sb.append("loop ");
        sb.append(this.rt.content());
        ControlInstr.arrayInstructionContent(sb, this.its);
        return sb.toString();
    }
}
