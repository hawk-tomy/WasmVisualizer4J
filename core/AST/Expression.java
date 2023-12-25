package core.AST;

import core.AST.Instructions.Instruction;
import core.Parser;
import core.util.ParseException;
import core.util.Result.Ok;
import core.util.Result.Result;
import core.util.ToStringUtil;

import java.util.ArrayList;

public class Expression {
    ArrayList<Instruction> instructions;

    Expression(ArrayList<Instruction> instructions) {
        this.instructions = instructions;
    }

    public static Result<Expression, ParseException> parse(Parser parser) {
        ArrayList<Instruction> instructions = parser.parseSequence(Instruction::parse);
        return parser
            .nextByte((byte) 0x0B)
            .and(new Ok<>(new Expression(instructions)));
    }

    public String toString() {
        return (
            "Expression(\n"
            + ("instructions=" + ToStringUtil.arrayList(this.instructions)).indent(2)
            + ")"
        );
    }
}
