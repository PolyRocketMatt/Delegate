package com.github.polyrocketmatt.delegate.core.command.definition;

import com.github.polyrocketmatt.delegate.core.command.DelegateCommand;

public class SubcommandDefinition extends CommandDefinition<DelegateCommand> {

    public SubcommandDefinition(DelegateCommand command) { super("subCommand", command); }

}
