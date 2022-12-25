package com.github.polyrocketmatt.delegate.core.platform;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class PlatformTest {

    @Test
    public void testPlatform() {
        Platform platform = new MockPlatform();

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
