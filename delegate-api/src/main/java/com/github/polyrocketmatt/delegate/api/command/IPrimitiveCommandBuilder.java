package com.github.polyrocketmatt.delegate.api.command;

import org.apiguardian.api.API;
import org.jetbrains.annotations.NotNull;

@API(status = API.Status.STABLE, since = "0.0.1")
public interface IPrimitiveCommandBuilder {

    @NotNull IDelegateCommand build();

    @NotNull IPrimitiveCommandBuilder with(@NotNull ICommandAttribute attribute);

}
