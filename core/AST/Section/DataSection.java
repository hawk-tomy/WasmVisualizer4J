package core.AST.Section;

import core.AST.Component.DataComponent;
import core.Parser;
import core.util.ParseException;
import core.util.Result.Result;

import java.util.ArrayList;

public final class DataSection implements BaseSection {
    ArrayList<DataComponent> data;

    DataSection(ArrayList<DataComponent> data) {
        this.data = data;
    }

    public static Result<DataSection, ParseException> parse(int length, Parser parser) {
        return parser
            .nextVector(DataComponent::parse)
            .map(DataSection::new);
    }
}
