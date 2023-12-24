package core.parser;

import core.Parser;
import core.util.InvalidIndexException;
import core.util.ParseException;
import core.util.Result.Err;
import core.util.Result.Ok;
import core.util.Result.Result;

import java.util.ArrayList;

public class LEB128Parser {
    private final Parser parser;

    public LEB128Parser(Parser parser) {
        this.parser = parser;
    }
    // parse LEB128 u64/i64 -> if 32bit, check under max and over min.

    // parse LEB128 encoding. first bit is 0 mean end Byte. data are sorted LE.
    // `0b1aaaaaaa 0b1bbbbbbb 0b0ccccccc` -> 0bcccccccbbbbbbbaaaaaaa
    // get '(0b1a...a)+ 0b0a...a'
    Result<ArrayList<Byte>, ParseException> nextMaybeInteger(int bitWidth) {
        ArrayList<Byte> bytes = new ArrayList<>();
        int i = 0;
        byte b;
        do {
            switch (this.parser.next()) {
                case Err(InvalidIndexException e) -> {
                    return new Err<>(e.into());
                }
                case Ok(Byte b_) -> b = b_;
            }
            bytes.add(b);
            if (this.parser.takeByte(b, 7) == 0) {
                return new Ok<>(bytes);
            }
            i += 7;
        } while (i < bitWidth);
        // if reach here, not finish.
        return new Err<>(new ParseException("Overflow in parsing number.(too long bytes)"));
    }

    /**
     * @param bitWidth int. must 0 < bitWidth <= 64. if not, throw Error(not Exception).
     * @param isSinged boolean. if true, parse to iN.
     * @return parse result. if ok, got Long value.
     */
    public Result<Long, ParseException> parseNumber(int bitWidth, boolean isSinged) {
        if (!(0 < bitWidth && bitWidth <= 64)) {
            throw new Error("invalid bit width");
        }

        ArrayList<Byte> bytes;
        switch (this.nextMaybeInteger(bitWidth)) {
            case Err(ParseException e) -> {
                return new Err<>(e);
            }
            case Ok(ArrayList<Byte> bytes_) -> bytes = bytes_;
        }
        long v = 0;
        for (int i = 0; i < bytes.size() - 1; i++) { // skip last byte.
            v |= ((long) this.parser.takeByte(bytes.get(i), 0, 7)) << (i * 7);
        }
        byte uB = bytes.getLast();
        // max: 7bit x 9 + 1bit x 1 -> 10byte
        if (!isSinged) {
            // spec 5.2.2 unsigned, first branch: n ( if n < 2^7 && n < 2^(N-1)
            //   -> n < min(2^7, 2^(N-1)) -> 2^7=0x80, n < min(0x80, 2^(N-1))
            if (Byte.compareUnsigned(uB, (byte) (1L << Math.clamp(bitWidth - 1, 0, 7))) >= 0) {
                return new Err<>(new ParseException("Overflow in parsing number.(last byte is too big)"));
            }
            v |= (long) uB << (7 * (bytes.size() - 1));
        } else {
            // spec 5.2.2 singed, first branch: n ( if n < 2^6 && n < 2^(N-1) )
            //   -> n < 2^6 && n < 2^(N-1) -> n < 2^(min(6, N-1))
            if (Byte.compareUnsigned(uB, (byte) (1 << Math.clamp(bitWidth - 1, 0, 6))) < 0) {
                v |= (long) uB << (7 * (bytes.size() - 1));
                // spec 5.2.2 singed, second branch: n - 2^7 ( if (2^6 <= n < 2^7) && (n >= (2^7 - 2^(N-1))) )
                //    -> (2^6 <= n < 2^7) && (n >= (2^7 - 2^(N-1))) -> max(2^6,(2^7 - 2^(N-1))) <= n
                //    -> 2^7=0x80, 2^6=0x40, clamp(v:0x80-2^(N-1), min:0, max:0x40) <= n
            } else if (Byte.compareUnsigned((byte) (Math.clamp(0x80 - (1L << (bitWidth - 1)), 0, 0x40)), uB) <= 0) {
                // n - 2^7 (n < 2^7 == 0x80, underflow) -> -(0x80 - n)
                v |= (long) (-(0x80 - uB)) << (7 * (bytes.size() - 1));
            } else {
                // otherwise.
                return new Err<>(new ParseException("Overflow in parsing number.(last byte is too big)"));
            }
        }
        return new Ok<>(v);
    }

    public Result<Integer, ParseException> nextU32() {
        return switch (this.parseNumber(32, false)) {
            case Err(ParseException e) -> new Err<>(e);
            // i & 0xffff_ffff_0000_0000L -> get over 32bit. if over 32bit is 0 -> can convert to int.
            case Ok(Long i) when (i & 0xffff_ffff_0000_0000L) == 0 -> new Ok<>(i.intValue());
            default -> new Err<>(new ParseException("Overflow in parsing umber.(over 32bit)"));
        };
    }

    public Result<Integer, ParseException> nextI32() {
        return switch (this.parseNumber(32, true)) {
            case Err(ParseException e) -> new Err<>(e);
            case Ok(Long i) when (Integer.MIN_VALUE < i) && (i < Integer.MAX_VALUE) -> new Ok<>(i.intValue());
            default -> new Err<>(new ParseException("Overflow in parsing umber.(over 32bit)"));
        };
    }

    public Result<Long, ParseException> nextU64() {
        return this.parseNumber(64, false);
    }

    public Result<Long, ParseException> nextI64() {
        return this.parseNumber(64, true);
    }
}
