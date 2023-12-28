package core.AST.Component;

import core.AST.Expression;
import core.AST.Type.GlobalType;
import core.Parser;
import core.util.ParseException;
import core.util.Result.Err;
import core.util.Result.Ok;
import core.util.Result.Result;

public class GlobalsComponent {
    private final GlobalType gt;
    private final Expression expr;

    GlobalsComponent(GlobalType gt, Expression expr) {
        this.gt = gt;
        this.expr = expr;
    }

    public static Result<GlobalsComponent, ParseException> parse(Parser parser) {
        GlobalType gt;
        switch (GlobalType.parse(parser)) {
            case Err(ParseException e) -> {return new Err<>(e);}
            case Ok(GlobalType gt_) -> gt = gt_;
        }
        return Expression.parse(parser).map(expr -> new GlobalsComponent(gt, expr));
    }

    public String toString() {
        return (
            "GlobalsComponent(\n"
            + (
                "gt=" + this.gt
                + "\nexpr=" + this.expr
            ).indent(2)
            + ')'
        );
    }
}
