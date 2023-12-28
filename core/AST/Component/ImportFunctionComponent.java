package core.AST.Component;

import core.Parser;
import core.util.ParseException;
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
        return parser.nextU32().map(i -> new ImportFunctionComponent(mod, name, i));
    }

    public String toString() {
        return String.format("ImportFunctionComponent(mod='%s', name='%s', idx=%d)", this.mod, this.name, this.uIdx);
    }
}
