package com.github.polyrocketmatt.delegate.core.command;

import com.github.polyrocketmatt.delegate.core.command.definition.DescriptionDefinition;
import com.github.polyrocketmatt.delegate.core.command.definition.NameDefinition;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CommandFactoryTest {

    @Test
    public void testNameAndDescCreate() {
        DelegateCommandBuilder chain = CommandFactory.create("test", "this is a test description");

        assertNotNull(chain);
        assertEquals(2, chain.size());
        assertEquals(0, chain.getActions().size());
        assertEquals(0, chain.getArguments().size());
        assertEquals(2, chain.getDefinitions().size());
        assertEquals(0, chain.getProperties().size());

        CommandAttribute nameAttrib = chain.find(NameDefinition.class);
        CommandAttribute descriptionAttrib = chain.find(DescriptionDefinition.class);

        assertNotNull(nameAttrib);
        assertEquals(NameDefinition.class, nameAttrib.getClass());
        assertEquals("test", ((NameDefinition) nameAttrib).getValue());

        assertNotNull(descriptionAttrib);
        assertEquals(DescriptionDefinition.class, descriptionAttrib.getClass());
        assertEquals("this is a test description", ((DescriptionDefinition) descriptionAttrib).getValue());
    }

}
