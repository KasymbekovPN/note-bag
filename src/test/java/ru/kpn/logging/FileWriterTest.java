package ru.kpn.logging;

import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

public class FileWriterTest {

    private static final String FILE_PATH = "FileWriterTest.txt";

    private Path path;

    @BeforeEach
    void setUp() {
        path = Paths.get(FILE_PATH);
    }

    @SneakyThrows
    @Test
    void shouldWriteToFile() {

        Writer writer = new FileWriter(path);
        writer.write("Test");
        assertThat(getFileContent(path)).isEqualTo("Test");

        writer.write("!!!");
        assertThat(getFileContent(path)).isEqualTo("Test!!!");
    }

    private String getFileContent(Path path) throws IOException {
        byte[] bytes = Files.readAllBytes(path);
        return new String(bytes, StandardCharsets.UTF_8);
    }

    @AfterEach
    @SneakyThrows
    void tearDown() {
        Files.deleteIfExists(path);
    }
}
