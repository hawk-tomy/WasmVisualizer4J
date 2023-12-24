package core.AST.Section;

import core.AST.Component.ImportComponentBase;
import core.Parser;
import core.util.ParseException;
import core.util.Result.Err;
import core.util.Result.Ok;
import core.util.Result.Result;

import java.util.ArrayList;

public final class ImportSection implements BaseSection {
    private final ArrayList<ImportComponentBase> imports;

    ImportSection(ArrayList<ImportComponentBase> imports) {
        this.imports = imports;
    }

    public static Result<ImportSection, ParseException> parse(int length, Parser parser) {
        return switch (parser.nextVector(ImportComponentBase::parse)) {
            case Err(ParseException e) -> new Err<>(e);
            case Ok(ArrayList<ImportComponentBase> imports) -> new Ok<>(new ImportSection(imports));
        };
    }
}
