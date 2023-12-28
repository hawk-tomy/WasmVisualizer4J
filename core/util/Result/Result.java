package core.util.Result;

import core.util.Option.Option;

import java.util.function.Function;
import java.util.function.Predicate;

/**
 * copied from rust-lang Result. not implemented methods are: any unsafe/nightly method as_ref,
 * as_mut, as_deref, as_deref_mut: java has not &T, &mut T. iter, iter_mut: temporary un
 * implemented. unwrap_or_default: no Default trait like interface or any. use Result<T,
 * E>.unwrapOr(T) -> T instead.
 */
public sealed interface Result<T, E extends Exception> permits Ok, Err {

    String toString();

    boolean isOk();

    boolean isOkAnd(Predicate<T> f);

    boolean isErr();

    boolean isErrAnd(Predicate<E> f);

    Option<T> ok();

    Option<E> err();

    <U> Result<U, E> map(Function<T, U> f);

    <U> U mapOr(U default_, Function<T, U> f);

    <U> U mapOrElse(Function<E, U> default_, Function<T, U> f);

    <F extends Exception> Result<T, F> mapErr(Function<E, F> op);

    T expect(String msg) throws RuntimeException;

    T unwrap() throws E;

    E expectErr(String msg) throws RuntimeException;

    E unwrapErr() throws RuntimeException;

    <U> Result<U, E> and(Result<U, E> res);

    <U> Result<U, E> andThen(Function<T, Result<U, E>> f);

    <F extends Exception> Result<T, F> or(Result<T, F> res);

    <F extends Exception> Result<T, F> orElse(Function<E, Result<T, F>> f);

    T unwrapOr(T default_);

    T unwrapOrElse(Function<E, T> f);
}
