package com.github.polyrocketmatt.delegate.core.command.argument;

import com.github.polyrocketmatt.delegate.api.AttributeType;
import com.github.polyrocketmatt.delegate.api.command.argument.Argument;
import com.github.polyrocketmatt.delegate.api.exception.ArgumentParseException;
import com.github.polyrocketmatt.delegate.core.command.argument.rule.IntMaxRule;
import com.github.polyrocketmatt.delegate.core.command.argument.rule.IntMinRule;
import com.github.polyrocketmatt.delegate.core.command.argument.rule.NonNullRule;
import com.mojang.brigadier.StringReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.github.polyrocketmatt.delegate.core.DelegateCore.getDelegate;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EnumTypeArgumentTest {

    private enum TestEnum {
        LOW,
        MEDIUM,
        HIGH
    }

    @BeforeEach
    public void setup() {
        getDelegate().setVerbose(true);
    }

    @Test
    public void testPrimaryConstructor() {
        EnumTypeArgument<TestEnum> argument = EnumTypeArgument.of("identifier", "description", TestEnum.class);

        assertEquals("identifier", argument.getIdentifier());
        assertEquals("description", argument.getArgumentDescription());
        assertEquals(AttributeType.ARGUMENT, argument.getType());
        assertEquals(TestEnum.class, argument.getArgumentType());
    }

    @Test
    public void testSecondaryConstructor() {
        EnumTypeArgument<TestEnum> argument = EnumTypeArgument.of("identifier", "description", TestEnum.LOW, TestEnum.class);

        assertEquals("identifier", argument.getIdentifier());
        assertEquals("description", argument.getArgumentDescription());
        assertEquals(AttributeType.ARGUMENT, argument.getType());
        assertEquals(TestEnum.class, argument.getArgumentType());
        assertEquals(TestEnum.LOW, argument.getDefault().output());
    }

    @Test
    public void testTernaryConstructor() {
        EnumTypeArgument<TestEnum> argument = EnumTypeArgument.of("identifier", "description", TestEnum.class, new NonNullRule());

        assertEquals("identifier", argument.getIdentifier());
        assertEquals("description", argument.getArgumentDescription());
        assertEquals(AttributeType.ARGUMENT, argument.getType());
        assertEquals(TestEnum.class, argument.getArgumentType());
        assertEquals(1, argument.getArgumentRules().size());
        assertTrue(argument.getArgumentRules().get(0) instanceof NonNullRule);
        assertNull(argument.getDefault().output());
    }

    @Test
    public void testQuaternaryConstructor() {
        EnumTypeArgument<TestEnum> argument = EnumTypeArgument.of("identifier", "description", TestEnum.LOW, TestEnum.class,
                new IntMinRule(0), new IntMaxRule(1));

        assertEquals("identifier", argument.getIdentifier());
        assertEquals("description", argument.getArgumentDescription());
        assertEquals(AttributeType.ARGUMENT, argument.getType());
        assertEquals(TestEnum.class, argument.getArgumentType());
        assertEquals(2, argument.getArgumentRules().size());
        assertTrue(argument.getArgumentRules().get(0) instanceof IntMinRule);
        assertTrue(argument.getArgumentRules().get(1) instanceof IntMaxRule);
        assertEquals(TestEnum.LOW, argument.getDefault().output());
    }

    @Test
    public void testInternalParserSuccess() {
        EnumTypeArgument<TestEnum> argument = EnumTypeArgument.of("identifier", "description", TestEnum.LOW, TestEnum.class);
        Argument<TestEnum> parsedArgumentA = argument.parse("LOW");
        Argument<TestEnum> parsedArgumentB = argument.parse("HigH");

        assertEquals(TestEnum.LOW, parsedArgumentA.output());
        assertEquals(TestEnum.HIGH, parsedArgumentB.output());
    }

    @Test
    public void testInternalParserInputNull() {
        EnumTypeArgument<TestEnum> argumentA = EnumTypeArgument.of("identifier", "description", TestEnum.class);
        EnumTypeArgument<TestEnum> argumentB = EnumTypeArgument.of("identifier", "description", TestEnum.MEDIUM, TestEnum.class);

        assertThrows(ArgumentParseException.class, () -> argumentA.parse((String) null));
        assertEquals(TestEnum.MEDIUM, argumentB.parse((String) null).output());
    }

    @Test
    public void testInternalParserFailure() {
        EnumTypeArgument<TestEnum> argument = EnumTypeArgument.of("identifier", "description", TestEnum.class);

        assertThrows(ArgumentParseException.class, () -> argument.parse("0."));
        assertThrows(ArgumentParseException.class, () -> argument.parse("not an enum value"));
        assertThrows(ArgumentParseException.class, () -> argument.parse("LooW"));
        assertThrows(ArgumentParseException.class, () -> argument.parse("LO W"));
    }

    @Test
    public void testInternalParserFailureWithDefault() {
        EnumTypeArgument<TestEnum> argument = EnumTypeArgument.of("identifier", "description", TestEnum.LOW, TestEnum.class);
        Argument<TestEnum> parsedArgumentA = argument.parse("0.");
        Argument<TestEnum> parsedArgumentB = argument.parse("not a number");
        Argument<TestEnum> parsedArgumentC = argument.parse("LooW");
        Argument<TestEnum> parsedArgumentD = argument.parse("LO W");

        assertEquals(TestEnum.LOW, parsedArgumentA.output());
        assertEquals(TestEnum.LOW, parsedArgumentB.output());
        assertEquals(TestEnum.LOW, parsedArgumentC.output());
        assertEquals(TestEnum.LOW, parsedArgumentD.output());
    }

    @Test
    public void testBrigadierParserSuccess() {
        EnumTypeArgument<TestEnum> argument = EnumTypeArgument.of("identifier", "description", TestEnum.LOW, TestEnum.class);
        StringReader readerA = new StringReader("MEDIUM");

        assertEquals(TestEnum.MEDIUM, argument.parse(readerA));
    }

    @Test
    public void testBrigadierParserFailure() {
        EnumTypeArgument<TestEnum> argument = EnumTypeArgument.of("identifier", "description", TestEnum.class);
        StringReader readerA = new StringReader("LooW");
        StringReader readerB = new StringReader("LO W");

        assertThrows(ArgumentParseException.class, () -> argument.parse(readerA));
        assertThrows(ArgumentParseException.class, () -> argument.parse(readerB));
    }

    @Test
    public void testBrigadierParserFailureWithDefault() {
        EnumTypeArgument<TestEnum> argument = EnumTypeArgument.of("identifier", "description", TestEnum.LOW, TestEnum.class);
        StringReader readerA = new StringReader("LooW");
        StringReader readerB = new StringReader("LO W");

        assertEquals(TestEnum.LOW, argument.parse(readerA));
        assertEquals(TestEnum.LOW, argument.parse(readerB));
    }

}
