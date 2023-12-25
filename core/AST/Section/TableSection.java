package core.AST.Section;

import core.AST.Type.TableType;
import core.Parser;
import core.util.ParseException;
import core.util.Result.Result;
import core.util.ToStringUtil;

import java.util.ArrayList;

public final class TableSection implements BaseSection {
    ArrayList<TableType> tts;

    TableSection(ArrayList<TableType> tts) {
        this.tts = tts;
    }

    public static Result<TableSection, ParseException> parse(int length, Parser parser) {
        return parser
            .nextVector(TableType::parse)
            .map(TableSection::new);
    }

    public String toString() {
        return (
            "TableSection(\n"
            + (
                "tts="
                + ToStringUtil.arrayList(this.tts)
            ).indent(2)
            + ")"
        );
    }
}
