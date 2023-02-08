// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

package com.github.polyrocketmatt.delegate.api.command;

import com.github.polyrocketmatt.delegate.api.command.action.CommandAction;
import com.github.polyrocketmatt.delegate.api.command.argument.CommandArgument;
import com.github.polyrocketmatt.delegate.api.command.definition.CommandDefinition;
import com.github.polyrocketmatt.delegate.api.command.property.CommandProperty;
import com.github.polyrocketmatt.delegate.api.command.permission.PermissionTier;

/**
 * Builder that allows to build a command.
 *
 */
public interface ICommandBuilder {

    IDelegateCommand build();

    ICommandBuilder with(ICommandAttribute attribute);

    ICommandBuilder withAlias(String alias);

    ICommandBuilder withAliases(String... aliases);

    ICommandBuilder withAction(CommandAction action);

    ICommandBuilder withArgument(CommandArgument<?> argument);

    ICommandBuilder withFloat(String name, String description);

    ICommandBuilder withFloat(String name, String description, float defaultValue);

    ICommandBuilder withInt(String name, String description);

    ICommandBuilder withInt(String name, String description, int defaultValue);

    ICommandBuilder withString(String name, String description);

    ICommandBuilder withString(String name, String description, String defaultValue);

    ICommandBuilder withDefinition(CommandDefinition<?> definition);

    ICommandBuilder withSubcommand(CommandDefinition<?> subcommand);

    ICommandBuilder withProperty(CommandProperty property);

    ICommandBuilder withAsync();

    ICommandBuilder withIgnoreNull();

    ICommandBuilder withIgnoreNonPresent();

    ICommandBuilder withExceptionCatching();

    ICommandBuilder withPermission(PermissionTier tier);

    ICommandBuilder withPermission(String permission, PermissionTier parent);

    ICommandBuilder withPermission(String permission);

    ICommandBuilder withOperatorPermission();

    ICommandBuilder withGlobalPermission();

}
