// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

package com.github.polyrocketmatt.delegate.api.command.tree;

import java.util.List;

@FunctionalInterface
public interface ICommandTree {

    <T extends ICommandNode> List<T> getRoots();

}
