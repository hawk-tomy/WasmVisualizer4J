package core.AST.Section;

import core.AST.Component.CodeComponent;
import core.Parser;
import core.util.ParseException;
import core.util.Result.Result;
import core.util.ToStringUtil;

import java.util.ArrayList;

public final class CodeSection implements BaseSection {
    public final ArrayList<CodeComponent> code;

    CodeSection(ArrayList<CodeComponent> code) {
        this.code = code;
    }

    public static Result<CodeSection, ParseException> parse(int ignoredLength, Parser parser) {
        return parser.nextVector(CodeComponent::parse).map(CodeSection::new);
    }

    public String toString() {
        return (
            "CodeSection(\n"
            + (
                "code=" + ToStringUtil.arrayList(this.code)
            ).indent(2)
            + ')'
        );
    }
}
