package core.AST.Section;

import core.AST.Component.ImportComponentBase;
import core.Parser;
import core.util.ParseException;
import core.util.Result.Result;
import core.util.ToStringUtil;

import java.util.ArrayList;

public final class ImportSection implements BaseSection {
    private final ArrayList<ImportComponentBase> imports;

    ImportSection(ArrayList<ImportComponentBase> imports) {
        this.imports = imports;
    }

    public static Result<ImportSection, ParseException> parse(int length, Parser parser) {
        return parser.nextVector(ImportComponentBase::parse).map(ImportSection::new);
    }

    public String toString() {
        return (
            "ImportSection(\n"
            + (
                "imports="
                + ToStringUtil.arrayList(this.imports)
            ).indent(2)
            + ')'
        );
    }
}
