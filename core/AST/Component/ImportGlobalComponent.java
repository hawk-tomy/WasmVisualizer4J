package core.AST.Component;

import core.AST.Type.GlobalType;
import core.Parser;
import core.util.ParseException;
import core.util.Result.Err;
import core.util.Result.Ok;
import core.util.Result.Result;

public final class ImportGlobalComponent implements ImportComponentBase {
    private final String mod, name;
    private final GlobalType gt;

    ImportGlobalComponent(String mod, String name, GlobalType gt) {
        this.mod = mod;
        this.name = name;
        this.gt = gt;
    }

    public static Result<ImportComponentBase, ParseException> parseComponent(String mod, String name, Parser parser) {
        return switch (core.AST.Type.GlobalType.parse(parser)) {
            case Err(ParseException e) -> new Err<>(e);
            case Ok(GlobalType gt) -> new Ok<>(new ImportGlobalComponent(mod, name, gt));
        };
    }

    public String toString() {
        return String.format(
            "ImportGlobalComponent(mod='%s', name='%s', gt=%s)", this.mod, this.name, this.gt.toString());
    }
}
