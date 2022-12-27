package com.github.polyrocketmatt.delegate.core.command.definition;

import com.github.polyrocketmatt.delegate.core.command.CommandAttributeChain;

/**
 * Represents a {@link CommandDefinition} that describes a subcommand
 * for the command.
 *
 * @since 0.0.1
 * @author Matthias Kovacic
 */
public class SubcommandDefinition extends CommandDefinition<CommandAttributeChain> {

    /**
     * Creates a new subcommand definition with a {@link CommandAttributeChain}
     * that describes the subcommand.
     *
     * @param chain The chain of attributes that describe the subcommand.
     */
    public SubcommandDefinition(CommandAttributeChain chain) { super("subCommand", chain); }

}
