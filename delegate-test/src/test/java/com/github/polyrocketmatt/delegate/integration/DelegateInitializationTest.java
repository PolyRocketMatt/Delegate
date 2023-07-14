package com.github.polyrocketmatt.delegate.integration;

import com.github.polyrocketmatt.delegate.api.IPlatform;
import com.github.polyrocketmatt.delegate.core.DelegateCore;
import com.github.polyrocketmatt.delegate.core.PlatformImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the initialization of the DelegateCore class.
 */
public class DelegateInitializationTest {

    @BeforeEach
    public void reset() {
        DelegateCore.getDelegate().setPlatform(null);
    }

    @Test
    public void testDelegateInit() {
        IPlatform platform = new PlatformImpl();
        DelegateCore.getDelegate().setPlatform(platform);

        //  Test nullity of handlers
        assertNotNull(DelegateCore.getDelegate().getCommandHandler());
        assertNotNull(DelegateCore.getDelegate().getAttributeHandler());

        //  Test nullity of implemented command handlers
        assertNotNull(DelegateCore.getDelegate().getInternalCommandHandler());
        assertNotNull(DelegateCore.getDelegate().getBrigadierCommandHandler());

        //  Test nullity and equality of platform
        assertNotNull(DelegateCore.getDelegate().getPlatform());
        assertEquals(platform, DelegateCore.getDelegate().getPlatform());

        //  Test nullity of configuration
        assertNotNull(DelegateCore.getDelegate().getConfiguration());

        //  Basic config
        assertTrue(DelegateCore.getDelegate().isVerbose());
        assertFalse(DelegateCore.getDelegate().useBrigadier());
    }

}
