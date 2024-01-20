package core.AST.Component;

import core.AST.Expression;
import core.Parser;
import core.util.InvalidIndexException;
import core.util.ParseException;
import core.util.Result.Err;
import core.util.Result.Ok;
import core.util.Result.Result;
import core.util.ToStringUtil;

import java.util.ArrayList;

public class DataComponent {
    public final int memIdx;
    public final Expression expr;
    public final ArrayList<Byte> bytes;

    DataComponent(int memIdx, Expression expr, ArrayList<Byte> bytes) {
        this.memIdx = memIdx;
        this.expr = expr;
        this.bytes = bytes;
    }

    public static Result<DataComponent, ParseException> parse(Parser parser) {
        int memIdx;
        switch (parser.nextU32()) {
            case Err(ParseException e) -> {return new Err<>(e);}
            case Ok(Integer idx) -> memIdx = idx;
        }
        Expression expr;
        switch (Expression.parse(parser)) {
            case Err(ParseException e) -> {return new Err<>(e);}
            case Ok(Expression expr_) -> expr = expr_;
        }
        return parser
            .nextVector(p -> p.next().mapErr(InvalidIndexException::into))
            .map(bytes -> new DataComponent(memIdx, expr, bytes));
    }

    public String toString() {
        return (
            "DataComponent("
            + (
                "memIdx=" + this.memIdx
                + "\nexpr=" + this.expr
                + "\nbytes=" + ToStringUtil.byteList(this.bytes)
            )
            + ')'
        );
    }
}
