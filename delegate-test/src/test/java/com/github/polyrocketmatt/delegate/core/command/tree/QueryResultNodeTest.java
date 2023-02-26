package com.github.polyrocketmatt.delegate.core.command.tree;

import com.github.polyrocketmatt.delegate.core.command.DelegateCommand;
import com.github.polyrocketmatt.delegate.core.command.definition.AliasDefinition;
import com.github.polyrocketmatt.delegate.core.command.definition.DescriptionDefinition;
import com.github.polyrocketmatt.delegate.core.command.definition.NameDefinition;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class QueryResultNodeTest {

    private static class DelegateCommandImpl extends DelegateCommand {

        private final NameDefinition nameDefinition;
        private final DescriptionDefinition descriptionDefinition;
        private final AliasDefinition[] aliasDefinitions;

        public DelegateCommandImpl(String name, String description, String... aliases) {
            this.nameDefinition = new NameDefinition(name);
            this.descriptionDefinition = new DescriptionDefinition(description);
            this.aliasDefinitions = new AliasDefinition[aliases.length];
            for (int i = 0; i < aliases.length; i++)
                this.aliasDefinitions[i] = new AliasDefinition(aliases[i]);
        }

        public DelegateCommandImpl() {
            this("test", "A simple description", "test2");
        }

        @Override
        public @NotNull NameDefinition getNameDefinition() {
            return this.nameDefinition;
        }

        @Override
        public @NotNull DescriptionDefinition getDescriptionDefinition() {
            return this.descriptionDefinition;
        }

        @Override
        public AliasDefinition[] getAliases() {
            return this.aliasDefinitions;
        }
    }

    @Test
    public void testConstructor() {
        CommandNode node = new CommandNode(new DelegateCommandImpl("test", "A simple description", "test2"));
        QueryResultNode result = new QueryResultNode(node, "test", new String[] { "arg1" });

        assertEquals(node, result.node());
        assertEquals("test", result.commandPattern());
        assertEquals(1, result.remainingArgs().length);
        assertEquals("arg1", result.remainingArgs()[0]);
    }

    @Test
    public void testConstructorNull() {
        CommandNode node = new CommandNode(new DelegateCommandImpl("test", "A simple description", "test2"));

        assertThrows(IllegalArgumentException.class, () -> new QueryResultNode(null, "test", new String[] { "arg1" }));
        assertThrows(IllegalArgumentException.class, () -> new QueryResultNode(node, null, new String[] { "arg1" }));
        assertThrows(IllegalArgumentException.class, () -> new QueryResultNode(node, "test", null));
        assertThrows(IllegalArgumentException.class, () -> new QueryResultNode(node, "test", new String[] { "arg1", null }));
    }

}
