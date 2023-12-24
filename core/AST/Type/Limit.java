package core.AST.Type;

import core.Parser;
import core.util.ParseException;
import core.util.Result.Err;
import core.util.Result.Ok;
import core.util.Result.Result;

public class Limit {
    private final int uMin, uMax;

    Limit(int uMin, int uMax) {
        this.uMin = uMin;
        this.uMax = uMax;
    }

    public static Result<Limit, ParseException> parse(Parser parser) {
        boolean hasMax;
        if (parser
            .nextByte((byte) 0x00)
            .isOk()) {
            hasMax = false;
        } else if (parser.nextByte((byte) 0x01) instanceof Err(ParseException e)) {
            return new Err<>(e);
        } else {
            hasMax = true;
        }
        int uMin, uMax;
        switch (parser.nextU32()) {
            case Err(ParseException e) -> {return new Err<>(e);}
            case Ok(Integer uMin_) -> uMin = uMin_;
        }
        if (hasMax) {
            switch (parser.nextU32()) {
                case Err(ParseException e) -> {return new Err<>(e);}
                case Ok(Integer uMax_) -> uMax = uMax_;
            }
        } else {
            uMax = -1;
        }
        return new Ok<>(new Limit(uMin, uMax));
    }

    public String toString() {
        return "Limit(uMin=" + this.uMin + ", uMax=" + this.uMax + ')';
    }
}
