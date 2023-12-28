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

public class IfInstr implements ControlInstr {
    BlockType rt;
    ArrayList<Instruction> in1;
    ArrayList<Instruction> in2;

    IfInstr(BlockType rt, ArrayList<Instruction> in1, ArrayList<Instruction> in2) {
        this.rt = rt;
        this.in1 = in1;
        this.in2 = in2;
    }

    public static Result<ControlInstr, ParseException> parse(Parser parser) {
        if (parser.nextByte((byte) 0x04) instanceof Err(ParseException e)) {
            return new Err<>(e);
        }
        BlockType rt;
        switch (BlockType.parse(parser)) {
            case Err(ParseException e) -> {return new Err<>(e);}
            case Ok(BlockType rt_) -> rt = rt_;
        }
        ArrayList<Instruction> in1 = new ArrayList<>();
        while (parser
            .peek()
            .isOkAnd(b -> (b != 0x0B && b != 0x05))) {
            parser.next();
            if (Instruction.parse(parser) instanceof Ok(Instruction it)) {
                in1.add(it);
            } else {
                break;
            }
        }
        ArrayList<Instruction> in2 = new ArrayList<>();
        if (parser.nextByte((byte) 0x05).isOk()) {
            while (parser.nextByte((byte) 0x0B).isErr()) {
                if (Instruction.parse(parser) instanceof Ok(Instruction it)) {
                    in1.add(it);
                } else {
                    break;
                }
            }
        } else {
            parser.next(); // consume 0x0B
        }
        return new Ok<>(new IfInstr(rt, in1, in2));
    }

    public String toString() {
        return (
            "IfInstr("
            + (
                "rt=" + this.rt
                + "\nin1=" + ToStringUtil.arrayList(this.in1)
                + "\nin2=" + ToStringUtil.arrayList(this.in2)
            ).indent(2)
            + ')'
        );
    }
}
