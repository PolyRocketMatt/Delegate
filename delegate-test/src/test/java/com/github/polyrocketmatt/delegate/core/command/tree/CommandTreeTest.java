package com.github.polyrocketmatt.delegate.core.command.tree;

import com.github.polyrocketmatt.delegate.core.command.DelegateCommand;
import com.github.polyrocketmatt.delegate.core.command.definition.AliasDefinition;
import com.github.polyrocketmatt.delegate.core.command.definition.DescriptionDefinition;
import com.github.polyrocketmatt.delegate.core.command.definition.NameDefinition;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CommandTreeTest {

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

    private final CommandNode rootA = new CommandNode(new DelegateCommandImpl("rootA", "Root A Description"));
    private final CommandNode rootB = new CommandNode(new DelegateCommandImpl("rootB", "Root B Description"));
    private final CommandNode rootC = new CommandNode(new DelegateCommandImpl("rootC", "Root C Description"));

    @Test
    public void testConstructor() {
        CommandTree tree = new CommandTree();

        assertEquals(0, tree.getRoots().size());
    }

    @Test
    public void testAddRoot() {
        CommandTree tree = new CommandTree();

        tree.add(this.rootA);
        tree.add(this.rootB);
        tree.add(this.rootC);

        assertEquals(3, tree.getRoots().size());
    }

    @Test
    public void testAddRootDuplicate() {
        CommandTree tree = new CommandTree();

        tree.add(this.rootA);

        assertThrows(IllegalArgumentException.class, () -> tree.add(this.rootA));
        assertEquals(1, tree.getRoots().size());
    }

    @Test
    public void testClear() {
        CommandTree tree = new CommandTree();

        tree.add(this.rootA);
        tree.add(this.rootB);
        tree.add(this.rootC);

        assertEquals(3, tree.getRoots().size());

        tree.clear();

        assertEquals(0, tree.getRoots().size());
    }

    @Test
    public void testFindRoot() {
        CommandTree tree = new CommandTree();

        tree.add(this.rootA);
        tree.add(this.rootB);
        tree.add(this.rootC);

        assertEquals(this.rootA, tree.find("rootA"));
        assertEquals(this.rootB, tree.find("rootB"));
        assertEquals(this.rootC, tree.find("rootC"));
    }

    @Test
    public void testFindRootNotFound() {
        CommandTree tree = new CommandTree();

        tree.add(this.rootA);
        tree.add(this.rootB);
        tree.add(this.rootC);

        assertNull(tree.find("rootD"));
    }

    @Test
    public void testFindRootNull() {
        CommandTree tree = new CommandTree();

        tree.add(this.rootA);
        tree.add(this.rootB);
        tree.add(this.rootC);

        assertThrows(IllegalArgumentException.class, () -> tree.find(null));
    }

}
