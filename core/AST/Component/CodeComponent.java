package core.AST.Component;

import core.AST.Expression;
import core.Parser;
import core.util.ParseException;
import core.util.Result.Err;
import core.util.Result.Ok;
import core.util.Result.Result;
import core.util.ToStringUtil;

import java.util.ArrayList;

public class CodeComponent {
    int size;
    ArrayList<localValType> localValTypes;
    Expression expr;

    CodeComponent(int size, ArrayList<localValType> localValTypes, Expression expr) {
        this.size = size;
        this.localValTypes = localValTypes;
        this.expr = expr;
    }

    public static Result<CodeComponent, ParseException> parse(Parser parser) {
        int size;
        switch (parser.nextU32()) {
            case Err(ParseException e) -> {return new Err<>(e);}
            case Ok(Integer i) -> size = i;
        }
        int beforeIndex = parser.getIndex();
        ArrayList<localValType> localValTypes;
        switch (parser.nextVector(localValType::parse)) {
            case Err(ParseException e) -> {return new Err<>(e);}
            case Ok(ArrayList<localValType> localValTypes_) -> localValTypes = localValTypes_;
        }
        Expression expr;
        switch (Expression.parse(parser)) {
            case Err(ParseException e) -> {return new Err<>(e);}
            case Ok(Expression expr_) -> expr = expr_;
        }
        return parser
            .checkLength(beforeIndex, size)
            .map(ignored -> new CodeComponent(size, localValTypes, expr));
    }

    public String toString() {
        return (
            "CodeComponent(\n"
            + (
                "size=" + this.size
                + "\nlocalValTypes=" + ToStringUtil.arrayList(this.localValTypes)
                + "\nexpr=" + this.expr
            ).indent(2)
            + ')'
        );
    }
}
