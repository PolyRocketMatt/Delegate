package com.github.polyrocketmatt.delegate.api.event;

import com.github.polyrocketmatt.delegate.api.IPlatform;
import com.github.polyrocketmatt.delegate.api.command.CommandDispatchInformation;
import com.github.polyrocketmatt.delegate.api.command.data.CommandCapture;
import com.github.polyrocketmatt.delegate.api.entity.CommanderEntity;

public interface CommandEvent {

    CommandDispatchInformation getDispatchInformation();

    CommandCapture getCapture();

}
