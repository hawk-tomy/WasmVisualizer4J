package core.AST;

import core.AST.Section.BaseSection;
import core.AST.Section.CodeSection;
import core.AST.Section.CustomSection;
import core.AST.Section.DataSection;
import core.AST.Section.ElementSection;
import core.AST.Section.ExportSection;
import core.AST.Section.FunctionSection;
import core.AST.Section.GlobalSection;
import core.AST.Section.ImportSection;
import core.AST.Section.MemorySection;
import core.AST.Section.StartSection;
import core.AST.Section.TableSection;
import core.AST.Section.TypeSection;
import core.Parser;
import core.util.Option.None;
import core.util.Option.Option;
import core.util.Option.Some;
import core.util.ParseException;
import core.util.Result.Err;
import core.util.Result.Ok;
import core.util.Result.Result;

import java.util.ArrayList;
import java.util.function.BiFunction;

public final class Module {

    ArrayList<CustomSection> customSections;
    ArrayList<TypeSection> types;
    ArrayList<ImportSection> imports;
    ArrayList<FunctionSection> functions;
    ArrayList<TableSection> tables;
    ArrayList<MemorySection> memories;
    ArrayList<GlobalSection> globals;
    ArrayList<ExportSection> exports;
    Option<StartSection> starts;
    ArrayList<ElementSection> elements;
    ArrayList<CodeSection> codes;
    ArrayList<DataSection> data;

    Module(
        ArrayList<CustomSection> customSections,
        ArrayList<TypeSection> types,
        ArrayList<ImportSection> imports,
        ArrayList<FunctionSection> functions,
        ArrayList<TableSection> tables,
        ArrayList<MemorySection> memories,
        ArrayList<GlobalSection> globals,
        ArrayList<ExportSection> exports,
        Option<StartSection> starts,
        ArrayList<ElementSection> elements,
        ArrayList<CodeSection> codes,
        ArrayList<DataSection> data
    ) {
        this.customSections = customSections;
        this.types = types;
        this.imports = imports;
        this.functions = functions;
        this.tables = tables;
        this.memories = memories;
        this.globals = globals;
        this.exports = exports;
        this.starts = starts;
        this.elements = elements;
        this.codes = codes;
        this.data = data;
    }

    public static Result<Module, ParseException> parse(Parser parser) {
        // check magic byte.
        if (!(
            parser
                .nextByte((byte) 0x00)
                .and(parser.nextByte((byte) 0x61))
                .and(parser.nextByte((byte) 0x73))
                .and(parser.nextByte((byte) 0x6D))
                .isOk()
        )) {
            return new Err<>(new ParseException("not wasm file. (magic byte is miss match)"));
        }
        // check version.
        if (!(
            parser
                .nextByte((byte) 0x01)
                .and(parser.nextByte((byte) 0x00))
                .and(parser.nextByte((byte) 0x00))
                .and(parser.nextByte((byte) 0x00))
                .isOk()
        )) {
            return new Err<>(new ParseException("invalid version(not supported)"));
        }
        ArrayList<CustomSection> customSections = Module.parseSequence((byte) 0x00,
            CustomSection::parse, parser
        );

        ArrayList<TypeSection> types = Module.parseSequence((byte) 0x01, TypeSection::parse, parser);
        customSections.addAll(Module.parseSequence((byte) 0x00, CustomSection::parse, parser));

        ArrayList<ImportSection> imports = Module.parseSequence((byte) 0x02, ImportSection::parse,
            parser
        );
        customSections.addAll(Module.parseSequence((byte) 0x00, CustomSection::parse, parser));

        ArrayList<FunctionSection> functions = Module.parseSequence((byte) 0x03, FunctionSection::parse,
            parser
        );
        customSections.addAll(Module.parseSequence((byte) 0x00, CustomSection::parse, parser));

        ArrayList<TableSection> tables = Module.parseSequence((byte) 0x04, TableSection::parse, parser);
        customSections.addAll(Module.parseSequence((byte) 0x00, CustomSection::parse, parser));

        ArrayList<MemorySection> memories = Module.parseSequence((byte) 0x05, MemorySection::parse,
            parser
        );
        customSections.addAll(Module.parseSequence((byte) 0x00, CustomSection::parse, parser));

        ArrayList<GlobalSection> globals = Module.parseSequence((byte) 0x06, GlobalSection::parse,
            parser
        );
        customSections.addAll(Module.parseSequence((byte) 0x00, CustomSection::parse, parser));

        ArrayList<ExportSection> exports = Module.parseSequence((byte) 0x07, ExportSection::parse,
            parser
        );
        customSections.addAll(Module.parseSequence((byte) 0x00, CustomSection::parse, parser));

        Option<StartSection> starts = Module.parseOptional((byte) 0x08, StartSection::parse, parser);
        customSections.addAll(Module.parseSequence((byte) 0x00, CustomSection::parse, parser));

        ArrayList<ElementSection> elements = Module.parseSequence((byte) 0x09, ElementSection::parse,
            parser
        );
        customSections.addAll(Module.parseSequence((byte) 0x00, CustomSection::parse, parser));

        ArrayList<CodeSection> codes = Module.parseSequence((byte) 0x0a, CodeSection::parse, parser);
        customSections.addAll(Module.parseSequence((byte) 0x00, CustomSection::parse, parser));

        ArrayList<DataSection> data = Module.parseSequence((byte) 0x0b, DataSection::parse, parser);
        customSections.addAll(Module.parseSequence((byte) 0x00, CustomSection::parse, parser));

        return new Ok<>(new Module(
            customSections,
            types,
            imports,
            functions,
            tables,
            memories,
            globals,
            exports,
            starts,
            elements,
            codes,
            data
        ));
    }

    static <S extends BaseSection> ArrayList<S> parseSequence(
        byte id,
        BiFunction<Integer, Parser, Result<S, ParseException>> parse,
        Parser parser
    ) {
        ArrayList<S> sections = new ArrayList<>();
        while (true) {
            if (parser.nextSection(id, parse) instanceof Some(Result<S, ParseException> ret)) {
                if (ret instanceof Ok(S s)) {
                    sections.add(s);
                    continue;
                }
            }
            break;
        }
        return sections;
    }

    static <S extends BaseSection> Option<S> parseOptional(
        byte id,
        BiFunction<Integer, Parser, Result<S, ParseException>> parse,
        Parser parser
    ) {
        return switch (parser.nextSection(id, parse)) {
            case Some(Result<S, ParseException> ret) -> switch (ret) {
                case Err(ParseException ignored) -> new None<>();
                case Ok(S s) -> new Some<>(s);
            };
            case None() -> new None<>();
        };
    }
}
