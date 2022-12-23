package com.github.polyrocketmatt.delegate.core;

import com.github.polyrocketmatt.delegate.core.command.CommandFactory;
import com.github.polyrocketmatt.delegate.core.command.DelegateCommand;
import com.github.polyrocketmatt.delegate.core.command.argument.StringArgument;
import com.github.polyrocketmatt.delegate.core.command.definition.NameDefinition;
import com.github.polyrocketmatt.delegate.core.command.properties.IgnoreNullProperty;
import org.junit.jupiter.api.Test;

public class TestArgumentChain {

    @Test
    public void testChain() {
        DelegateCommand cmd = CommandFactory.create()
                .append(new NameDefinition("hello"))
                .append(new IgnoreNullProperty())
                .append(new StringArgument("name", "The name of the player"))
                .append(new StringArgument("message", "The message to send"))
                .build();
    }

    @Test
    public void testCompatibility() {

    }

}