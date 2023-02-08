// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

package com.github.polyrocketmatt.delegate.core.handlers;

import com.github.polyrocketmatt.delegate.api.command.CommandBuffer;
import com.github.polyrocketmatt.delegate.api.command.CommandDispatchInformation;
import com.github.polyrocketmatt.delegate.api.command.action.CommandAction;
import com.github.polyrocketmatt.delegate.api.command.argument.Argument;
import com.github.polyrocketmatt.delegate.api.command.argument.CommandArgument;
import com.github.polyrocketmatt.delegate.api.command.data.CommandCapture;
import com.github.polyrocketmatt.delegate.api.command.feedback.FeedbackType;
import com.github.polyrocketmatt.delegate.api.entity.CommanderEntity;
import com.github.polyrocketmatt.delegate.api.exception.CommandExecutionException;
import com.github.polyrocketmatt.delegate.api.exception.CommandRegisterException;
import com.github.polyrocketmatt.delegate.core.command.VerifiedDelegateCommand;
import com.github.polyrocketmatt.delegate.core.command.action.ExceptAction;
import com.github.polyrocketmatt.delegate.core.command.properties.AsyncProperty;
import com.github.polyrocketmatt.delegate.core.command.properties.CatchExceptionProperty;
import com.github.polyrocketmatt.delegate.core.command.tree.CommandNode;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.context.ParsedArgument;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.github.polyrocketmatt.delegate.core.DelegateCore.getDelegate;
import static com.mojang.brigadier.builder.RequiredArgumentBuilder.argument;

public class BrigadierCommandHandler extends DelegateCommandHandler {

    private final CommandDispatcher<CommanderEntity> dispatcher;

    public BrigadierCommandHandler() {
        super(32, 8);

        this.dispatcher = new CommandDispatcher<>();
    }

    /**
     * Adds a {@link CommandNode} to the brigadier structure.
     *
     * @param node The {@link CommandNode} to add.
     */
    public boolean registerCommand(CommandNode node) {
        //  We need to construct a command that can be registered with Brigadier
        dispatcher.register(constructCommand(node));

        return true;
    }

    /**
     * Handles the given {@link CommandDispatchInformation} and tries to execute the
     * command associated with the information.
     *
     * @param information The {@link CommandDispatchInformation} to handle.
     * @return True if the information was handled successfully, false otherwise.
     * @throws CommandExecutionException If an error occurred while parsing the information.
     */
    @Override
    public boolean handle(CommandDispatchInformation information) throws CommandExecutionException {
        String command = information.command() + " " + String.join(" ", information.arguments());
        CommanderEntity commander = information.commander();

        try {
            //  Execution is now handled by Brigadier
            return dispatcher.execute(command, commander) > 0;
        } catch (CommandSyntaxException ex) {
            throw new CommandExecutionException(information, ex.getMessage(), FeedbackType.BRIGADIER_SYNTAX,
                    new Object[]{ ex.getMessage() });
        }
    }

    private LiteralArgumentBuilder<CommanderEntity> constructCommand(CommandNode node) throws CommandRegisterException {
        //  For any given node, we receive a possible command. We thus create a literal argument builder
        LiteralArgumentBuilder<CommanderEntity> builder = LiteralArgumentBuilder.literal(node.getNameDefinition().getValue());

        //  For all arguments, we construct required arguments and add them to the current builder
        constructArgumentScheme(node, builder);

        //  For all sub-commands, we construct execution schemes and add them to the current builder
        for (CommandNode childNode : node.getChildren())
            builder.then(constructCommand(childNode));

        return builder;
    }

