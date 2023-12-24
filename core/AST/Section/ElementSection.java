package core.AST.Section;

import core.AST.Component.ElementsComponent;
import core.Parser;
import core.util.ParseException;
import core.util.Result.Result;

import java.util.ArrayList;

public final class ElementSection implements BaseSection {
    ArrayList<ElementsComponent> elements;

    ElementSection(ArrayList<ElementsComponent> elements) {
        this.elements = elements;
    }

    public static Result<ElementSection, ParseException> parse(int length, Parser parser) {
        return parser
            .nextVector(ElementsComponent::parse)
            .map(ElementSection::new);
    }
}
