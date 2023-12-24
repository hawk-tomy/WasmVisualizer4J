package core;

import core.util.InvalidIndexException;
import core.util.ParseException;
import core.util.Result.Err;
import core.util.Result.Ok;
import core.util.Result.Result;

/**
 * note: u** fields are only allow unsigned integer.
 */
public class Parser {

    private final byte[] wasm;
    private Integer uIndex;

    public Parser(byte[] wasm) {
        this.wasm = wasm;
        this.uIndex = 0;
    }

    public Integer getIndex() {
        return this.uIndex;
    }

    public boolean hasNext() {
        return this.wasm.length > this.uIndex + 1;
    }

    public Result<Byte, InvalidIndexException> peek() {
        return this.peek(0);
    }

    public Result<Byte, InvalidIndexException> peek(int uIdx) {
        int idx = this.uIndex + uIdx;
        if (this.wasm.length <= idx) {
            return new Err<>(new InvalidIndexException());
        }
        return new Ok<>(this.wasm[idx]);
    }

    public Result<Byte, InvalidIndexException> next() {
        if (this.wasm.length <= this.uIndex) {
            return new Err<>(new InvalidIndexException());
        }
        byte b = this.wasm[this.uIndex];
        this.uIndex++;
        return new Ok<>(b);
    }

}
