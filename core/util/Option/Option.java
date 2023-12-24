package core.util.Option;

import core.util.Result.Result;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * copied from rust-lang Option. not implemented methods are: any unsafe/nightly method as_ref,
 * as_mut, as_pin_ref, as_pin_mut, as_deref, as_deref_mut: java has not &T, &mut T. insert,
 * get_or_insert, get_or_insert_with, take, replace: not edit in-place. zip: java has not tuple
 * type. iter, iter_mut: temporary un implemented. unwrap_or_default: no Default trait like
 * interface or any. use Option<T>.unwrapOr(T) -> T instead.
 */
public sealed interface Option<T> permits Some, None {

    String toString();

    boolean isSome();

    boolean isSomeAnd(Predicate<T> f);

    boolean isNone();

    T expect(String msg) throws RuntimeException;

    T unwrap() throws RuntimeException;

    T unwrapOr(T default_);

    T unwrapOrElse(Supplier<T> f);

    <U> Option<U> map(Function<T, U> f);

    <U> U mapOr(U default_, Function<T, U> f);

    <U> U mapOrElse(Supplier<U> d, Function<T, U> f);

    <E extends Exception> Result<T, E> okOr(E err);

    <E extends Exception> Result<T, E> okOrElse(Supplier<E> f);

    <U> Option<U> and(Option<U> opt);

    <U> Option<U> andThen(Function<T, Option<U>> f);

    Option<T> filter(Predicate<T> predicate);

    Option<T> or(Option<T> opt);

    Option<T> orElse(Supplier<Option<T>> f);

    Option<T> xor(Option<T> opt);
}
