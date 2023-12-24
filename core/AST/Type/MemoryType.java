package core.AST.Type;

import core.Parser;
import core.util.ParseException;
import core.util.Result.Err;
import core.util.Result.Ok;
import core.util.Result.Result;

public class MemoryType {
    private final Limit limit;

    MemoryType(Limit limit) {
        this.limit = limit;
    }

    public static Result<MemoryType, ParseException> parse(Parser parser) {
        return switch (Limit.parse(parser)) {
            case Err(ParseException e) -> new Err<>(e);
            case Ok(Limit limit) -> new Ok<>(new MemoryType(limit));
        };
    }
}
