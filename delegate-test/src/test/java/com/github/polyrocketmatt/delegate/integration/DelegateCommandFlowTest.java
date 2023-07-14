package com.github.polyrocketmatt.delegate.integration;

import com.github.polyrocketmatt.delegate.api.IPlatform;
import com.github.polyrocketmatt.delegate.core.CommandBuilderImpl;
import com.github.polyrocketmatt.delegate.core.CommanderEntityImpl;
import com.github.polyrocketmatt.delegate.core.PlatformImpl;
import org.junit.jupiter.api.BeforeEach;

import static com.github.polyrocketmatt.delegate.core.DelegateCore.getDelegate;

/**
 * Tests all possible points of failure when a command is registered.
 * Building will fail if the attribute-handler fails, but is tested separately.
 */
public class DelegateCommandFlowTest {

    private static final IPlatform PLATFORM = new PlatformImpl();
    private static final CommanderEntityImpl ENTITY = new CommanderEntityImpl();
    private static CommandBuilderImpl BUILDER = new CommandBuilderImpl();

    @BeforeEach
    public void reset() {
        if (getDelegate().getPlatform() == null)
            getDelegate().setPlatform(PLATFORM);

        BUILDER = new CommandBuilderImpl();

        //  Clear command roots
        getDelegate().getCommandHandler().clearCommandCache();
    }

}
