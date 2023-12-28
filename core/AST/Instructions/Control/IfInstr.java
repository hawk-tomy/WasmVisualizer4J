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
        BlockType rt;
        switch (BlockType.parse(parser)) {
            case Err(ParseException e) -> {return new Err<>(e);}
            case Ok(BlockType rt_) -> rt = rt_;
        }
        ArrayList<Instruction> in1;
        switch (parser.nextSequence(
            p -> p.peek().isOkAnd(b -> (b != 0x0B && b != 0x05)), // has byte and not end byte
            Instruction::parse
        )) {
            case Err(ParseException e) -> {return new Err<>(e);}
            case Ok(ArrayList<Instruction> in) -> in1 = in;
        }
        ArrayList<Instruction> in2;
        if (parser.nextByte((byte) 0x05).isOk()) {
            switch (parser.nextSequence(
                p -> p.nextByte((byte) 0x0B).isErr(),
                Instruction::parse
            )) {
                case Err(ParseException e) -> {return new Err<>(e);}
                case Ok(ArrayList<Instruction> in) -> in2 = in;
            }
        } else if (parser.nextByte((byte) 0x0B).isOk()) {
            in2 = new ArrayList<>();
        } else {
            throw new Error("Unreachable");
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
