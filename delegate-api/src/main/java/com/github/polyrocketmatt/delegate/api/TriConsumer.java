// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

package com.github.polyrocketmatt.delegate.api;

import org.apiguardian.api.API;

import java.util.function.Consumer;

/**
 * @param <S> the type of the first argument to the operation
 * @param <T> the type of the second argument to the operation
 * @param <U> the type of the third argument to the operation
 *
 * @see Consumer
 * @since 1.8
 */
@FunctionalInterface
@API(status = API.Status.STABLE, since = "0.0.1")
public interface TriConsumer<S, T, U> {

    /**
     * Performs this operation on the given arguments.
     *
     * @param t the first input argument
     * @param u the second input argument
     */
    void accept(S s, T t, U u);

}