package core.AST.Section;

import core.AST.Component.GlobalsComponent;
import core.Parser;
import core.util.ParseException;
import core.util.Result.Result;
import core.util.ToStringUtil;

import java.util.ArrayList;

public final class GlobalSection implements BaseSection {
    public final ArrayList<GlobalsComponent> global;

    GlobalSection(ArrayList<GlobalsComponent> global) {
        this.global = global;
    }

    public static Result<GlobalSection, ParseException> parse(int ignoredLength, Parser parser) {
        return parser.nextVector(GlobalsComponent::parse).map(GlobalSection::new);
    }

    public String toString() {
        return (
            "GlobalSection(\n"
            + (
                "global="
                + ToStringUtil.arrayList(this.global)
            ).indent(2)
            + ')'
        );
    }
}