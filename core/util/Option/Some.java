package core.util.Option;

import core.util.Result.Ok;
import core.util.Result.Result;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public record Some<T>(T value) implements Option<T> {

    public String toString() {
        return "util.Option.Some(" + this.value.toString() + ')';
    }

    public boolean isSome() {
        return true;
    }

    public boolean isSomeAnd(Predicate<T> f) {
        return f.test(this.value);
    }

    public boolean isNone() {
        return false;
    }

    public T expect(String msg) throws RuntimeException {
        return this.value;
    }

    public T unwrap() throws RuntimeException {
        return this.value;
    }

    public T unwrapOr(T default_) {
        return this.value;
    }

    public T unwrapOrElse(Supplier<T> f) {
        return this.value;
    }

    public <U> Option<U> map(Function<T, U> f) {
        return new Some<>(f.apply(this.value));
    }

    public <U> U mapOr(U default_, Function<T, U> f) {
        return f.apply(this.value);
    }

    public <U> U mapOrElse(Supplier<U> d, Function<T, U> f) {
        return f.apply(this.value);
    }

    public <E extends Exception> Result<T, E> okOr(E err) {
        return new Ok<>(this.value);
    }

    public <E extends Exception> Result<T, E> okOrElse(Supplier<E> f) {
        return new Ok<>(this.value);
    }

    public <U> Option<U> and(Option<U> opt) {
        return opt;
    }

    public <U> Option<U> andThen(Function<T, Option<U>> f) {
        return f.apply(this.value);
    }

    public Option<T> filter(Predicate<T> predicate) {
        return predicate.test(this.value) ? this : new None<>();
    }

    public Option<T> or(Option<T> opt) {
        return this;
    }

    public Option<T> orElse(Supplier<Option<T>> f) {
        return this;
    }

    public Option<T> xor(Option<T> opt) {
        return opt.isSome() ? new None<>() : this;
    }
}
