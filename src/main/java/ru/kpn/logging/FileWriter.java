package ru.kpn.logging;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE_NEW;

class FileWriter implements Writer {

    private final Path path;

    public FileWriter(Path path) {
        this.path = path;
    }

    @Override
    public void write(String log) throws IOException {
        Files.write(
                path,
                log.getBytes(StandardCharsets.UTF_8),
                Files.exists(path) ? APPEND : CREATE_NEW
        );
    }
}
