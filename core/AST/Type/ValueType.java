package core.AST.Type;

import core.Parser;
import core.util.InvalidIndexException;
import core.util.ParseException;
import core.util.Result.Err;
import core.util.Result.Ok;
import core.util.Result.Result;

public enum ValueType {
    I32((byte) 0x7F), I64((byte) 0x7E), F32((byte) 0x7D), F64((byte) 0x7C);

    ValueType(byte id) {
        if (id >> 2 != 0x1f) {
            // if 0x7C <= id <= 0x7F
            throw new Error("invalid id. (unreachable)");
        }
    }

    static public Result<ValueType, ParseException> parse(Parser parser) {
        byte id;
        switch (parser.next()) {
            case Err(InvalidIndexException e) -> {return new Err<>(e.into());}
            case Ok(Byte id_) -> id = id_;
        }
        return switch (id) {
            case 0x7F -> new Ok<>(ValueType.I32);
            case 0x7E -> new Ok<>(ValueType.I64);
            case 0x7D -> new Ok<>(ValueType.F32);
            case 0x7C -> new Ok<>(ValueType.F64);
            default -> new Err<>(new ParseException(String.format("Invalid Type ID(except Value Type, got=0x%x)", id)));
        };
    }
}
