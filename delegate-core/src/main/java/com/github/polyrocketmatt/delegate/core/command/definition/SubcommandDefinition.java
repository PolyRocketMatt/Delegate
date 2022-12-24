package com.github.polyrocketmatt.delegate.core.command.definition;

import com.github.polyrocketmatt.delegate.core.command.CommandAttributeChain;

public class SubcommandDefinition extends CommandDefinition<CommandAttributeChain> {

    public SubcommandDefinition(CommandAttributeChain chain) { super("subCommand", chain); }

}
