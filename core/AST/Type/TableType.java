package core.AST.Type;

import core.Parser;
import core.util.ParseException;
import core.util.Result.Err;
import core.util.Result.Ok;
import core.util.Result.Result;

public class TableType {
    private final Limit limit;

    TableType(Limit limit) {
        this.limit = limit;
    }

    public static Result<TableType, ParseException> parse(Parser parser) {
        if (parser.nextByte((byte) 0x70) instanceof Err(ParseException e)) {
            return new Err<>(e);
        }
        return switch (Limit.parse(parser)) {
            case Err(ParseException e) -> new Err<>(e);
            case Ok(Limit limit) -> new Ok<>(new TableType(limit));
        };
    }
}
