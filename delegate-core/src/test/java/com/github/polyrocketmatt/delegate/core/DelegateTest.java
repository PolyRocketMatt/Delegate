package com.github.polyrocketmatt.delegate.core;

import com.github.polyrocketmatt.delegate.core.platform.Platform;
import com.github.polyrocketmatt.delegate.core.platform.PlatformType;
import org.junit.jupiter.api.Test;

import java.io.File;

import static com.github.polyrocketmatt.delegate.core.Delegate.getDelegate;
import static org.junit.jupiter.api.Assertions.*;

public class DelegateTest {

    @Test
    public void testDelegate() {
        assertNotNull(getDelegate());
        assertNotNull(getDelegate().getAttributeHandler());
        assertNotNull(getDelegate().getCommandHandler());
        assertFalse(getDelegate().setup());
        assertNull(getDelegate().getPlatform());
        assertNull(getDelegate().dataFolder());
        assertNull(getDelegate().getDelegateVersion());
    }

    @Test
    public void testBukkitPlatform() {
        getDelegate().setPlatform(new MockPlatform());
        Platform platform = getDelegate().getPlatform();

        assertNull(platform.getFileStorage());
        assertEquals("mockPlatform", platform.getPluginVersion());
        assertEquals(PlatformType.BUKKIT, platform.getPlatformType());
    }

    private static class MockPlatform implements Platform {

        @Override
        public File getFileStorage() {
            return null;
        }

        @Override
        public String getPluginVersion() {
            return "mockPlatform";
        }

        @Override
        public PlatformType getPlatformType() {
            return PlatformType.BUKKIT;
        }

    }

}
