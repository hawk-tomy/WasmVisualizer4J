package core.AST.Section;

import core.Parser;
import core.util.ParseException;
import core.util.Result.Result;
import core.util.ToStringUtil;

import java.util.ArrayList;

public final class FunctionSection implements BaseSection {
    ArrayList<Integer> type_indexes;

    FunctionSection(ArrayList<Integer> type_indexes) {
        this.type_indexes = type_indexes;
    }

    public static Result<FunctionSection, ParseException> parse(int length, Parser parser) {
        return parser.nextVector(Parser::nextU32).map(FunctionSection::new);
    }

    public String toString() {
        return (
            "FunctionSection(\n"
            + (
                "type_indexes="
                + ToStringUtil.arrayList(this.type_indexes)
            ).indent(2)
            + ')'
        );
    }
}
