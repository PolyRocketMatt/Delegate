package com.github.polyrocketmatt.delegate.core;

import com.github.polyrocketmatt.delegate.core.command.CommandAttributeChain;
import com.github.polyrocketmatt.delegate.core.command.CommandFactory;
import com.github.polyrocketmatt.delegate.core.command.DelegateCommand;
import com.github.polyrocketmatt.delegate.core.command.definition.NameAttribute;
import org.junit.jupiter.api.Test;

import java.util.List;

public class TestArgumentChain {

    @Test
    public void testChain() {
        DelegateCommand cmd = CommandFactory.create()
                .append(new NameAttribute("test"))
                .append(new NameAttribute("test2"))
                .build();
    }

    @Test
    public void testCompatibility() {

    }

}