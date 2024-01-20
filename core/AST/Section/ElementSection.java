package core.AST.Section;

import core.AST.Component.ElementsComponent;
import core.Parser;
import core.util.ParseException;
import core.util.Result.Result;
import core.util.ToStringUtil;

import java.util.ArrayList;

public final class ElementSection implements BaseSection {
    final ArrayList<ElementsComponent> elements;

    ElementSection(ArrayList<ElementsComponent> elements) {
        this.elements = elements;
    }

    public static Result<ElementSection, ParseException> parse(int ignoredLength, Parser parser) {
        return parser.nextVector(ElementsComponent::parse).map(ElementSection::new);
    }

    public String toString() {
        return (
            "ElementSection(\n"
            + (
                "elements="
                + ToStringUtil.arrayList(this.elements)
            ).indent(2)
            + ')'
        );
    }
}
