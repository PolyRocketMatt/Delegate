// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

package com.github.polyrocketmatt.delegate.api.command;

import com.github.polyrocketmatt.delegate.api.command.action.CommandAction;
import com.github.polyrocketmatt.delegate.api.command.argument.CommandArgument;
import com.github.polyrocketmatt.delegate.api.command.definition.CommandDefinition;
import com.github.polyrocketmatt.delegate.api.command.property.CommandProperty;
import com.github.polyrocketmatt.delegate.api.command.permission.PermissionTier;
import org.jetbrains.annotations.NotNull;

/**
 * Builder that allows to build a command.
 *
 */
public interface ICommandBuilder {

    @NotNull IDelegateCommand build();

    @NotNull ICommandBuilder with(ICommandAttribute attribute);

    @NotNull ICommandBuilder withAlias(String alias);

    @NotNull ICommandBuilder withAliases(String... aliases);

    @NotNull ICommandBuilder withAction(CommandAction action);

    @NotNull ICommandBuilder withArgument(CommandArgument<?> argument);

    @NotNull ICommandBuilder withFloat(String name, String description);

    @NotNull ICommandBuilder withFloat(String name, String description, float defaultValue);

    @NotNull ICommandBuilder withInt(String name, String description);

    @NotNull ICommandBuilder withInt(String name, String description, int defaultValue);

    @NotNull ICommandBuilder withString(String name, String description);

    @NotNull ICommandBuilder withString(String name, String description, String defaultValue);

    @NotNull ICommandBuilder withDefinition(CommandDefinition<?> definition);

    @NotNull ICommandBuilder withSubcommand(CommandDefinition<?> subcommand);

    @NotNull ICommandBuilder withProperty(CommandProperty property);

    @NotNull ICommandBuilder withAsync();

    @NotNull ICommandBuilder withIgnoreNull();

    @NotNull ICommandBuilder withIgnoreNonPresent();

    @NotNull ICommandBuilder withExceptionCatching();

    @NotNull ICommandBuilder withPermission(PermissionTier tier);

    @NotNull ICommandBuilder withPermission(String permission, PermissionTier parent);

    @NotNull ICommandBuilder withPermission(String permission);

    @NotNull ICommandBuilder withOperatorPermission();

    @NotNull ICommandBuilder withGlobalPermission();

}
