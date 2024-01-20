package core.AST.Component;

import core.AST.Type.ValueType;
import core.Parser;
import core.util.ParseException;
import core.util.Result.Err;
import core.util.Result.Ok;
import core.util.Result.Result;

public record localValType(int count, ValueType valType) {
    public static Result<localValType, ParseException> parse(Parser parser) {
        int count;
        switch (parser.nextU32()) {
            case Err(ParseException e) -> {return new Err<>(e);}
            case Ok(Integer count_) -> count = count_;
        }
        return ValueType.parse(parser).map(valueType -> new localValType(count, valueType));
    }

    public String content() {
        String vt = this.valType.toString().toLowerCase();
        StringBuilder sb = new StringBuilder();
        sb.append(vt);
        for (int i = 0; i < this.count - 1; i++) {
            sb.append(", ");
            sb.append(vt);
        }
        return sb.toString();
    }
}
