package core.AST.Type;

import core.Parser;
import core.util.ParseException;
import core.util.Result.Result;

public class MemoryType {
    private final Limit limit;

    MemoryType(Limit limit) {
        this.limit = limit;
    }

    public static Result<MemoryType, ParseException> parse(Parser parser) {
        return Limit.parse(parser).map(MemoryType::new);
    }

    public String toString() {
        return String.format("MemoryType(limit=%s)", this.limit);
    }
}
