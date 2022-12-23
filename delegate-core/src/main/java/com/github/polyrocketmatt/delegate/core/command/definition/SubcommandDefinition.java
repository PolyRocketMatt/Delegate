package com.github.polyrocketmatt.delegate.core.command.definition;

import com.github.polyrocketmatt.delegate.core.command.AttributedDelegateCommand;
import com.github.polyrocketmatt.delegate.core.command.DelegateCommand;
import com.github.polyrocketmatt.delegate.core.command.VerifiedDelegateCommand;

public class SubcommandDefinition extends CommandDefinition<DelegateCommand> {

    public SubcommandDefinition(String identifier, AttributedDelegateCommand command) {
        super(identifier, command);
    }

    public SubcommandDefinition(String identifier, VerifiedDelegateCommand command) { super(identifier, command); }

}
