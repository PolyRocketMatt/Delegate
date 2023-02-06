package com.github.polyrocketmatt.delegate.api.command.tree;

import java.util.List;

@FunctionalInterface
public interface ICommandTree {

    <T extends ICommandNode> List<T> getRoots();

}
