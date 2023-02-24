// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

package com.github.polyrocketmatt.delegate.api.command.tree;

import org.jetbrains.annotations.NotNull;

import java.util.List;

@FunctionalInterface
public interface ICommandTree {

    <T extends ICommandNode> @NotNull List<T> getRoots();

}
