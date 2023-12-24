package core.AST.Component;

import core.AST.Expression;
import core.Parser;
import core.util.ParseException;
import core.util.Result.Err;
import core.util.Result.Ok;
import core.util.Result.Result;

import java.util.ArrayList;

public class ElementsComponent {
    int tableIdx;
    Expression expr;
    ArrayList<Integer> funcIdx;

    ElementsComponent(int tableIdx, Expression expr, ArrayList<Integer> funcIdx) {
        this.tableIdx = tableIdx;
        this.expr = expr;
        this.funcIdx = funcIdx;
    }

    public static Result<ElementsComponent, ParseException> parse(Parser parser) {
        int tableIdx;
        switch (parser.nextU32()) {
            case Err(ParseException e) -> {return new Err<>(e);}
            case Ok(Integer idx) -> tableIdx = idx;
        }
        Expression expr;
        switch (Expression.parse(parser)) {
            case Err(ParseException e) -> {return new Err<>(e);}
            case Ok(Expression expr_) -> expr = expr_;
        }
        return parser
            .nextVector(Parser::nextU32)
            .map(idxes -> new ElementsComponent(tableIdx, expr, idxes));
    }
}
