package com.github.polyrocketmatt.delegate.integration;

import com.github.polyrocketmatt.delegate.api.IPlatform;
import com.github.polyrocketmatt.delegate.api.exception.CommandRegisterException;
import com.github.polyrocketmatt.delegate.core.CommandBuilderImpl;
import com.github.polyrocketmatt.delegate.core.CommanderEntityImpl;
import com.github.polyrocketmatt.delegate.core.DelegateCore;
import com.github.polyrocketmatt.delegate.core.PlatformImpl;
import com.github.polyrocketmatt.delegate.core.command.AttributedDelegateCommand;
import com.github.polyrocketmatt.delegate.core.command.DelegateCommand;
import com.github.polyrocketmatt.delegate.core.command.definition.DescriptionDefinition;
import com.github.polyrocketmatt.delegate.core.command.definition.NameDefinition;
import com.github.polyrocketmatt.delegate.core.command.definition.SubcommandDefinition;
import com.github.polyrocketmatt.delegate.core.command.tree.CommandNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.github.polyrocketmatt.delegate.core.DelegateCore.getDelegate;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests all possible points of failure when a command is registered.
 * Building will fail if the attribute-handler fails, but is tested separately.
 */
public class DelegateCommandFlowTest {

    private static final IPlatform PLATFORM = new PlatformImpl();
    private static final CommanderEntityImpl ENTITY = new CommanderEntityImpl();
    private static CommandBuilderImpl BUILDER = new CommandBuilderImpl();

    @BeforeEach
    public void reset() {
        if (getDelegate().getPlatform() == null)
            getDelegate().setPlatform(PLATFORM);

        BUILDER = new CommandBuilderImpl();

        //  Clear command roots
        getDelegate().getCommandHandler().clearCommandCache();
    }

    @Test
    public void testInvalidOverwrite() {
        SubcommandDefinition subCommandA = new SubcommandDefinition("subCommandA", "");
        SubcommandDefinition subCommandB = new SubcommandDefinition("subCommandB", "");

        DelegateCommand command = new AttributedDelegateCommand(BUILDER
                .withDefinition(new NameDefinition("test"))
                .withDefinition(new DescriptionDefinition("This is a test command"))
                .withSubcommand(subCommandA)
                .withSubcommand(subCommandB)
        );

        CommandNode node = new CommandNode(command);

        assertThrows(CommandRegisterException.class, () -> DelegateCore.getDelegate().registerCommand(node), "Cannot overwrite verified command node");
    }

}
