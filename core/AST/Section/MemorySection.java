package core.AST.Section;

import core.AST.Type.MemoryType;
import core.Parser;
import core.util.ParseException;
import core.util.Result.Result;

import java.util.ArrayList;

public final class MemorySection implements BaseSection {
    public final ArrayList<MemoryType> mts;

    MemorySection(ArrayList<MemoryType> mts) {
        this.mts = mts;
    }

    public static Result<MemorySection, ParseException> parse(int ignoredLength, Parser parser) {
        return parser.nextVector(MemoryType::parse).map(MemorySection::new);
    }

    public String toString() {
        return "MemorySection(mts=" + this.mts + ')';
    }
}
