package core.AST.Type;

import core.Parser;
import core.util.Option.None;
import core.util.Option.Option;
import core.util.Option.Some;
import core.util.ParseException;
import core.util.Result.Ok;
import core.util.Result.Result;

public class BlockType {
    Option<ValueType> t;

    BlockType(Option<ValueType> t) {
        this.t = t;
    }

    public static Result<BlockType, ParseException> parse(Parser parser) {
        if (parser
            .nextByte((byte) 0x40)
            .isOk()) {
            return new Ok<>(new BlockType(new None<>()));
        }
        return ValueType
            .parse(parser)
            .map(t -> new BlockType(new Some<>(t)));
    }

    public String toString() {
        return "BlockType(t=" + this.t + ')';
    }
}
