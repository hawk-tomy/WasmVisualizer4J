package core.AST.Section;

import core.Parser;
import core.util.ParseException;
import core.util.Result.Result;

public final class StartSection implements BaseSection {
    int uIdx;

    StartSection(int uIdx) {
        this.uIdx = uIdx;
    }

    public static Result<StartSection, ParseException> parse(int length, Parser parser) {
        return parser
            .nextU32()
            .map(StartSection::new);
    }
}
