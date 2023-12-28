package core.util.Result;

import core.util.Option.None;
import core.util.Option.Option;
import core.util.Option.Some;

import java.util.function.Function;
import java.util.function.Predicate;

public record Err<T, E extends Exception>(E value) implements Result<T, E> {

    public String toString() {
        return String.format("util.Result.Err(%s)", this.value.toString());
    }

    public boolean isOk() {
        return false;
    }

    public boolean isOkAnd(Predicate<T> f) {
        return false;
    }

    public boolean isErr() {
        return true;
    }

    public boolean isErrAnd(Predicate<E> f) {
        return f.test(this.value);
    }

    public Option<T> ok() {
        return new None<>();
    }

    public Option<E> err() {
        return new Some<>(this.value);
    }

    public <U> Result<U, E> map(Function<T, U> f) {
        return new Err<>(this.value);
    }

    public <U> U mapOr(U default_, Function<T, U> f) {
        return default_;
    }

    public <U> U mapOrElse(Function<E, U> default_, Function<T, U> f) {
        return default_.apply(this.value);
    }

    public <F extends Exception> Result<T, F> mapErr(Function<E, F> op) {
        return new Err<>(op.apply(this.value));
    }

    public T expect(String msg) throws RuntimeException {
        throw new RuntimeException(msg, this.value);
    }

    public T unwrap() throws E {
        throw this.value;
    }

    public E expectErr(String msg) throws RuntimeException {
        return this.value;
    }

    public E unwrapErr() throws RuntimeException {
        return this.value;
    }

    public <U> Result<U, E> and(Result<U, E> res) {
        return new Err<>(this.value);
    }

    public <U> Result<U, E> andThen(Function<T, Result<U, E>> f) {
        return new Err<>(this.value);
    }

    public <F extends Exception> Result<T, F> or(Result<T, F> res) {
        return res;
    }

    public <F extends Exception> Result<T, F> orElse(Function<E, Result<T, F>> f) {
        return f.apply(this.value);
    }

    public T unwrapOr(T default_) {
        return default_;
    }

    public T unwrapOrElse(Function<E, T> f) {
        return f.apply(this.value);
    }
}
