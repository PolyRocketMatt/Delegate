package com.github.polyrocketmatt.delegate.core.io;

import java.io.File;
import java.io.IOException;

public interface Configurable<T> {

    T importFrom(File file) throws IOException;

    void exportTo(File file) throws IOException;

}

