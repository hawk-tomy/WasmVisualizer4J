package core.util;

import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;

public class ToStringUtil {
    public static <T> String arrayList(ArrayList<T> list) {
        if (list.isEmpty()) {
            return "()";
        }
        int length = list.size();
        if (length < 5) {
            return (
                "(\n"
                + list
                    .stream()
                    .map(Objects::toString)
                    .collect(Collectors.joining("\n"))
                    .indent(2)
                + ")"
            );
        }
        int d = (int) Math.ceil(Math.log10(length + 1));
        StringBuilder sb = new StringBuilder("");
        for (int i = 0; i < length; i++) {
            sb.append(String.format("%0" + d + "d %s\n", i + 1, list.get(i)));
        }
        return "(\n" + sb
            .toString()
            .indent(2) + ")";
    }
}
