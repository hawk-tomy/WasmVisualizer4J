package core.AST.Section;

import core.Parser;
import core.util.ParseException;
import core.util.Result.Result;

public final class StartSection implements BaseSection {
    final int uIdx;

    StartSection(int uIdx) {
        this.uIdx = uIdx;
    }

    public static Result<StartSection, ParseException> parse(int ignoredLength, Parser parser) {
        return parser.nextU32().map(StartSection::new);
    }

    public String toString() {
        return "StartSection(uIdx=" + this.uIdx + ')';
    }
}
