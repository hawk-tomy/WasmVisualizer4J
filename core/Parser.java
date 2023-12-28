package core;

import core.AST.Module;
import core.AST.Section.BaseSection;
import core.parser.LEB128Parser;
import core.util.InvalidIndexException;
import core.util.Option.None;
import core.util.Option.Option;
import core.util.Option.Some;
import core.util.ParseException;
import core.util.Result.Err;
import core.util.Result.Ok;
import core.util.Result.Result;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * note: u** fields are only allow unsigned integer.
 */
public class Parser {
    private final byte[] wasm;
    private final LEB128Parser integerP;
    private Integer uIndex;

    public Parser(byte[] wasm) {
        this.wasm = wasm;
        this.uIndex = 0;
        this.integerP = new LEB128Parser(this);
    }

    public Integer getIndex() {
        return this.uIndex;
    }

    public Result<Module, ParseException> parse() {
        return Module.parse(this);
    }

    public Result<Byte, InvalidIndexException> peek() {
        if (this.wasm.length <= this.uIndex) {
            return new Err<>(new InvalidIndexException());
        }
        return new Ok<>(this.wasm[this.uIndex]);
    }

    public Result<Byte, InvalidIndexException> next() {
        var ret = this.peek();
        this.consume();
        return ret;
    }

    public void consume() {
        this.uIndex++;
    }

    public Byte takeByte(byte b, int start) {
        return this.takeByte(b, start, 1);
    }

    /**
     * e.g. this.take((byte)0x01, 0) -> (byte)0x01 this.take((byte)0x10, 5) -> (byte)0x01
     * this.take((byte)0b00000110, 1, 2) -> (byte)0b11 start: 0 <= && < 8 count: 1 <= && <= 8 - start
     * return the Byte.
     */
    public Byte takeByte(byte b, int start, int count) {
        if (start < 0 || 8 <= start || count < 0 || 8 < (start + count)) {
            throw new Error("Invalid argument");
        }
        return (byte) ((b >> start) & ((1 << count) - 1));
    }

    public Result<Void, ParseException> nextByte(byte id) {
        return switch (this.peek()) {
            case Err(InvalidIndexException ignored) ->
                new Err<>(new ParseException(String.format("Invalid Byte(expect=0x%x,but got=EOF", id)));
            case Ok(Byte b) -> {
                if (b == id) {
                    this.consume();
                    yield new Ok<>(null);
                } else {
                    yield new Err<>(new ParseException(String.format("Invalid Byte(expect=0x%x,but got=0x%x)", id, b)));
                }
            }
        };
    }

    public Result<ArrayList<Byte>, ParseException> nextBytes(int count) {
        ArrayList<Byte> bytes = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            switch (this.next()) {
                case Err(InvalidIndexException e) -> {return new Err<>(e.into());}
                case Ok(Byte b) -> bytes.add(b);
            }
        }
        return new Ok<>(bytes);
    }

    public Result<Boolean, ParseException> nextBoolean() {
        return switch (this.next()) {
            case Err(InvalidIndexException e) -> new Err<>(e.into());
            case Ok(Byte b) -> switch (b) {
                case 0x00 -> new Ok<>(false);
                case 0x01 -> new Ok<>(true);
                default -> new Err<>(new ParseException(String.format(
                    "Invalid Byte(expect=0x00 or 0x01,but got=0x%x)",
                    b
                )));
            };
        };
    }

    public Result<Integer, ParseException> nextU32() {return this.integerP.nextU32();}

    public Result<Integer, ParseException> nextI32() {return this.integerP.nextI32();}

    public Result<Long, ParseException> nextI64() {return this.integerP.nextI64();}

    /**
     * spec 5.5.2 section_N(B) like method.
     * if correct section, return Some<S>. otherwise, return None.
     *
     * @param id    section id.(N)
     * @param parse (N, this) -> S(B)
     * @param <S>   Section.
     * @return parsed section.
     */
    public <S extends BaseSection> Option<Result<S, ParseException>> nextSection(
        byte id,
        BiFunction<Integer, Parser, Result<S, ParseException>> parse
    ) {
        if (this.nextByte(id).isErr()) {
            return new None<>(); // not correct section.
        }
        // get content length;
        int length;
        switch (this.nextU32()) {
            case Err(ParseException e) -> {return new Some<>(new Err<>(e));}
            case Ok(Integer i) -> length = i;
        }
        int beforeIndex = this.getIndex();
        Result<S, ParseException> ret = parse.apply(length, this);
        if (ret.isErr()) {
            return new Some<>(ret);
        }
        return new Some<>(
            this.checkLength(beforeIndex, length).and(ret)
        );
    }

    public <T> Result<ArrayList<T>, ParseException> nextVector(Function<Parser, Result<T, ParseException>> parse) {
        int uCount;
        switch (this.nextU32()) {
            case Err(ParseException e) -> {return new Err<>(e);}
            case Ok(Integer i) -> uCount = i;
        }
        ArrayList<T> rst = new ArrayList<>();
        for (int i = 0; Integer.compareUnsigned(i, uCount) < 0; i++) {
            switch (parse.apply(this)) {
                case Err(ParseException e) -> {return new Err<>(e);}
                case Ok(T v) -> rst.add(v);
            }
        }
        return new Ok<>(rst);
    }

    /**
     * like: while (condition()) { parse() }
     *
     * @param condition call parse while this is true.
     * @param parse     parser.
     * @param <T>       return value Type of parse.
     * @return if err return it, else return list of T
     */
    public <T> Result<ArrayList<T>, ParseException> nextSequence(
        Predicate<Parser> condition,
        Function<Parser, Result<T, ParseException>> parse
    ) {
        ArrayList<T> rets = new ArrayList<>();
        while (condition.test(this)) {
            switch (parse.apply(this)) {
                case Err(ParseException e) -> {return new Err<>(e);}
                case Ok(T ret) -> rets.add(ret);
            }
        }
        return new Ok<>(rets);
    }

    public Result<String, ParseException> nextName() {
        // b*: Vector<byte> -> name: if utf(name) == b*
        ArrayList<Byte> bytes;
        // get string bytes. parse: get byte and error convert to correct type.
        switch (this.nextVector(
            p -> p.next().mapErr(InvalidIndexException::into)
        )) {
            case Err(ParseException e) -> {return new Err<>(e);}
            case Ok(ArrayList<Byte> bytes_) -> bytes = bytes_;
        }
        byte[] array = new byte[bytes.size()];
        for (int i = 0; i < bytes.size(); i++) {array[i] = bytes.get(i);}
        return new Ok<>(new String(array, StandardCharsets.UTF_8));
    }

    public Result<Void, ParseException> checkLength(int beforeIndex, int length) {
        int realLength = this.getIndex() - beforeIndex;
        int sign = realLength - length;
        return (
            sign == 0
            ? new Ok<>(null)
            : new Err<>(new ParseException(String.format(
                "Content Size is %s than expect. (expect=%d, got=%d)",
                sign < 0 ? "less" : "greater",
                length,
                realLength
            )))
        );
    }
}
