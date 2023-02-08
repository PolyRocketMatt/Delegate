package com.github.polyrocketmatt.delegate.core.command.definition;

import com.github.polyrocketmatt.delegate.core.command.TestCommandBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.github.polyrocketmatt.delegate.core.DelegateCore.getDelegate;
import static org.junit.jupiter.api.Assertions.*;

public class SubcommandDefinitionTest {

    private TestCommandBuilder builder;

    @BeforeEach
    public void setup() {
        getDelegate().setVerbose(true);

        this.builder = new TestCommandBuilder();
    }

    @Test
    public void testConstructor() {
        SubcommandDefinition definition = new SubcommandDefinition(builder);

        assertNotNull(definition.getIdentifier());
        assertNotNull(definition.getValue());
    }

    @Test
    public void testConstructorWithNull() {
        assertThrows(IllegalArgumentException.class, () -> new SubcommandDefinition(null));
    }

}
