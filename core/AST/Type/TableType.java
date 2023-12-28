package core.AST.Type;

import core.Parser;
import core.util.ParseException;
import core.util.Result.Err;
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
        return Limit.parse(parser).map(TableType::new);
    }

    public String toString() {
        return String.format("TableType(limit=%s)", this.limit);
    }
}
