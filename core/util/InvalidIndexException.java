package core.util;

public class InvalidIndexException extends Exception {
    public ParseException into() {
        return new ParseException("Index Out Of Range.");
    }
}
