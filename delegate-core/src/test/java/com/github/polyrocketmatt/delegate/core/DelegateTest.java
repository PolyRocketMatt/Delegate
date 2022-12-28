package com.github.polyrocketmatt.delegate.core;

import com.github.polyrocketmatt.delegate.api.IPlatform;
import com.github.polyrocketmatt.delegate.api.PlatformType;
import org.junit.jupiter.api.Test;

import static com.github.polyrocketmatt.delegate.core.Delegate.getDelegate;
import static org.junit.jupiter.api.Assertions.*;

public class DelegateTest {

    @Test
    public void testDelegate() {
        assertNotNull(getDelegate());
        assertNotNull(getDelegate().getAttributeHandler());
        assertNotNull(getDelegate().getCommandHandler());
        assertNull(getDelegate().getPlatform());
        assertNull(getDelegate().getDelegateVersion());
    }

    @Test
    public void testBukkitPlatform() {
        getDelegate().setPlatform(new MockPlatform());
        IPlatform platform = getDelegate().getPlatform();

        assertEquals("mockPlatform", platform.getPluginVersion());
        assertEquals(PlatformType.BUKKIT, platform.getPlatformType());
    }

    private static class MockPlatform implements IPlatform {

        @Override
        public String getPluginVersion() {
            return "mockPlatform";
        }

        @Override
        public PlatformType getPlatformType() {
            return PlatformType.BUKKIT;
        }

        @Override
        public boolean hasPermission(String permission) throws UnsupportedOperationException {
            return false;
        }
    }

}
