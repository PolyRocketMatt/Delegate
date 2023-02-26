// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

package com.github.polyrocketmatt.delegate.core.command.tree;

import com.github.polyrocketmatt.delegate.core.command.DelegateCommand;
import com.github.polyrocketmatt.delegate.core.command.VerifiedDelegateCommand;
import com.github.polyrocketmatt.delegate.core.command.definition.AliasDefinition;
import com.github.polyrocketmatt.delegate.core.command.definition.DescriptionDefinition;
import com.github.polyrocketmatt.delegate.core.command.definition.NameDefinition;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CommandNodeTest {

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

    private final DelegateCommandImpl command = new DelegateCommandImpl();
    private final VerifiedDelegateCommand verifiedCommand = new VerifiedDelegateCommand.VerifiedCommandBuilder()
            .buildNameDefinition(command.getNameDefinition())
            .buildDescriptionDefinition(command.getDescriptionDefinition())
            .buildAliasDefinitions(command.getAliases())
            .build();
    private final VerifiedDelegateCommand parentVerifiedCommand = new VerifiedDelegateCommand.VerifiedCommandBuilder()
            .buildNameDefinition(new NameDefinition("parent"))
            .buildDescriptionDefinition(new DescriptionDefinition("A simple parent description"))
            .buildAliasDefinitions(new AliasDefinition[] {
                    new AliasDefinition("parent2"),
            })
            .build();

    @Test
    public void testPrimaryConstructor() {
        CommandNode node = new CommandNode(command);

        assertEquals(command, node.getCommand());
        assertEquals(0, node.getChildren().size());
        assertEquals(command.getNameDefinition(), node.getNameDefinition());
        assertEquals(command.getDescriptionDefinition(), node.getDescriptionDefinition());
        assertEquals(command.getAliases(), node.getAliasDefinitions());
        assertNull(node.getParent());
        assertFalse(node.isVerified());
    }

    @Test
    public void testPrimaryConstructorVerified() {
        CommandNode node = new CommandNode(verifiedCommand);

        assertEquals(verifiedCommand, node.getCommand());
        assertEquals(0, node.getChildren().size());
        assertEquals(command.getNameDefinition(), node.getNameDefinition());
        assertEquals(command.getDescriptionDefinition(), node.getDescriptionDefinition());
        assertEquals(command.getAliases(), node.getAliasDefinitions());
        assertNull(node.getParent());
        assertTrue(node.isVerified());
    }

    @Test
    public void testPrimaryConstructorNull() {
        assertThrows(IllegalArgumentException.class, () -> new CommandNode(null));
    }

    @Test
    public void testSecondaryConstructor() {
        CommandNode parent = new CommandNode(parentVerifiedCommand);
        CommandNode node = new CommandNode(parent, command);

        assertEquals(command, node.getCommand());
        assertEquals(0, node.getChildren().size());
        assertEquals(command.getNameDefinition(), node.getNameDefinition());
        assertEquals(command.getDescriptionDefinition(), node.getDescriptionDefinition());
        assertEquals(command.getAliases(), node.getAliasDefinitions());
        assertEquals(parent, node.getParent());
        assertFalse(node.isVerified());
    }

    @Test
    public void testSecondaryConstructorVerified() {
        CommandNode parent = new CommandNode(parentVerifiedCommand);
        CommandNode node = new CommandNode(parent, verifiedCommand);

        assertEquals(verifiedCommand, node.getCommand());
        assertEquals(0, node.getChildren().size());
        assertEquals(command.getNameDefinition(), node.getNameDefinition());
        assertEquals(command.getDescriptionDefinition(), node.getDescriptionDefinition());
        assertEquals(command.getAliases(), node.getAliasDefinitions());
        assertEquals(parent, node.getParent());
        assertTrue(node.isVerified());
    }

    @Test
    public void testSecondaryConstructorNull() {
        assertThrows(IllegalArgumentException.class, () -> new CommandNode(null, null));
    }

    @Test
    public void testReplaceCommand() {
        CommandNode node = new CommandNode(parentVerifiedCommand);
        node.setCommand(verifiedCommand);

        assertEquals(verifiedCommand, node.getCommand());
        assertEquals(0, node.getChildren().size());
        assertEquals(command.getNameDefinition(), node.getNameDefinition());
        assertEquals(command.getDescriptionDefinition(), node.getDescriptionDefinition());
        assertEquals(command.getAliases(), node.getAliasDefinitions());
        assertNull(node.getParent());
        assertTrue(node.isVerified());
    }

    @Test
    public void testReplaceCommandNull() {
        CommandNode node = new CommandNode(parentVerifiedCommand);
        assertThrows(IllegalArgumentException.class, () -> node.setCommand(null));
    }

    @Test
    public void testAddChild() {
        CommandNode node = new CommandNode(parentVerifiedCommand);
        CommandNode child = new CommandNode(node, verifiedCommand);

        assertEquals(1, node.getChildren().size());
        assertEquals(child, node.getChildren().get(0));
        assertEquals(verifiedCommand, node.getChildren().get(0).getCommand());
    }

    @Test
    public void testAddChildSuccessfully() {
        CommandNode parent = new CommandNode(parentVerifiedCommand);
        CommandNode node = new CommandNode(verifiedCommand);

        parent.addChild(node);

        assertEquals(1, parent.getChildren().size());
        assertEquals(node, parent.getChildren().get(0));
        assertEquals(verifiedCommand, parent.getChildren().get(0).getCommand());
    }

    @Test
    public void testAddChildNull() {
        CommandNode node = new CommandNode(parentVerifiedCommand);

        assertThrows(IllegalArgumentException.class, () -> node.addChild(null));
    }

    @Test
    public void testAddChildNonParental() {
        CommandNode node = new CommandNode(parentVerifiedCommand);
        CommandNode child = new CommandNode(verifiedCommand);

        node.addChild(child);

        assertThrows(IllegalArgumentException.class, () -> node.addChild(child));
    }

    @Test
    public void testAddChildAlreadyParental() {
        CommandNode node = new CommandNode(parentVerifiedCommand);
        CommandNode child = new CommandNode(node, verifiedCommand);

        assertThrows(IllegalArgumentException.class, () -> node.addChild(child));
    }

    private final DelegateCommandImpl test = new DelegateCommandImpl("test", "", "testA");
    private final DelegateCommandImpl testAdd = new DelegateCommandImpl("add", "", "testAddA");
    private final DelegateCommandImpl testRemove = new DelegateCommandImpl("remove", "", "testRemoveA");
    private final DelegateCommandImpl run = new DelegateCommandImpl("run", "", "");
    private final DelegateCommandImpl runAdd = new DelegateCommandImpl("add", "", "");
    private final DelegateCommandImpl runAddA = new DelegateCommandImpl("a", "", "");
    private final DelegateCommandImpl runAddB = new DelegateCommandImpl("b", "", "");
    private final String pattern = "test";
    private final String[] names = new String[] { "run", "add", "a"};
    private final String[] illegalNames = new String[] { "run", "add", "c"};

    /**
     * Tree structure:
     * - test
     *      | - test add
     *      | - test remove
     *      | - test run
     *          | - test run add
     *              | - test run add a
     *              | - test run add b
     */
    private CommandNode createStructure() {
        CommandNode testNode = new CommandNode(test);
        CommandNode testAddNode = new CommandNode(testNode, testAdd);
        CommandNode testRemoveNode = new CommandNode(testNode, testRemove);
        CommandNode runNode = new CommandNode(testNode, run);
        CommandNode runAddNode = new CommandNode(runNode, runAdd);
        CommandNode runAddANode = new CommandNode(runAddNode, runAddA);
        CommandNode runAddBNode = new CommandNode(runAddNode, runAddB);

        return testNode;
    }

    @Test
    public void testFindDeepest() {
        CommandNode tree = createStructure();
        CommandNode expected = tree.getChildren().get(2).getChildren().get(0).getChildren().get(0);
        QueryResultNode result = tree.findDeepest(pattern, names);

        assertEquals(expected, result.node());
    }

    @Test
    public void testFindDeepestNonExistent() {
        CommandNode tree = createStructure();
        CommandNode expected = tree.getChildren().get(2).getChildren().get(0);
        QueryResultNode result = tree.findDeepest(pattern, illegalNames);

        assertEquals(expected, result.node());
    }

    @Test
    public void testFindDeepestNull() {
        CommandNode tree = createStructure();

        assertThrows(IllegalArgumentException.class, () -> tree.findDeepest(null, new String[] {}));
        assertThrows(IllegalArgumentException.class, () -> tree.findDeepest("", null));
        assertThrows(IllegalArgumentException.class, () -> tree.findDeepest("null", new String[]{ null, "" }));
    }

    @Test
    public void testFindDeepestNoChildren() {
        CommandNode tree = new CommandNode(test);
        QueryResultNode result = tree.findDeepest(pattern, names);

        assertEquals(tree, result.node());
        assertEquals(pattern, result.commandPattern());
        assertEquals(names, result.remainingArgs());
    }

    @Test
    public void testFindDeepestNoNames() {
        String[] names = new String[] {};
        CommandNode tree = new CommandNode(test);
        QueryResultNode result = tree.findDeepest(pattern, names);

        assertEquals(tree, result.node());
        assertEquals(pattern, result.commandPattern());
        assertEquals(names, result.remainingArgs());
    }

    @Test
    public void testCommandNodeEquality() {
        CommandNode a = new CommandNode(test);
        CommandNode b = new CommandNode(test);

        assertEquals(a, b);
    }

}
