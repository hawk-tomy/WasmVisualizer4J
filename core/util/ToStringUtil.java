package core.util;

import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;

public class ToStringUtil {
    public static <T> String arrayList(ArrayList<T> list) {
        return (
            "(\n"
            + list
                .stream()
                .map(Objects::toString)
                .collect(Collectors.joining("\n"))
                .indent(2)
            + "\n)"
        );
    }
}
