package core.AST.Component;

import core.AST.Module;
import core.AST.Type.GlobalType;
import core.Parser;
import core.util.ParseException;
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
        return GlobalType.parse(parser).map(gt -> new ImportGlobalComponent(mod, name, gt));
    }

    public String toString() {
        return String.format(
            "ImportGlobalComponent(mod='%s', name='%s', gt=%s)", this.mod, this.name, this.gt.toString());
    }

    public String title() {
        return "global val: " + this.mod + "." + this.name;
    }

    public String content(Module module) {
        return (
            this.title()
            + "\n"
            + this.gt.content()
        );
    }
}
