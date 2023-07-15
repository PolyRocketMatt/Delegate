package com.github.polyrocketmatt.delegate.core;

import com.github.polyrocketmatt.delegate.api.command.definition.CommandDefinition;
import com.github.polyrocketmatt.delegate.api.command.tree.ICommandNode;
import com.github.polyrocketmatt.delegate.core.command.definition.DescriptionDefinition;
import com.github.polyrocketmatt.delegate.core.command.definition.NameDefinition;
import com.github.polyrocketmatt.delegate.core.command.definition.SubcommandDefinition;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class InvalidCommandNodeImpl implements ICommandNode {

    @Override
    public @NotNull <T extends ICommandNode> List<T> getChildren() {
        return List.of();
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public @NotNull CommandDefinition<String> getNameDefinition() {
        return new NameDefinition("INVALID");
    }

    @Override
    public @NotNull CommandDefinition<String> getDescriptionDefinition() {
        return new DescriptionDefinition("INVALID COMMAND NODE");
    }

    @SuppressWarnings("unchecked")
    @Override
    public @NotNull CommandDefinition<String>[] getAliasDefinitions() {
        return new CommandDefinition[] { new SubcommandDefinition(null) };
    }

}
