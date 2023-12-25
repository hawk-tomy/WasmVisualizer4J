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
    BlockType rt;
    ArrayList<Instruction> its;

    LoopInstr(BlockType rt, ArrayList<Instruction> its) {
        this.rt = rt;
        this.its = its;
    }

    public static Result<ControlInstr, ParseException> parse(Parser parser) {
        if (parser.nextByte((byte) 0x3) instanceof Err(ParseException e)) {
            return new Err<>(e);
        }
        BlockType rt;
        switch (BlockType.parse(parser)) {
            case Err(ParseException e) -> {return new Err<>(e);}
            case Ok(BlockType rt_) -> rt = rt_;
        }
        ArrayList<Instruction> its = new ArrayList<>();
        while (parser
            .peek()
            .isOkAnd(b -> b != 0x0B)) {
            parser.next();
            if (Instruction.parse(parser) instanceof Ok(Instruction it)) {
                its.add(it);
            } else {
                break;
            }
        }
        if (parser.nextByte((byte) 0x0B) instanceof Err(ParseException e)) {
            return new Err<>(e);
        }
        return new Ok<>(new LoopInstr(rt, its));
    }

    public String toString() {
        return (
            "LoopInstr(\n"
            + (
                "rt=" + this.rt
                + "\nits=" + ToStringUtil.arrayList(this.its)
            ).indent(2)
            + ")"
        );
    }
}
