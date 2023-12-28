package core.AST.Component;

import core.Parser;
import core.util.InvalidIndexException;
import core.util.ParseException;
import core.util.Result.Err;
import core.util.Result.Ok;
import core.util.Result.Result;

public sealed interface ImportComponentBase
    permits ImportFunctionComponent, ImportTableComponent, ImportMemoryComponent, ImportGlobalComponent {
    static Result<ImportComponentBase, ParseException> parse(Parser parser) {
        String mod, name;
        switch (parser.nextName()) {
            case Err(ParseException e) -> {return new Err<>(e);}
            case Ok(String mod_) -> mod = mod_;
        }
        switch (parser.nextName()) {
            case Err(ParseException e) -> {return new Err<>(e);}
            case Ok(String name_) -> name = name_;
        }

        if (parser.nextByte((byte) 0x00).isOk()) {
            return ImportFunctionComponent.parseComponent(mod, name, parser);
        } else if (parser.nextByte((byte) 0x01).isOk()) {
            return ImportTableComponent.parseComponent(mod, name, parser);
        } else if (parser.nextByte((byte) 0x02).isOk()) {
            return ImportMemoryComponent.parseComponent(mod, name, parser);
        } else if (parser.nextByte((byte) 0x03).isOk()) {
            return ImportGlobalComponent.parseComponent(mod, name, parser);
        } else {
            return new Err<>(switch (parser.peek()) {
                case Err(InvalidIndexException e) -> e.into();
                case Ok(Byte b) -> new ParseException(String.format(
                    "Invalid Byte(expect=0x00 ~ 0x02, got=0x%x",
                    b
                ));
            });
        }
    }
}
