// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

package com.github.polyrocketmatt.delegate.api.command;

import com.github.polyrocketmatt.delegate.api.command.action.CommandAction;
import com.github.polyrocketmatt.delegate.api.command.argument.CommandArgument;
import com.github.polyrocketmatt.delegate.api.command.definition.CommandDefinition;
import com.github.polyrocketmatt.delegate.api.command.property.CommandProperty;
import com.github.polyrocketmatt.delegate.api.command.permission.PermissionTier;
import org.apiguardian.api.API;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Builder that allows to build a command.
 *
 */
@API(status = API.Status.STABLE, since = "0.0.1")
public interface ICommandBuilder {

    @NotNull IDelegateCommand build();

    @NotNull ICommandBuilder with(@NotNull ICommandAttribute attribute);

    @NotNull ICommandBuilder withAlias(@NotNull String alias);

    @NotNull ICommandBuilder withAliases(@NotNull String... aliases);

    @NotNull ICommandBuilder withAction(@NotNull CommandAction action);

    @NotNull ICommandBuilder withArgument(@NotNull CommandArgument<?> argument);

    @NotNull ICommandBuilder withDouble(@NotNull String name, @NotNull String description);

    @NotNull ICommandBuilder withDouble(@NotNull String name, @NotNull String description, double defaultValue);

    @NotNull ICommandBuilder withFloat(@NotNull String name, @NotNull String description);

    @NotNull ICommandBuilder withFloat(@NotNull String name, @NotNull String description, float defaultValue);

    @NotNull ICommandBuilder withInt(@NotNull String name, @NotNull String description);

    @NotNull ICommandBuilder withInt(@NotNull String name, @NotNull String description, int defaultValue);

    @NotNull ICommandBuilder withString(@NotNull String name, @NotNull String description);

    @NotNull ICommandBuilder withString(@NotNull String name, @NotNull String description, @Nullable String defaultValue);

    @NotNull ICommandBuilder withDefinition(@NotNull CommandDefinition<?> definition);

    @NotNull ICommandBuilder withSubcommand(@NotNull CommandDefinition<?> subcommand);

    @NotNull ICommandBuilder withSubcommand(@NotNull String name, @NotNull String description);

    @NotNull ICommandBuilder withProperty(@NotNull CommandProperty property);

    @NotNull ICommandBuilder withAsync();

    @NotNull ICommandBuilder withIgnoreNull();

    @NotNull ICommandBuilder withIgnoreNonPresent();

    @NotNull ICommandBuilder withExceptionCatching();

    @NotNull ICommandBuilder withPermission(@NotNull PermissionTier tier);

    @NotNull ICommandBuilder withPermission(@NotNull String permission, @NotNull PermissionTier parent);

    @NotNull ICommandBuilder withPermission(@NotNull String permission);

    @NotNull ICommandBuilder withOperatorPermission();

    @NotNull ICommandBuilder withGlobalPermission();

}
