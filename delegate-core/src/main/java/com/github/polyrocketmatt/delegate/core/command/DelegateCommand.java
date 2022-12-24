package com.github.polyrocketmatt.delegate.core.command;

import com.github.polyrocketmatt.delegate.core.command.tree.CommandTreeNode;

@FunctionalInterface
public interface DelegateCommand {

    CommandTreeNode getAsNode();

}
