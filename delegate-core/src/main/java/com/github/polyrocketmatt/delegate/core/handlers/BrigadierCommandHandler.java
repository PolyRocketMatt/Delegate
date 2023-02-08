package com.github.polyrocketmatt.delegate.core.handlers;

import com.github.polyrocketmatt.delegate.api.IHandler;
import com.github.polyrocketmatt.delegate.api.command.CommandBuffer;
import com.github.polyrocketmatt.delegate.api.command.CommandDispatchInformation;
import com.github.polyrocketmatt.delegate.api.command.action.CommandAction;
import com.github.polyrocketmatt.delegate.api.command.argument.Argument;
import com.github.polyrocketmatt.delegate.api.command.data.ActionItem;
import com.github.polyrocketmatt.delegate.api.command.data.CommandCapture;
import com.github.polyrocketmatt.delegate.api.command.feedback.FeedbackType;
import com.github.polyrocketmatt.delegate.api.command.trigger.CommandTrigger;
import com.github.polyrocketmatt.delegate.api.entity.CommanderEntity;
import com.github.polyrocketmatt.delegate.api.exception.CommandExecutionException;
import com.github.polyrocketmatt.delegate.api.exception.CommandRegisterException;
import com.github.polyrocketmatt.delegate.core.command.VerifiedDelegateCommand;
import com.github.polyrocketmatt.delegate.core.command.properties.AsyncProperty;
import com.github.polyrocketmatt.delegate.core.command.properties.CatchExceptionProperty;
import com.github.polyrocketmatt.delegate.core.command.tree.CommandNode;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.context.ParsedArgument;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.github.polyrocketmatt.delegate.core.DelegateCore.getDelegate;

public class BrigadierCommandHandler extends DelegateCommandHandler implements IHandler {

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
        LiteralArgumentBuilder<CommanderEntity> builder = LiteralArgumentBuilder
                .literal(node.getNameDefinition().getValue());

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
                        if (safeExecute)
                            generateEventFromException(information, ex);
                        return 0;
                    }
                });

            return builder;
        } else
            throw new CommandRegisterException("Command node is not verified!");
    }

    private void executeTriggers(CommandDispatchInformation information, VerifiedDelegateCommand command, CommandCapture capture) {
        CommandBuffer<CommandTrigger> triggers = command.getTriggerBuffer();
        List<ActionItem.Result> results = capture.getResults();

        //  TODO: Async triggers?
        triggers.forEach(trigger -> {
            if (trigger.shouldTrigger(results))
                trigger.call(information, capture);
        });
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
