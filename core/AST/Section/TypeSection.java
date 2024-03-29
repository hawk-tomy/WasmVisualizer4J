package core.AST.Section;

import core.AST.Type.FunctionType;
import core.Parser;
import core.util.ParseException;
import core.util.Result.Result;
import core.util.ToStringUtil;

import java.util.ArrayList;

public final class TypeSection implements BaseSection {
    public final ArrayList<FunctionType> types;

    TypeSection(ArrayList<FunctionType> types) {
        this.types = types;
    }

    public static Result<TypeSection, ParseException> parse(int ignoredLength, Parser parser) {
        return parser.nextVector(FunctionType::parse).map(TypeSection::new);
    }

    public String toString() {
        return (
            "TypeSection(\n"
            + (
                "types="
                + ToStringUtil.arrayList(this.types)
            ).indent(2)
            + ')'
        );
    }
}
