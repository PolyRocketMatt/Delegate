package com.github.polyrocketmatt.delegate.core;

import com.github.polyrocketmatt.delegate.core.command.CommandFactory;
import com.github.polyrocketmatt.delegate.core.command.DelegateCommand;
import com.github.polyrocketmatt.delegate.core.command.argument.StringArgument;
import com.github.polyrocketmatt.delegate.core.command.definition.DescriptionDefinition;
import com.github.polyrocketmatt.delegate.core.command.definition.NameDefinition;
import com.github.polyrocketmatt.delegate.core.command.definition.SubcommandDefinition;
import com.github.polyrocketmatt.delegate.core.command.properties.IgnoreNullProperty;
import org.junit.jupiter.api.Test;

public class TestArgumentChain {

    @Test
    public void testChain() {
        DelegateCommand cmd = CommandFactory.create()
                .append(new NameDefinition("hello"))
                .append(new DescriptionDefinition("Says hello to the user"))
                .append(new IgnoreNullProperty())
                .append(new StringArgument("name", "The name of the player"))
                .append(new StringArgument("message", "The message to send"))
                .build();
    }

    @Test
    public void testSubChain() {
        DelegateCommand cmd = CommandFactory.create()
                .append(new NameDefinition("hello"))
                .append(new DescriptionDefinition("Says hello to the user"))
                .append(new SubcommandDefinition(
                        CommandFactory.create()
                                .append(new NameDefinition("world"))
                                .append(new DescriptionDefinition("Says hello to the world"))
                                .build()))
                .append(new IgnoreNullProperty())
                .build();
    }

    @Test
    public void testCompatibility() {

    }

}