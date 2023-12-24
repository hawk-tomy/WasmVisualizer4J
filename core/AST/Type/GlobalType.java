package core.AST.Type;

import core.Parser;
import core.util.InvalidIndexException;
import core.util.ParseException;
import core.util.Result.Err;
import core.util.Result.Ok;
import core.util.Result.Result;

public class GlobalType {
    ValueType vt;
    boolean isMut;

    GlobalType(ValueType vt, boolean isMut) {
        this.vt = vt;
        this.isMut = isMut;
    }

    public static Result<GlobalType, ParseException> parse(Parser parser) {
        ValueType vt;
        switch (ValueType.parse(parser)) {
            case Err(ParseException e) -> {return new Err<>(e);}
            case Ok(ValueType vt_) -> vt = vt_;
        }
        boolean isMut;
        switch (parser.next()) {
            case Err(InvalidIndexException e) -> {return new Err<>(e.into());}
            case Ok(Byte b) -> isMut = b == (byte) 0x01;
        }
        return new Ok<>(new GlobalType(vt, isMut));
    }

    public String toString() {
        return "GlobalType(vt=" + this.vt + ", isMut=" + this.isMut + ')';
    }
}
