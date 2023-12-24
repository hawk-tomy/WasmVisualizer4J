package core.util.Option;

import core.util.Result.Err;
import core.util.Result.Result;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public record None<T>() implements Option<T> {

    public String toString() {
        return "util.Option.None";
    }

    public boolean isSome() {
        return false;
    }

    public boolean isSomeAnd(Predicate<T> f) {
        return false;
    }

    public boolean isNone() {
        return true;
    }

    public T expect(String msg) throws RuntimeException {
        throw new RuntimeException(msg);
    }

    public T unwrap() throws RuntimeException {
        throw new RuntimeException("This value is None");
    }

    public T unwrapOr(T default_) {
        return default_;
    }

    public T unwrapOrElse(Supplier<T> f) {
        return f.get();
    }

    public <U> Option<U> map(Function<T, U> f) {
        return new None<>();
    }

    public <U> U mapOr(U default_, Function<T, U> f) {
        return default_;
    }

    public <U> U mapOrElse(Supplier<U> d, Function<T, U> f) {
        return d.get();
    }

    public <E extends Exception> Result<T, E> okOr(E err) {
        return new Err<>(err);
    }

    public <E extends Exception> Result<T, E> okOrElse(Supplier<E> f) {
        return new Err<>(f.get());
    }

    public <U> Option<U> and(Option<U> opt) {
        return new None<>();
    }

    public <U> Option<U> andThen(Function<T, Option<U>> f) {
        return new None<>();
    }

    public Option<T> filter(Predicate<T> predicate) {
        return this;
    }

    public Option<T> or(Option<T> opt) {
        return opt;
    }

    public Option<T> orElse(Supplier<Option<T>> f) {
        return f.get();
    }

    public Option<T> xor(Option<T> opt) {
        return opt.isSome() ? opt : this;
    }
}
