package core.AST.Component;

import core.AST.Expression;
import core.Parser;
import core.util.InvalidIndexException;
import core.util.ParseException;
import core.util.Result.Err;
import core.util.Result.Ok;
import core.util.Result.Result;

import java.util.ArrayList;
import java.util.HexFormat;
import java.util.stream.Collectors;

public class DataComponent {
    int memIdx;
    Expression expr;
    ArrayList<Byte> bytes;

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
        ArrayList<Byte> bytes;
        switch (parser.nextVector(p -> p
            .next()
            .mapErr(InvalidIndexException::into))) {
            case Err(ParseException e) -> {return new Err<>(e);}
            case Ok(ArrayList<Byte> bytes_) -> bytes = bytes_;
        }
        return new Ok<>(new DataComponent(memIdx, expr, bytes));
    }

    public String toString() {
        HexFormat f = HexFormat.of();
        String s = this.bytes
            .subList(0, 10)
            .stream()
            .map(f::toHexDigits)
            .collect(Collectors.joining());
        return (
            "DataComponent("
            + (
                "memIdx=" + this.memIdx
                + "\nexpr=" + this.expr
                + "\nbytes=" + s + "..."
            )
            + "\n)"
        );
    }
}
