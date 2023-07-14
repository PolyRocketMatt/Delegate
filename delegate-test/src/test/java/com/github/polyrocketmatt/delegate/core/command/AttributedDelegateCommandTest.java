// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

package com.github.polyrocketmatt.delegate.core.command;

import com.github.polyrocketmatt.delegate.core.CommandBuilderImpl;
import com.github.polyrocketmatt.delegate.core.command.definition.DescriptionDefinition;
import com.github.polyrocketmatt.delegate.core.command.definition.NameDefinition;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AttributedDelegateCommandTest {

    @Test
    public void testWithNullChain() {
        assertThrows(IllegalArgumentException.class, () -> new AttributedDelegateCommand(null));
    }

    @Test
    public void testAttributedCommand() {
        AttributedDelegateCommand command = new AttributedDelegateCommand(
                new CommandBuilderImpl()
                        .withDefinition(new NameDefinition("test"))
                        .withDefinition(new DescriptionDefinition("test"))
                        .withAliases("a", "b")
        );

        assertEquals("test", command.getNameDefinition().getValue());
        assertEquals("test", command.getDescriptionDefinition().getValue());
        assertEquals(2, command.getAliases().length);
        assertEquals("a", command.getAliases()[0].getValue());
        assertEquals("b", command.getAliases()[1].getValue());
    }

}
