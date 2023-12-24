package core.AST.Section;

import core.AST.Component.ExportsComponent;
import core.Parser;
import core.util.ParseException;
import core.util.Result.Result;

import java.util.ArrayList;

public final class ExportSection implements BaseSection {
    ArrayList<ExportsComponent> exports;

    ExportSection(ArrayList<ExportsComponent> exports) {
        this.exports = exports;
    }

    public static Result<ExportSection, ParseException> parse(int length, Parser parser) {
        return parser
            .nextVector(ExportsComponent::parse)
            .map(ExportSection::new);
    }

    public String toString() {
        return (
            "ExportSection(\n"
            + (
                "exports="
                + this.exports
            ).indent(2)
            + "\n)"
        );
    }
}