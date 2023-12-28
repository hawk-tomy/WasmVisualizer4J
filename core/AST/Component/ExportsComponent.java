package core.AST.Component;

import core.Parser;
import core.util.InvalidIndexException;
import core.util.ParseException;
import core.util.Result.Err;
import core.util.Result.Ok;
import core.util.Result.Result;
import core.util.UnsignedByteOp;

public class ExportsComponent {
    String name;
    byte type;
    int idx;

    ExportsComponent(String name, byte type, int idx) {
        this.name = name;
        this.type = type;
        this.idx = idx;
    }

    public static Result<ExportsComponent, ParseException> parse(Parser parser) {
        String name;
        switch (parser.nextName()) {
            case Err(ParseException e) -> {return new Err<>(e);}
            case Ok(String name_) -> name = name_;
        }
        return switch (parser.next()) {
            case Err(InvalidIndexException e) -> new Err<>(e.into());
            case Ok(Byte b) when UnsignedByteOp.isInRange((byte) 0x00, b, (byte) 0x04) -> parser
                .nextU32()
                .map(idx -> new ExportsComponent(name, b, idx));
            case Ok(Byte ignored) -> new Err<>(new ParseException("Unknown Export Type."));
        };
    }

    public String toString() {
        return String.format("ExportsComponent(name='%s', type=0x%X, idx=%d)", this.name, this.type, this.idx);
    }
}
