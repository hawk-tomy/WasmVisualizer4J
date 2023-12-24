package core.AST.Component;

import core.Parser;
import core.util.ParseException;
import core.util.Result.Err;
import core.util.Result.Ok;
import core.util.Result.Result;

public final class ImportFunctionComponent implements ImportComponentBase {
    private final String mod, name;
    int uIdx;

    ImportFunctionComponent(String mod, String name, int uIdx) {
        this.mod = mod;
        this.name = name;
        this.uIdx = uIdx;
    }

    public static Result<ImportComponentBase, ParseException> parseComponent(String mod, String name, Parser parser) {
        return switch (parser.nextU32()) {
            case Err(ParseException e) -> new Err<>(e);
            case Ok(Integer i) -> new Ok<>(new ImportFunctionComponent(mod, name, i));
        };
    }

    public String toString() {
        return String.format("ImportFunctionComponent(mod='%s', name='%s', idx=%d)", this.mod, this.name, this.uIdx);
    }
}
