package com.github.polyrocketmatt.delegate.core.handlers;

import com.github.polyrocketmatt.delegate.api.command.CommandBuffer;
import com.github.polyrocketmatt.delegate.api.command.CommandDispatchInformation;
import com.github.polyrocketmatt.delegate.api.command.action.CommandAction;
import com.github.polyrocketmatt.delegate.api.command.argument.Argument;
import com.github.polyrocketmatt.delegate.api.command.data.ActionItem;
import com.github.polyrocketmatt.delegate.api.command.data.CommandCapture;
import com.github.polyrocketmatt.delegate.api.command.feedback.FeedbackType;
import com.github.polyrocketmatt.delegate.api.entity.CommanderEntity;
import com.github.polyrocketmatt.delegate.api.exception.CommandExecutionException;
import com.github.polyrocketmatt.delegate.api.handlers.CommandHandler;
import com.github.polyrocketmatt.delegate.core.DelegateCore;
import com.github.polyrocketmatt.delegate.core.command.VerifiedDelegateCommand;
import com.github.polyrocketmatt.delegate.core.command.action.ExceptAction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;

public abstract class DelegateCommandHandler extends CommandHandler {

    private final int availableProcessors;
    private final int maxStealCount;

    public DelegateCommandHandler(int availableProcessors, int maxStealCount) {
        this.availableProcessors = availableProcessors;
        this.maxStealCount = maxStealCount;
    }

    protected boolean generateEventFromException(CommandDispatchInformation information, Exception ex) {
        StackTraceElement[] stackTrace = ex.getStackTrace();
        StringBuilder builder = new StringBuilder("\n");
        for (StackTraceElement element : stackTrace)
            builder.append(element.toString()).append("     \n");

        CommandCapture capture = new CommandCapture(List.of(
                new CommandCapture.Capture("exception", new ActionItem<>(ActionItem.Result.FAILURE, ex.getMessage())),
                new CommandCapture.Capture("stacktrace", new ActionItem<>(ActionItem.Result.FAILURE, builder.toString()))
        ));

        return DelegateCore.getDelegate().getPlatform().dispatch(information, capture);
    }

    protected CommandExecutionException exceptOrThrow(
            CommandDispatchInformation information,
            VerifiedDelegateCommand cmd,
            FeedbackType type,
            Object... args)
            throws CommandExecutionException {
        if (cmd != null) {
            CommandBuffer<ExceptAction> actions = cmd.getExceptBuffer();
            List<String> arguments = new ArrayList<>();

            arguments.add(information.command());
            arguments.addAll(Arrays.asList(information.arguments()));

            if (actions != null) {
                for (ExceptAction action : actions)
                    action.run(information.commander(), type, arguments);
            }
        }

        return new CommandExecutionException(information, DelegateCore.getDelegate().getConfiguration().get(type), type, args);
    }

    protected List<CommandCapture.Capture> execute(CommanderEntity commander, CommandBuffer<CommandAction> actions, List<Argument<?>> arguments, boolean async) {
        //  Run all command actions in order of precedence
        List<Integer> precedences = actions.stream()
                .map(CommandAction::getPrecedence)
                .sorted()
                .toList();

        //  Initialize parameters
        int availableThreadCount = Math.min(this.availableProcessors, this.maxStealCount);
        int threadCount = Math.min(availableThreadCount, actions.size());

        //  Verification of arguments ensures correct order of arguments
        List<CommandCapture.Capture> captures = new ArrayList<>();
        ExecutorService executor = new ForkJoinPool(threadCount);

        for (int precedence : precedences) {
            List<CommandAction> actionsWithPrecedence = actions.stream()
                    .filter(action -> action.getPrecedence() == precedence)
                    .toList();

            if (async) {
                for (CommandAction action : actionsWithPrecedence)
                    executor.execute(() -> captures.add(new CommandCapture.Capture(action.getIdentifier(), action.run(commander, arguments))));
            } else {
                for (CommandAction action : actionsWithPrecedence)
                    captures.add(new CommandCapture.Capture(action.getIdentifier(), action.run(commander, arguments)));
            }
        }

        return captures;
    }

}
