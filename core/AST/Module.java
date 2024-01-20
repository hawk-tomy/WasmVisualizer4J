package core.AST;

import core.AST.Component.CodeComponent;
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
import core.AST.Type.FunctionType;
import core.Parser;
import core.util.Option.None;
import core.util.Option.Option;
import core.util.Option.Some;
import core.util.ParseException;
import core.util.Result.Err;
import core.util.Result.Ok;
import core.util.Result.Result;
import core.util.ToStringUtil;

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
        // check magic byte(0x0061736D).
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
        // check version. only 0x01000000
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

        try {
            var customSections = Module.parseSequence((byte) 0x00, CustomSection::parse, parser).unwrap();

            var types = Module.parseSequence((byte) 0x01, TypeSection::parse, parser).unwrap();
            Module.parseSequence((byte) 0x00, CustomSection::parse, parser).map(customSections::addAll).unwrap();

            var imports = Module.parseSequence((byte) 0x02, ImportSection::parse, parser).unwrap();
            Module.parseSequence((byte) 0x00, CustomSection::parse, parser).map(customSections::addAll).unwrap();

            var functions = Module.parseSequence((byte) 0x03, FunctionSection::parse, parser).unwrap();
            Module.parseSequence((byte) 0x00, CustomSection::parse, parser).map(customSections::addAll).unwrap();

            var tables = Module.parseSequence((byte) 0x04, TableSection::parse, parser).unwrap();
            Module.parseSequence((byte) 0x00, CustomSection::parse, parser).map(customSections::addAll).unwrap();

            var memories = Module.parseSequence((byte) 0x05, MemorySection::parse, parser).unwrap();
            Module.parseSequence((byte) 0x00, CustomSection::parse, parser).map(customSections::addAll).unwrap();

            var globals = Module.parseSequence((byte) 0x06, GlobalSection::parse, parser).unwrap();
            Module.parseSequence((byte) 0x00, CustomSection::parse, parser).map(customSections::addAll).unwrap();

            var exports = Module.parseSequence((byte) 0x07, ExportSection::parse, parser).unwrap();
            Module.parseSequence((byte) 0x00, CustomSection::parse, parser).map(customSections::addAll).unwrap();

            Option<StartSection> starts = switch (parser.nextSection((byte) 0x08, StartSection::parse)) {
                case Some(Result<StartSection, ParseException> ret) -> new Some<>(ret.unwrap());
                case None<Result<StartSection, ParseException>> ignored -> new None<>();
            };
            Module.parseSequence((byte) 0x00, CustomSection::parse, parser).map(customSections::addAll).unwrap();

            var elements = Module.parseSequence((byte) 0x09, ElementSection::parse, parser).unwrap();
            Module.parseSequence((byte) 0x00, CustomSection::parse, parser).map(customSections::addAll).unwrap();

            var codes = Module.parseSequence((byte) 0x0a, CodeSection::parse, parser).unwrap();
            Module.parseSequence((byte) 0x00, CustomSection::parse, parser).map(customSections::addAll).unwrap();

            var data = Module.parseSequence((byte) 0x0b, DataSection::parse, parser).unwrap();
            Module.parseSequence((byte) 0x00, CustomSection::parse, parser).map(customSections::addAll).unwrap();

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
        } catch (ParseException e) {
            return new Err<>(e);
        }
    }

    static <S extends BaseSection> Result<ArrayList<S>, ParseException> parseSequence(
        byte id,
        BiFunction<Integer, Parser, Result<S, ParseException>> parse,
        Parser parser
    ) {
        ArrayList<S> sections = new ArrayList<>();
        while (true) {
            if (parser.nextSection(id, parse) instanceof Some(Result<S, ParseException> ret)) {
                switch (ret) {
                    case Err(ParseException e) -> {return new Err<>(e);}
                    case Ok(S s) -> sections.add(s);
                }
            } else {
                return new Ok<>(sections);
            }
        }
    }

    public String toString() {
        return (
            "Module(\n"
            + (
                "customSections=" + ToStringUtil.arrayList(this.customSections)
                + "\ntypes=" + ToStringUtil.arrayList(this.types)
                + "\nimports=" + ToStringUtil.arrayList(this.imports)
                + "\nfunctions=" + ToStringUtil.arrayList(this.functions)
                + "\ntables=" + ToStringUtil.arrayList(this.tables)
                + "\nmemories=" + ToStringUtil.arrayList(this.memories)
                + "\nglobals=" + ToStringUtil.arrayList(this.globals)
                + "\nexports=" + ToStringUtil.arrayList(this.exports)
                + "\nstarts=" + this.starts
                + "\nelements=" + ToStringUtil.arrayList(this.elements)
                + "\ncodes=" + ToStringUtil.arrayList(this.codes)
                + "\ndata=" + ToStringUtil.arrayList(this.data)
            ).indent(2)
            + ')'
        );
    }

    public Option<FunctionType> getFuncType(int idx) {
        for (TypeSection ts : this.types) {
            if (idx >= ts.types.size()) {
                idx -= ts.types.size();
            } else {
                return new Some<>(ts.types.get(idx));
            }
        }
        return new None<>();
    }

    public Option<CodeComponent> getCodeType(int idx) {
        for (CodeSection cs : this.codes) {
            if (idx >= cs.code.size()) {
                idx -= cs.code.size();
            } else {
                return new Some<>(cs.code.get(idx));
            }
        }
        return new None<>();
    }
}
