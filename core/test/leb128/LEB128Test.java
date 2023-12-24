package core.test.leb128;

import core.Parser;
import core.parser.LEB128Parser;
import core.util.ParseException;
import core.util.Result.Err;
import core.util.Result.Ok;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HexFormat;
import java.util.stream.Stream;

public class LEB128Test {

    /**
     * for parse test.
     */
    public static void main(String[] args) throws IOException {
        // test for parse leb128. see ./test/leb128

        System.out.println("u32: start");
        Path path_u32 = FileSystems
            .getDefault()
            .getPath("./core/test/leb128/u32_case.txt");

        ArrayList<Integer> u32Numbers = new ArrayList<>();
        ArrayList<Byte> u32Bytes_ = new ArrayList<>();
        try (Stream<String> stream = Files.lines(path_u32)) {
            for (String line : stream.toList()) {
                String[] s = line.split(" ");
                u32Numbers.add(Integer.parseUnsignedInt(s[0]));
                HexFormat hex = HexFormat.of();
                byte[] bytes = hex.parseHex(s[1]);
                for (byte b : bytes) {
                    u32Bytes_.add(b);
                }
            }
        }
        byte[] u32Bytes = new byte[u32Bytes_.size()];
        for (int i = 0; i < u32Bytes_.size(); i++) {
            u32Bytes[i] = u32Bytes_.get(i);
        }
        Parser parser_u32 = new Parser(u32Bytes);
        LEB128Parser lp_u32 = new LEB128Parser(parser_u32);
        boolean failed = false;
        for (int i = 0; i < u32Numbers.size(); i++) {
            failed |= switch (lp_u32.nextU32()) {
                case Ok(Integer v) -> {
                    if (!u32Numbers
                        .get(i)
                        .equals(v)) {
                        System.out.println(
                            "parse u32 err: " + u32Numbers.get(i) + " != " + v + ", idx=" + i + ", uIdx="
                            + parser_u32.getIndex());
                        yield true;
                    }
                    yield false;
                }
                case Err(ParseException e) -> {
                    System.out.println(e.msg + ", uIdx=" + parser_u32.getIndex());
                    yield true;
                }
            };
        }
        if (failed) {
            System.out.println("u32: end & WITH ERROR");
        } else {
            System.out.println("u32: end & pass");
        }

        System.out.println("i32: start");
        Path path_i32 = FileSystems
            .getDefault()
            .getPath("./core/test/leb128/i32_case.txt");

        ArrayList<Integer> i32Numbers = new ArrayList<>();
        ArrayList<Byte> i32Bytes_ = new ArrayList<>();
        try (Stream<String> stream = Files.lines(path_i32)) {
            for (String line : stream.toList()) {
                String[] s = line.split(" ");
                i32Numbers.add(Integer.parseInt(s[0]));
                HexFormat hex = HexFormat.of();
                byte[] bytes = hex.parseHex(s[1]);
                for (byte b : bytes) {
                    i32Bytes_.add(b);
                }
            }
        }
        byte[] i32Bytes = new byte[i32Bytes_.size()];
        for (int i = 0; i < i32Bytes_.size(); i++) {
            i32Bytes[i] = i32Bytes_.get(i);
        }
        Parser parser_i32 = new Parser(i32Bytes);
        LEB128Parser lp_i32 = new LEB128Parser(parser_i32);
        failed = false;
        for (int i = 0; i < i32Numbers.size(); i++) {
            failed |= switch (lp_i32.nextI32()) {
                case Ok(Integer v) -> {
                    if (!i32Numbers
                        .get(i)
                        .equals(v)) {
                        System.out.println(
                            "parse i32 err: " + i32Numbers.get(i) + " != " + v + ", idx=" + i + ", uIdx="
                            + parser_i32.getIndex());
                        yield true;
                    }
                    yield false;
                }
                case Err(ParseException e) -> {
                    System.out.println(e.msg + ", uIdx=" + parser_i32.getIndex());
                    yield true;
                }
            };
        }
        if (failed) {
            System.out.println("i32: end & WITH ERROR");
        } else {
            System.out.println("i32: end & pass");
        }

        System.out.println("u64: start");
        Path path_u64 = FileSystems
            .getDefault()
            .getPath("./core/test/leb128/u64_case.txt");

        ArrayList<Long> u64Numbers = new ArrayList<>();
        ArrayList<Byte> u64Bytes_ = new ArrayList<>();
        try (Stream<String> stream = Files.lines(path_u64)) {
            for (String line : stream.toList()) {
                String[] s = line.split(" ");
                u64Numbers.add(Long.parseUnsignedLong(s[0]));
                HexFormat hex = HexFormat.of();
                byte[] bytes = hex.parseHex(s[1]);
                for (byte b : bytes) {
                    u64Bytes_.add(b);
                }
            }
        }
        byte[] u64Bytes = new byte[u64Bytes_.size()];
        for (int i = 0; i < u64Bytes_.size(); i++) {
            u64Bytes[i] = u64Bytes_.get(i);
        }
        Parser parser_u64 = new Parser(u64Bytes);
        LEB128Parser lp_u64 = new LEB128Parser(parser_u64);
        failed = false;
        for (int i = 0; i < u64Numbers.size(); i++) {
            failed |= switch (lp_u64.nextU64()) {
                case Ok(Long v) -> {
                    if (!u64Numbers
                        .get(i)
                        .equals(v)) {
                        System.out.println(
                            "parse u64 err: " + u64Numbers.get(i) + " != " + v + ", idx=" + i + ", uIdx="
                            + parser_u64.getIndex());
                        yield true;
                    }
                    yield false;
                }
                case Err(ParseException e) -> {
                    System.out.println(e.msg + ", uIdx=" + parser_u64.getIndex());
                    yield true;
                }
            };
        }
        if (failed) {
            System.out.println("u64: end & WITH ERROR");
        } else {
            System.out.println("u64: end & pass");
        }

        System.out.println("i64: start");
        Path path_i64 = FileSystems
            .getDefault()
            .getPath("./core/test/leb128/i64_case.txt");

        ArrayList<Long> i64Numbers = new ArrayList<>();
        ArrayList<Byte> i64Bytes_ = new ArrayList<>();
        try (Stream<String> stream = Files.lines(path_i64)) {
            for (String line : stream.toList()) {
                String[] s = line.split(" ");
                i64Numbers.add(Long.parseLong(s[0]));
                HexFormat hex = HexFormat.of();
                byte[] bytes = hex.parseHex(s[1]);
                for (byte b : bytes) {
                    i64Bytes_.add(b);
                }
            }
        }
        byte[] i64Bytes = new byte[i64Bytes_.size()];
        for (int i = 0; i < i64Bytes_.size(); i++) {
            i64Bytes[i] = i64Bytes_.get(i);
        }
        Parser parser_i64 = new Parser(i64Bytes);
        LEB128Parser lp_i64 = new LEB128Parser(parser_i64);
        failed = false;
        for (int i = 0; i < i64Numbers.size(); i++) {
            failed |= switch (lp_i64.nextI64()) {
                case Ok(Long v) -> {
                    if (!i64Numbers
                        .get(i)
                        .equals(v)) {
                        System.out.println(
                            "parse i64 err: " + i64Numbers.get(i) + " != " + v + ", idx=" + i + ", uIdx="
                            + parser_i64.getIndex());
                        yield true;
                    }
                    yield false;
                }
                case Err(ParseException e) -> {
                    System.out.println(e.msg + ", uIdx=" + parser_i64.getIndex());
                    yield true;
                }
            };
        }
        if (failed) {
            System.out.println("i64: end & WITH ERROR");
        } else {
            System.out.println("i64: end & pass");
        }
    }
}
