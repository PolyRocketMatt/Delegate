package com.github.polyrocketmatt.delegate.integration;

import com.github.polyrocketmatt.delegate.api.IPlatform;
import com.github.polyrocketmatt.delegate.api.command.CommandDispatchInformation;
import com.github.polyrocketmatt.delegate.api.command.data.CommandCapture;
import com.github.polyrocketmatt.delegate.core.CommandBuilderImpl;
import com.github.polyrocketmatt.delegate.core.CommanderEntityImpl;
import com.github.polyrocketmatt.delegate.core.PlatformImpl;
import com.github.polyrocketmatt.delegate.core.command.DelegateCommand;
import com.github.polyrocketmatt.delegate.core.command.definition.DescriptionDefinition;
import com.github.polyrocketmatt.delegate.core.command.definition.NameDefinition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.github.polyrocketmatt.delegate.core.DelegateCore.getDelegate;

public class DelegateSubcommandCapturesTest {

    private static final PlatformImpl PLATFORM = new PlatformImpl();
    private static final CommanderEntityImpl ENTITY = new CommanderEntityImpl();

    @BeforeEach
    public void reset() {
        if (getDelegate().getPlatform() == null)
            getDelegate().setPlatform(PLATFORM);

        //  Clear command roots
        getDelegate().getCommandHandler().clearCommandCache();
    }

    @Test
    public void testSubcommandCaptures() {
        new CommandBuilderImpl()
                .withDefinition(new NameDefinition("test"))
                .withDefinition(new DescriptionDefinition("This is a top-level description"))
                .withSubcommand(new CommandBuilderImpl()
                        .withDefinition(new NameDefinition("subA"))
                        .withDefinition(new DescriptionDefinition("This is a subcommand description"))
                        .withSupplierAction(() -> 10)
                )
                .withSubcommand(new CommandBuilderImpl()
                        .withDefinition(new NameDefinition("subB"))
                        .withDefinition(new DescriptionDefinition("This is a subcommand description"))
                        .withSupplierAction(() -> "This is a result")
                )
                .build();
        CommandDispatchInformation information = new CommandDispatchInformation(ENTITY, "test", new String[] { "subA" });
        PLATFORM.execute(information);
        CommandCapture capture = PLATFORM.getCommandCapture();

        System.out.println("Got capture");
    }

}