    private void constructArgumentScheme(CommandNode node, ArgumentBuilder<CommanderEntity, ?> builder) {
        //  Only if there is a verified command on the current level do we add the arguments
        //  If the node is not verified, we do not need to add any arguments/logic
        if (node.isVerified()) {
            VerifiedDelegateCommand command = (VerifiedDelegateCommand) node.getCommand();
            CommandBuffer<CommandArgument<?>> arguments = command.getArgumentBuffer();

            if (arguments == null || arguments.size() == 0)
                return;

            //  We need to construct a list of arguments that are required and optional
            List<CommandArgument<?>> requiredArguments = arguments.stream().filter(CommandArgument::isRequired).toList();
            ArgumentBuilder<CommanderEntity, ?> currentBuilder = null;

            if (requiredArguments.size() == 0)
                return;
            for (int i = 0; i < requiredArguments.size(); i++) {
                CommandArgument<?> argument = requiredArguments.get(i);

                //  If this is the first argument, we need to initialise the current builder and hook it to the main builder
                if (i == 0) {
                    currentBuilder = argument(argument.getIdentifier(), argument);
                    builder.then(currentBuilder);
                }

                //  If this is the last argument, we need to construct the execution scheme here for the given command
                else if (i == requiredArguments.size() - 1) {
                    ArgumentBuilder<CommanderEntity, ?> finalArgumentBuilder = argument(argument.getIdentifier(), argument);
                    constructExecutionScheme(node, finalArgumentBuilder);
                    currentBuilder.then(finalArgumentBuilder);
                }

                //  Otherwise, we create a new builder, append it to the current builder and make it the current builder
                else {
                    ArgumentBuilder<CommanderEntity, ?> newBuilder = argument(argument.getIdentifier(), argument);
                    currentBuilder.then(newBuilder);
                    currentBuilder = newBuilder;
                }
            }
        }
    }

    private void constructExecutionScheme(CommandNode node, ArgumentBuilder<CommanderEntity, ?> builder) {
        //  Only if there is a verified command on the current level do we add the command actions/triggers/excepts
        if (node.isVerified()) {
            VerifiedDelegateCommand command = (VerifiedDelegateCommand) node.getCommand();
            CommandBuffer<CommandAction> actions = command.getActionBuffer();

            //  Check if command can be executed asynchronous
            boolean async = command.getPropertyBuffer().stream()
                    .anyMatch(property -> property instanceof AsyncProperty);

            //  Check if the command is executed safely
            boolean safeExecute = command.getPropertyBuffer().stream()
                    .anyMatch(property -> property instanceof CatchExceptionProperty);

            if (actions != null)
                builder.executes(context -> {
                    //  Resolve context
                    List<Argument<?>> arguments = resolveContext(context);
                    String[] args = arguments.stream()
                            .map(Argument::identifier)
                            .toArray(String[]::new);
                    CommandDispatchInformation information = new CommandDispatchInformation(context.getSource(), node.getNameDefinition().getValue(), args);

                    try {
                        //  This is the execution loop where actions, excepts, triggers and events are executed
                        //      1. Actions
                        List<CommandCapture.Capture> captures = execute(context.getSource(), actions, arguments, async);
                        CommandCapture capture = new CommandCapture(captures);

                        //      2. Triggers
                        this.executeTriggers(information, command, capture);

                        //      3. Fire event
                        return getDelegate().getPlatform().dispatch(information, capture) ? 1 : 0;
                    } catch (Exception ex) {
                        //      4. Excepts
                        if (safeExecute) {
                            CommandBuffer<ExceptAction> exceptActions = command.getExceptBuffer();
                            List<String> exceptArguments = new ArrayList<>();

                            exceptArguments.add(information.command());
                            exceptArguments.addAll(Arrays.asList(information.arguments()));

                            if (exceptActions != null) {
                                for (ExceptAction action : exceptActions)
                                    action.run(information.commander(), FeedbackType.BRIGADIER_EXCEPTION, exceptArguments);
                            }
                        }
                        return generateEventFromException(information, ex) ? 1 : 0;
                    }
                });
        } else
            throw new CommandRegisterException("Command node must be verified before an execution scheme can be constructed!");
    }

    @SuppressWarnings("unchecked")
    private List<Argument<?>> resolveContext(CommandContext<CommanderEntity> context) throws CommandRegisterException {
        //  Hacky reflection way to get the arguments from the context
        try {
            Field argumentsMap = context.getClass().getField("arguments");
            argumentsMap.setAccessible(true);
            Map<String, ParsedArgument<CommanderEntity, ?>> arguments = (Map<String, ParsedArgument<CommanderEntity, ?>>) argumentsMap.get(null);
            List<Argument<?>> parsedArguments = new ArrayList<>();

            for (String key : arguments.keySet()) {
                ParsedArgument<CommanderEntity, ?> argument = arguments.get(key);
                Argument<?> parsedArgument = new Argument<>(key, argument.getResult());

                parsedArguments.add(parsedArgument);
            }

            return parsedArguments;
        } catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException ex) {
            throw new CommandRegisterException("Unable to resolve arguments from context.", ex);
        }
    }

}
