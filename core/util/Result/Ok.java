package core.util.Result;

import core.util.Option.None;
import core.util.Option.Option;
import core.util.Option.Some;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public record Ok<T, E extends Exception>(T value) implements Result<T, E> {

    public String toString() {
        String v = this.value.toString();
        if (v.length() >= 120) {
            v = "\n" + v.indent(2);
        }
        return String.format("util.Result.Ok(%s)", v);
    }

    public boolean isOk() {
        return true;
    }

    public boolean isOkAnd(Predicate<T> f) {
        return f.test(this.value);
    }

    public boolean isErr() {
        return false;
    }

    public boolean isErrAnd(Predicate<E> f) {
        return false;
    }

    public Option<T> ok() {
        return new Some<>(this.value);
    }

    public Option<E> err() {
        return new None<>();
    }

    public <U> Result<U, E> map(Function<T, U> f) {
        return new Ok<>(f.apply(this.value));
    }

    public <U> U mapOr(U default_, Function<T, U> f) {
        return f.apply(this.value);
    }

    public <U> U mapOrElse(Function<E, U> default_, Function<T, U> f) {
        return f.apply(this.value);
    }

    public <F extends Exception> Result<T, F> mapErr(Function<E, F> op) {
        return new Ok<>(this.value);
    }

    public T expect(String msg) throws RuntimeException {
        return this.value;
    }

    public T unwrap() {
        return this.value;
    }

    public E expectErr(String msg) throws RuntimeException {
        throw new RuntimeException(msg + ": " + this.value);
    }

    public E unwrapErr() throws RuntimeException {
        throw new RuntimeException(this.value.toString());
    }

    public <U> Result<U, E> and(Result<U, E> res) {
        return res;
    }

    public <U> Result<U, E> andThen(Function<T, Result<U, E>> f) {
        return f.apply(this.value);
    }

    public <F extends Exception> Result<T, F> or(Result<T, F> res) {
        return new Ok<>(this.value);
    }

    public <F extends Exception> Result<T, F> orElse(Function<E, Result<T, F>> f) {
        return new Ok<>(this.value);
    }

    public T unwrapOr(T default_) {
        return this.value;
    }

    public T unwrapOrElse(Function<E, T> f) {
        return this.value;
    }

    public void mapConsume(Consumer<T> ok, Consumer<E> err) {
        ok.accept(this.value);
    }
}
