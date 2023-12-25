import core.Parser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class Main {
    int errCount;

    public static void main(String[] args) {
        String dir = args[0];
        var m = new Main();
        try (Stream<Path> paths = Files.walk(Path.of(dir))) {
            System.out.println(paths
                .map(Path::toFile)
                .filter(File::isFile)
                .filter(f -> f
                    .getName()
                    .endsWith("wasm"))
                .map(f -> {
                    m.parseWasm(f);
                    return 0;
                })
                .count());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(m.errCount);
    }

    public void parseWasm(File path) {
        byte[] wasm;
        try {
            wasm = Files.readAllBytes(path.toPath());
        } catch (IOException e) {
            throw new RuntimeException(e); // unreachable or kill here.
        }

        Parser p = new Parser(wasm);
        try {
            var ret = p.parse();
            // System.out.println(path);
            // System.out.println(ret);
        } catch (Exception e) {
            this.errCount++;
            e.printStackTrace(System.err);
            System.err.println("index: " + p.getIndex());
        }
    }
}
