package com.github.polyrocketmatt.delegate.core;

import com.github.polyrocketmatt.delegate.core.command.DelegateCommand;
import com.github.polyrocketmatt.delegate.core.command.definition.AliasDefinition;
import com.github.polyrocketmatt.delegate.core.command.definition.DescriptionDefinition;
import com.github.polyrocketmatt.delegate.core.command.definition.NameDefinition;
import org.jetbrains.annotations.NotNull;

public class DelegateCommandImpl extends DelegateCommand {

    private final NameDefinition nameDefinition;
    private final DescriptionDefinition descriptionDefinition;
    private final AliasDefinition[] aliasDefinitions;

    public DelegateCommandImpl(String name, String description, String... aliases) {
        this.nameDefinition = new NameDefinition(name);
        this.descriptionDefinition = new DescriptionDefinition(description);
        this.aliasDefinitions = new AliasDefinition[aliases.length];
        for (int i = 0; i < aliases.length; i++)
            this.aliasDefinitions[i] = new AliasDefinition(aliases[i]);
    }

    public DelegateCommandImpl() {
        this("test", "A simple description", "test2");
    }

    @Override
    public @NotNull NameDefinition getNameDefinition() {
        return this.nameDefinition;
    }

    @Override
    public @NotNull DescriptionDefinition getDescriptionDefinition() {
        return this.descriptionDefinition;
    }

    @Override
    public AliasDefinition[] getAliases() {
        return this.aliasDefinitions;
    }
}