package ru.kpn.logging;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

public class SoutWriterTest {

    @Test
    @SneakyThrows
    void shouldWriteToSOUT() {
        Writer writer = new SoutWriter();
        writer.write("Test log !!!\n");
        writer.write("Test log !!!\n");
        writer.write("Test log !!!\n");
    }
}
