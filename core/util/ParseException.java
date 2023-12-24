package core.util;

public class ParseException extends Exception {

    public final String msg;

    public ParseException(String msg) {
        this.msg = msg;
    }

    public String toString() {
        return super.toString() + "\n" + this.msg;
    }
}
