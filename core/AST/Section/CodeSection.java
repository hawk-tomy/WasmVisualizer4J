package core.AST.Section;

import core.AST.Component.CodeComponent;
import core.Parser;
import core.util.ParseException;
import core.util.Result.Result;

import java.util.ArrayList;

public final class CodeSection implements BaseSection {
    ArrayList<CodeComponent> code;

    CodeSection(ArrayList<CodeComponent> code) {
        this.code = code;
    }

    public static Result<CodeSection, ParseException> parse(int length, Parser parser) {
        return parser
            .nextVector(CodeComponent::parse)
            .map(CodeSection::new);
    }
}
