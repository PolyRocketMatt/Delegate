package com.github.polyrocketmatt.delegate.api.command.trigger;

import com.github.polyrocketmatt.delegate.api.AttributeType;
import com.github.polyrocketmatt.delegate.api.command.CommandDispatchInformation;
import com.github.polyrocketmatt.delegate.api.command.data.ActionItem;
import com.github.polyrocketmatt.delegate.api.command.data.CommandCapture;
import com.github.polyrocketmatt.delegate.api.entity.ConsoleCommander;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.function.BiConsumer;

import static org.junit.jupiter.api.Assertions.*;

public class TestCommandTrigger {

    private static class CommandTriggerImpl extends CommandTrigger {

        public CommandTriggerImpl(@NotNull BiConsumer<CommandDispatchInformation, CommandCapture> trigger) {
            super(trigger);
        }

        @Override
        public boolean shouldTrigger(List<ActionItem.Result> results) {
            return results.stream().allMatch(ActionItem.Result::isSuccess);
        }

    }

    @Test
    public void testConstructor() {
        BiConsumer<CommandDispatchInformation, CommandCapture> trigger = (info, capture) -> {};
        CommandTriggerImpl commandTrigger = new CommandTriggerImpl(trigger);

        assertEquals(trigger, commandTrigger.trigger);
        assertEquals(AttributeType.TRIGGER, commandTrigger.getType());
    }

    @Test
    public void testConstructorIllegal() {
        assertThrows(IllegalArgumentException.class, () -> new CommandTriggerImpl(null));
    }

    @Test
    public void testCallValid() {
        ConsoleCommander commander = new ConsoleCommander();
        CommandTriggerImpl commandTrigger = new CommandTriggerImpl((info, capture) -> {
            System.out.println("Triggered!");
        });
        CommandDispatchInformation information = new CommandDispatchInformation(commander, "test", new String[] {});
        CommandCapture capture = new CommandCapture(List.of(
                new CommandCapture.Capture("a1", new ActionItem<>(ActionItem.Result.SUCCESS, true))));

        assertDoesNotThrow(() -> commandTrigger.call(information, capture));
    }

    @Test
    public void testCallInvalid() {
        ConsoleCommander commander = new ConsoleCommander();
        CommandTriggerImpl commandTrigger = new CommandTriggerImpl((info, capture) -> {
            System.out.println("Triggered!");
        });
        CommandDispatchInformation information = new CommandDispatchInformation(commander, "test", new String[] {});
        CommandCapture capture = new CommandCapture(List.of(
                new CommandCapture.Capture("a1", new ActionItem<>(ActionItem.Result.SUCCESS, true))));

        assertThrows(IllegalArgumentException.class, () -> commandTrigger.call(null, capture));
        assertThrows(IllegalArgumentException.class, () -> commandTrigger.call(information, null));
    }

}
