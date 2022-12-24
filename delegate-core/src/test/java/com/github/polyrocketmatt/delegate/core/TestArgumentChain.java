package com.github.polyrocketmatt.delegate.core;

import com.github.polyrocketmatt.delegate.core.command.CommandFactory;
import com.github.polyrocketmatt.delegate.core.command.DelegateCommand;
import com.github.polyrocketmatt.delegate.core.command.argument.StringArgument;
import com.github.polyrocketmatt.delegate.core.command.definition.DescriptionDefinition;
import com.github.polyrocketmatt.delegate.core.command.definition.NameDefinition;
import com.github.polyrocketmatt.delegate.core.command.definition.SubcommandDefinition;
import com.github.polyrocketmatt.delegate.core.command.properties.IgnoreNullProperty;
import com.github.polyrocketmatt.delegate.core.command.tree.CommandTree;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static com.github.polyrocketmatt.delegate.core.Delegate.getDelegate;

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

        Set<CommandTree> trees = getDelegate().getAttributeHandler().getCommandMap().keySet();
        System.out.println("YAY");
    }

    @Test
    public void testSubChain() {
        DelegateCommand cmd = CommandFactory.create()
                .append(new NameDefinition("hello"))
                .append(new DescriptionDefinition("Says hello to the user"))
                .append(new StringArgument("name", "The name of the player"))
                .append(new SubcommandDefinition(
                        CommandFactory.create()
                                .append(new NameDefinition("world"))
                                .append(new DescriptionDefinition("Says hello to the world"))))
                .append(new IgnoreNullProperty())
                .build();

        Set<CommandTree> trees = getDelegate().getAttributeHandler().getCommandMap().keySet();
        System.out.println("DOUBLE YAY");
    }

    @Test
    public void testCompatibility() {

    }

}