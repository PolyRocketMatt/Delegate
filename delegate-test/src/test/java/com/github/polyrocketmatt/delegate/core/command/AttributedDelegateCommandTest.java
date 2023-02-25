// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

package com.github.polyrocketmatt.delegate.core.command;

import com.github.polyrocketmatt.delegate.api.command.CommandAttribute;
import com.github.polyrocketmatt.delegate.api.command.ICommandAttribute;
import com.github.polyrocketmatt.delegate.api.command.action.CommandAction;
import com.github.polyrocketmatt.delegate.api.command.argument.CommandArgument;
import com.github.polyrocketmatt.delegate.api.command.definition.CommandDefinition;
import com.github.polyrocketmatt.delegate.api.command.permission.PermissionTier;
import com.github.polyrocketmatt.delegate.api.command.property.CommandProperty;
import com.github.polyrocketmatt.delegate.api.exception.AttributeException;
import com.github.polyrocketmatt.delegate.core.command.argument.FloatArgument;
import com.github.polyrocketmatt.delegate.core.command.argument.IntArgument;
import com.github.polyrocketmatt.delegate.core.command.argument.StringArgument;
import com.github.polyrocketmatt.delegate.core.command.definition.AliasDefinition;
import com.github.polyrocketmatt.delegate.core.command.definition.SubcommandDefinition;
import com.github.polyrocketmatt.delegate.core.command.permission.PermissionTierType;
import com.github.polyrocketmatt.delegate.core.command.permission.StandardPermission;
import com.github.polyrocketmatt.delegate.core.command.properties.AsyncProperty;
import com.github.polyrocketmatt.delegate.core.command.properties.CatchExceptionProperty;
import com.github.polyrocketmatt.delegate.core.command.properties.IgnoreNonPresentProperty;
import com.github.polyrocketmatt.delegate.core.command.properties.IgnoreNullProperty;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class AttributedDelegateCommandTest {

    private static class TestCommandBuilder extends DelegateCommandBuilder {

        @Override
        public @NotNull TestCommandBuilder with(@NotNull ICommandAttribute attribute) {
            if (attribute instanceof CommandAction && ((CommandAction) attribute).getPrecedence() < 0)
                throw new AttributeException("Action precedence must be greater than 0");
            this.attributes.add((CommandAttribute) attribute);
            return this;
        }

        @Override
        public @NotNull TestCommandBuilder withAlias(@NotNull String alias) {
            return this.with(new AliasDefinition(alias));
        }

        @Override
        public @NotNull TestCommandBuilder withAliases(String... aliases) {
            for (String alias : aliases)
                this.withAlias(alias);
            return this;
        }

        @Override
        public @NotNull TestCommandBuilder withAction(@NotNull CommandAction action) {
            //  Check that action precedence is greater than or equal to 0
            if (action.getPrecedence() <= 0)
                throw new AttributeException("Action precedence must be greater than 0");
            return this.with(action);
        }

        @Override
        public @NotNull TestCommandBuilder withArgument(@NotNull CommandArgument<?> argument) {
            return this.with(argument);
        }

        @Override
        public @NotNull TestCommandBuilder withFloat(@NotNull String name, @NotNull String description) {
            return this.with(FloatArgument.of(name, description));
        }

        @Override
        public @NotNull TestCommandBuilder withFloat(@NotNull String name, @NotNull String description, float defaultValue) {
            return this.with(FloatArgument.of(name, description, defaultValue));
        }

        @Override
        public @NotNull TestCommandBuilder withInt(@NotNull String name, @NotNull String description) {
            return this.with(IntArgument.of(name, description));
        }

        @Override
        public @NotNull TestCommandBuilder withInt(@NotNull String name, @NotNull String description, int defaultValue) {
            return this.with(IntArgument.of(name, description, defaultValue));
        }

        @Override
        public @NotNull TestCommandBuilder withString(@NotNull String name, @NotNull String description) {
            return this.with(StringArgument.of(name, description));
        }

        @Override
        public @NotNull TestCommandBuilder withString(@NotNull String name, @NotNull String description, @NotNull String defaultValue) {
            return this.with(StringArgument.of(name, description, defaultValue));
        }

        @Override
        public @NotNull TestCommandBuilder withDefinition(@NotNull CommandDefinition<?> definition) {
            return this.with(definition);
        }

        @Override
        public @NotNull TestCommandBuilder withSubcommand(@NotNull CommandDefinition<?> subcommand) {
            if (!(subcommand instanceof SubcommandDefinition))
                throw new AttributeException("Subcommand must be a SubcommandDefinition");
            return this.with(subcommand);
        }

        @Override
        public @NotNull TestCommandBuilder withProperty(@NotNull CommandProperty property) {
            return this.with(property);
        }

        @Override
        public @NotNull TestCommandBuilder withAsync() {
            return this.with(new AsyncProperty());
        }

        @Override
        public @NotNull TestCommandBuilder withIgnoreNull() {
            return this.with(new IgnoreNullProperty());
        }

        @Override
        public @NotNull TestCommandBuilder withIgnoreNonPresent() {
            return this.with(new IgnoreNonPresentProperty());
        }

        @Override
        public @NotNull TestCommandBuilder withExceptionCatching() {
            return this.with(new CatchExceptionProperty());
        }

        @Override
        public @NotNull TestCommandBuilder withPermission(@NotNull PermissionTier tier) {
            return this.with(tier);
        }

        @Override
        public @NotNull TestCommandBuilder withPermission(@NotNull String permission, @NotNull PermissionTier parent) {
            return this.with(new StandardPermission(permission, parent));
        }

        @Override
        public @NotNull TestCommandBuilder withPermission(@NotNull String permission) {
            return this.withPermission(new StandardPermission(permission));
        }

        @Override
        public @NotNull TestCommandBuilder withOperatorPermission() {
            return this.withPermission(PermissionTierType.OPERATOR.getTier());
        }

        @Override
        public @NotNull TestCommandBuilder withGlobalPermission() {
            return this.withPermission(PermissionTierType.GLOBAL.getTier());
        }
    }

    @Test
    public void testWithNullChain() {
        assertThrows(NullPointerException.class, () -> new AttributedDelegateCommand(null));
    }


}
