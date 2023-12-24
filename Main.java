import core.Parser;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) {
        System.out.println("parse file count:" + args.length);
        for (String pathStr : args) {
            System.out.println("[start] parse: " + pathStr);
            Path path = FileSystems
                .getDefault()
                .getPath(pathStr);
            byte[] wasm;
            try {
                wasm = Files.readAllBytes(path);
            } catch (IOException e) {
                throw new RuntimeException(e); // unreachable or kill here.
            }

            Parser p = new Parser(wasm);
            System.out.println(p.parse());
            System.out.println("[ end ] parse: " + pathStr);
        }
    }
}
