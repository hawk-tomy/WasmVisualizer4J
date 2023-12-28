package core.AST.Component;

import core.AST.Type.MemoryType;
import core.Parser;
import core.util.ParseException;
import core.util.Result.Result;

public final class ImportMemoryComponent implements ImportComponentBase {
    private final String mod, name;
    MemoryType mt;

    ImportMemoryComponent(String mod, String name, MemoryType mt) {
        this.mod = mod;
        this.name = name;
        this.mt = mt;
    }

    public static Result<ImportComponentBase, ParseException> parseComponent(String mod, String name, Parser parser) {
        return MemoryType.parse(parser).map(mt -> new ImportMemoryComponent(mod, name, mt));
    }

    public String toString() {
        return String.format(
            "ImportMemoryComponent(mod='%s', name='%s', mt=%s)", this.mod, this.name, this.mt.toString());
    }
}
