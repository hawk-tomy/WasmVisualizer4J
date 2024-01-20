package core.AST.Section;

import core.AST.Component.ExportsComponent;
import core.Parser;
import core.util.ParseException;
import core.util.Result.Result;
import core.util.ToStringUtil;

import java.util.ArrayList;

public final class ExportSection implements BaseSection {
    final ArrayList<ExportsComponent> exports;

    ExportSection(ArrayList<ExportsComponent> exports) {
        this.exports = exports;
    }

    public static Result<ExportSection, ParseException> parse(int ignoredLength, Parser parser) {
        return parser.nextVector(ExportsComponent::parse).map(ExportSection::new);
    }

    public String toString() {
        return (
            "ExportSection(\n"
            + (
                "exports="
                + ToStringUtil.arrayList(this.exports)
            ).indent(2)
            + ')'
        );
    }
}
