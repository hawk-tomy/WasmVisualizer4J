package core.AST.Component;

import core.AST.Type.TableType;
import core.Parser;
import core.util.ParseException;
import core.util.Result.Result;

public final class ImportTableComponent implements ImportComponentBase {
    private final String mod, name;
    private final TableType tt;

    ImportTableComponent(String mod, String name, TableType tt) {
        this.mod = mod;
        this.name = name;
        this.tt = tt;
    }

    public static Result<ImportComponentBase, ParseException> parseComponent(String mod, String name, Parser parser) {
        return TableType.parse(parser).map(tt -> new ImportTableComponent(mod, name, tt));
    }

    public String toString() {
        return (
            "ImportTableComponent(\n"
            + (
                "mod='" + this.mod + '\''
                + "\nname='" + this.name + '\''
                + "\ntt=" + this.tt
            ).indent(2)
            + ')'
        );
    }
}
