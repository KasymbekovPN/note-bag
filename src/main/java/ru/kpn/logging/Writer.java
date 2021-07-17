package ru.kpn.logging;

import java.io.IOException;

public interface Writer {
    void write(String log) throws IOException;
}
